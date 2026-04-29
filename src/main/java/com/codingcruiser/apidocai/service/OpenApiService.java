package com.codingcruiser.apidocai.service;

import com.codingcruiser.apidocai.model.ApiEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class OpenApiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public OpenApiService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public List<ApiEndpoint> extractEndpoints() throws Exception {

        String json = webClient.get()
                .uri("http://localhost:8080/v3/api-docs")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode root = objectMapper.readTree(json);
        JsonNode paths = root.get("paths");

        List<ApiEndpoint> endpoints = new ArrayList<>();

        if (paths == null || !paths.isObject()) {
            return endpoints; // fail safe
        }

        Iterator<Map.Entry<String, JsonNode>> fields = paths.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();

            String path = entry.getKey();

            // ✅ FILTER INTERNAL APIs
            if (path.startsWith("/api-doc-ai")) {
                continue;
            }

            JsonNode methodsNode = entry.getValue();
            Iterator<Map.Entry<String, JsonNode>> methods = methodsNode.fields();

            while (methods.hasNext()) {
                Map.Entry<String, JsonNode> methodEntry = methods.next();

                String method = methodEntry.getKey().toUpperCase();
                JsonNode methodDetails = methodEntry.getValue();

                // ✅ BETTER SUMMARY FALLBACK
                String summary = methodDetails.has("summary")
                        ? methodDetails.get("summary").asText()
                        : methodDetails.has("description")
                          ? methodDetails.get("description").asText()
                          : "No summary available";

                endpoints.add(new ApiEndpoint(path, method, summary));
            }
        }

        return endpoints;
    }
}
