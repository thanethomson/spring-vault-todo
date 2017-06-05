package com.thanethomson.demos.todo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.thanethomson.demos.todo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTests {

    private final static Logger logger = LoggerFactory.getLogger(ApiIntegrationTests.class);

    @Value("${todo.admin-user.password}")
    String adminPassword;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testCreateAndUpdateUser() throws Exception {
        // let's try to create a user
        JsonNode json = JsonNodeFactory.instance.objectNode()
                .put("email", "michael@anderson.com")
                .put("password", "somepassword")
                .put("firstName", "Michael")
                .put("lastName", "Anderson");

        ResponseEntity<String> response = TestHelpers.postJson(
                restTemplate.withBasicAuth("admin", adminPassword),
                "/api/users",
                json
        );
        // make sure it was created successfully
        assertEquals(201, response.getStatusCodeValue());

        logger.debug(
                String.format(
                        "Got response: %d\n%s\n",
                        response.getStatusCodeValue(),
                        response.getBody()
                )
        );

        JsonNode result = new ObjectMapper().readTree(response.getBody());
        assertEquals("michael@anderson.com", result.get("email").asText());
        // there should be no password field serialized into the HTTP response
        assertFalse(result.hasNonNull("password"));
        assertEquals("Michael", result.get("firstName").asText());
        assertEquals("Anderson", result.get("lastName").asText());

        int userId = result.get("id").asInt();

        // fetch the user's details using this user's own password
        response = TestHelpers.getJson(
                restTemplate.withBasicAuth("michael@anderson.com", "somepassword"),
                "/api/users"
        );
        assertEquals(200, response.getStatusCodeValue());

        // now update the user's password
        json = JsonNodeFactory.instance.objectNode()
                .put("password", "somenewpassword");
        response = TestHelpers.patchJson(
                restTemplate.withBasicAuth("michael@anderson.com", "somepassword"),
                String.format("/api/users/%d", userId),
                json
        );
        assertEquals(200, response.getStatusCodeValue());

        // check that the new password works
        response = TestHelpers.getJson(
                restTemplate.withBasicAuth("michael@anderson.com", "somenewpassword"),
                "/api/users"
        );
        assertEquals(200, response.getStatusCodeValue());
    }

}
