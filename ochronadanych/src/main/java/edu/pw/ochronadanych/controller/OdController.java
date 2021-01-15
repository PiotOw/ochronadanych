package edu.pw.ochronadanych.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OdController {

    @GetMapping
    public String hello() {
        System.out.println("hello!");
        return "hello!";
    }
}
