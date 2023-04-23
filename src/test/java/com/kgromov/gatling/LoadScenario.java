package com.kgromov.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;

import static com.kgromov.gatling.TestRequests.searchGoogleRequest;
import static io.gatling.javaapi.core.CoreDsl.csv;

public class LoadScenario {

    static FeederBuilder<String> csvFeeder = csv("").random();
    static ScenarioBuilder searchScenario = CoreDsl.scenario("search with google")
            .exec(searchGoogleRequest)
            .feed(csvFeeder);
}
