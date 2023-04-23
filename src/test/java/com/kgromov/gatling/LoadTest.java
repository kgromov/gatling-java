package com.kgromov.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import javax.annotation.Nonnull;

import static com.kgromov.gatling.LoadScenario.searchScenario;

public class LoadTest extends Simulation {
    HttpProtocolBuilder protocol = HttpDsl.http.baseUrl("https://google.com");


    public LoadTest() {
        this.setUp(searchScenario.injectOpen(
                CoreDsl.constantUsersPerSec(1).during(1)
        )).protocols(protocol);
    }
}
