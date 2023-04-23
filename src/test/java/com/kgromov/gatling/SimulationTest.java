package com.kgromov.gatling;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.concurrent.atomic.AtomicInteger;

import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;
import static io.gatling.javaapi.http.HttpDsl.status;

public class SimulationTest extends Simulation {
    //    HttpProtocolBuilder protocol = HttpDsl.http.baseUrl("https://google.com");
    HttpProtocolBuilder protocol = HttpDsl.http;
    private final FeederBuilder<String> csvFeeder = csv("search_data.csv").random();
    private final FeederBuilder<Object> jdbcFeeder = jdbcFeeder(
            "jdbc:mysql://localhost:3306/spring_guru_recipe",
            "root", "admin",
            "SELECT * FROM category"
    ).circular();

    private final ChainBuilder searchGoogleRequest = CoreDsl.exec(
            HttpDsl.http("google search")
                    .get("https://google.com/search")
                    .queryParam("q", "#{value}")
                    .check(
                            css("a:contains('#{value}')", "href").saveAs("searchResultUrl")
                    )
    );


    private final ChainBuilder browseResult = CoreDsl
            .exec(
                    session -> {
                        System.out.println("searchResultUrl = " + session.get("searchResultUrl"));
                        return session;
                    })
            .exec(HttpDsl.http("search for #{value}")
                    .get("#{searchResultUrl}")
                    .check(status().is(200))
            );
    private final AtomicInteger categoriesCount = new AtomicInteger();
    private final ChainBuilder searchForFood = CoreDsl
            .feed(jdbcFeeder)
            .exec(
                    HttpDsl.http("google food search")
                            .get("https://google.com/search")
                            .queryParam("q", "#{description}")
                            .check(
                                    css("a:contains('#{description}')", "href").saveAs("searchResultUrl")
                            )
            )
            .exec(session -> {
                System.out.printf("[%d] searchResultUrl = %s%n", categoriesCount.getAndIncrement(), session.getString("searchResultUrl"));
                return session;
            });

    ScenarioBuilder searchScenario = CoreDsl.scenario("search with google")
            .feed(csvFeeder)
            .exec(searchGoogleRequest)
//            .exec(browseResult)
            ;

    ScenarioBuilder searchScenario2 = CoreDsl.scenario("search food with google")
            .feed(jdbcFeeder)
            .exec(searchForFood);

    public SimulationTest() {
        CoreDsl.exec(session -> {
            System.out.println("searchResultUrl = " + session.get("searchResultUrl"));
            return session;
        });

        Config config = ConfigFactory.load("application.properties");
        System.out.println("baseUrl = " + config.getString("baseUrl"));
        System.out.println("jdbc feeder records = " + jdbcFeeder.recordsCount());
        this.setUp(
            /*    searchScenario.injectOpen(
                        CoreDsl.atOnceUsers(1)
                ).protocols(protocol),*/
                searchScenario2.injectOpen(
                        CoreDsl.constantUsersPerSec(2).during(10)
                ).protocols(protocol)
        );
    }
}
