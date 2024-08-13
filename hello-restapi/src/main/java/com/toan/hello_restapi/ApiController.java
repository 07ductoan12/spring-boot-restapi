package com.toan.hello_restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** ApiController */
@RestController
public class ApiController {
    @GetMapping("/api/hello")
    public Response hello() {
        return new Response("Hello World, is the first REST API. The current time is:");
    }
}
