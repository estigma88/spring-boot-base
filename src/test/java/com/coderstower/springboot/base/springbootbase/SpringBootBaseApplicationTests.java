package com.coderstower.springboot.base.springbootbase;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 9090)
@ActiveProfiles(profiles = {"test"})
class SpringBootBaseApplicationTests {
    @LocalServerPort
    private Integer port;

    @Test
    void helloWorld(WireMockRuntimeInfo wireMockRuntimeInfo) {
        setStubs("testcases/usecase1", wireMockRuntimeInfo);

        var response = RestAssured
                .given()
                .port(port)
                .when()
                .get("/helloWorld")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .asString();

        response.toString();
    }

    @Test
    void helloMars(WireMockRuntimeInfo wireMockRuntimeInfo) {
        setStubs("testcases/usecase2", wireMockRuntimeInfo);

        var response = RestAssured
                .given()
                .port(port)
                .when()
                .get("/helloWorld")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .asString();

        response.toString();
    }

    private static void setStubs(String path, WireMockRuntimeInfo wireMockRuntimeInfo) {
        wireMockRuntimeInfo.getWireMock()
                .loadMappingsFrom(
                        Path.of("src/test/resources/" + path).toFile());
    }

}
