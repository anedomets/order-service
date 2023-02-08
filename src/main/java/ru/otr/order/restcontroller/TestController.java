package ru.otr.order.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class TestController {

    @GetMapping(path = "/get")
    public ResponseEntity<String> apiGet() {
        return ResponseEntity.ok("/api/get");
    }

    @GetMapping
    public ResponseEntity<String> api() {
        return ResponseEntity.ok("/api");
    }
}
