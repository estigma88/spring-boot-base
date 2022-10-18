package com.coderstower.springboot.base.springbootbase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = { "test" })
class SpringBootBaseApplicationTests {

	@RegisterExtension
	static WireMockExtension wm1 = WireMockExtension.newInstance()
			.options(wireMockConfig().port(9090))
			.configureStaticDsl(true)
			.failOnUnmatchedRequests(true)
			.build();

	@LocalServerPort
	private Integer port;

	private final Path parentPath = Path.of("src/test/resources/");

	@Test
	void helloWorld(WireMockRuntimeInfo wireMockRuntimeInfo) {
		setStubs("testcases/usecase1", wireMockRuntimeInfo);

		var response = RestAssured.given().port(port).when().get("/helloWorld").then().assertThat().statusCode(200)
				.extract().asString();

		assertEquals("testcases/usecase1/response.json", response);
	}

	@Test
	void helloMars(WireMockRuntimeInfo wireMockRuntimeInfo) {
		setStubs("testcases/usecase2", wireMockRuntimeInfo);

		var response = RestAssured.given().port(port).when().get("/helloWorld").then().assertThat().statusCode(200)
				.extract().asString();

		assertEquals("testcases/usecase2/response.json", response);
	}

	private void setStubs(String path, WireMockRuntimeInfo wireMockRuntimeInfo) {
		wireMockRuntimeInfo.getWireMock().loadMappingsFrom(parentPath.resolve(path).toFile());
	}

	private void assertEquals(String expectedResponsePath, String response) {
		try {
			var mapper = new ObjectMapper();
			var expectedTree = mapper.readTree(Files.readString(parentPath.resolve(expectedResponsePath)));
			var expectedJSONPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedTree);

			var currentTree = mapper.readTree(response);
			var currentJSONPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(currentTree);

			Assertions.assertEquals(expectedJSONPretty, currentJSONPretty);
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
