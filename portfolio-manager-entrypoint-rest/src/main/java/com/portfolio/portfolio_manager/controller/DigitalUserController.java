package com.portfolio.portfolio_manager.controller;

import com.portfolio.portfolio_manager.api.DigitalUserRestApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class DigitalUserController implements DigitalUserRestApi {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
