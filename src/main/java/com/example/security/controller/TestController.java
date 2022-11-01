package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    @GetMapping(path = "/news")
    public List<Long> news() {
        List<Long> longList = new ArrayList<>();
        longList.add(3L);
        longList.add(4L);
        return longList;
    }
}
