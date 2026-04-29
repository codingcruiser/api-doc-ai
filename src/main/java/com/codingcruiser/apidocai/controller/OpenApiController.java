package com.codingcruiser.apidocai.controller;

import com.codingcruiser.apidocai.model.ApiEndpoint;
import com.codingcruiser.apidocai.service.OpenApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-doc-ai")
public class OpenApiController {

    private final OpenApiService openApiService;

    public OpenApiController(OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

    @GetMapping("/endpoints")
    public List<ApiEndpoint> getEndpoints() throws Exception {
        return openApiService.extractEndpoints();
    }
}
