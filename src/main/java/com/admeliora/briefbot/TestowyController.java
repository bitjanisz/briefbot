package com.admeliora.briefbot;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class TestowyController {
    // GET /api/hello
    @GetMapping
    public String hello() {
        return "Hello world";
    }

    // GET /api/hello/{name}
    @GetMapping("/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello " + name;
    }

    // POST /api/hello
    @PostMapping
    public GreetingResponse createGreeting(@RequestBody GreetingRequest request) {
        return new GreetingResponse("Hello " + request.name());
    }

    // Prosty DTO do requestu
    public record GreetingRequest(String name) {}

    // Prosty DTO do responsa
    public record GreetingResponse(String message) {}
}
