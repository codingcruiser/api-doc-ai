package com.codingcruiser.apidocai.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public String getUser(@PathVariable String id) {
        return "User " + id;
    }

    @Operation(summary = "Create user")
    @PostMapping
    public String createUser() {
        return "User created";
    }
}
