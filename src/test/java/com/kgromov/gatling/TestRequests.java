package com.kgromov.gatling;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.http.HttpDsl;

public class TestRequests {
    static ChainBuilder searchGoogleRequest = CoreDsl.exec(
            HttpDsl.http("google gatling")
                    .get("/search")
                    .queryParam("q", "gatling")
                    .check(HttpDsl.status().is(200))
    );
}
