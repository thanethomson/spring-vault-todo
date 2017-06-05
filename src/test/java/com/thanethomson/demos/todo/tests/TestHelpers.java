package com.thanethomson.demos.todo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Collections;

public class TestHelpers {

    public static ResponseEntity<String> postJson(
            TestRestTemplate restTemplate,
            String path,
            JsonNode json
    ) throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Accept", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        return restTemplate.exchange(new RequestEntity<String>(
                json.toString(),
                headers,
                HttpMethod.POST,
                new URI(path)),
                String.class
        );
    }

    public static ResponseEntity<String> getJson(
            TestRestTemplate restTemplate,
            String path
    ) throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Accept", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        return restTemplate.exchange(new RequestEntity<String>(
                headers,
                HttpMethod.GET,
                new URI(path)
        ), String.class);
    }

    public static ResponseEntity<String> patchJson(
            TestRestTemplate restTemplate,
            String path,
            JsonNode json
    ) throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Accept", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        headers.put("Content-Type", Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        return restTemplate.exchange(new RequestEntity<String>(
                        json.toString(),
                        headers,
                        HttpMethod.PATCH,
                        new URI(path)),
                String.class
        );
    }

}
