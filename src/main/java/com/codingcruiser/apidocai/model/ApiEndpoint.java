package com.codingcruiser.apidocai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiEndpoint {
    private String path;
    private String method;
    private String summary;
}
