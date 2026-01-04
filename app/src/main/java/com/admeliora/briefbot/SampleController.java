package com.admeliora.briefbot;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String getSample() {
        return "Hello from BriefBot";
    }
}

