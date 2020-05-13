package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @GetMapping("/hello/{@id}")
    public String hello(HttpServletRequest request, @PathVariable("@id") String id) {
        System.out.println(request.getRemoteHost());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());

        return "hello world! " + id;
    }
}
