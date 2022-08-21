package com.company;

import com.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class TestDataInit implements CommandLineRunner {

    GoodsService goodsService;
    SalesService salesService;
    Warehouse1Service warehouse1Service;
    Warehouse2Service warehouse2Service;

    CustomUserDetailsService customUserDetailsService;
    PasswordEncoder pwdEncoder;

    @Autowired
    public TestDataInit(GoodsService goodsService, SalesService salesService,
                        Warehouse1Service warehouse1Service, Warehouse2Service warehouse2Service,
                        CustomUserDetailsService customUserDetailsService, PasswordEncoder pwdEncoder) {
        this.goodsService = goodsService;
        this.salesService = salesService;
        this.warehouse1Service = warehouse1Service;
        this.warehouse2Service = warehouse2Service;
        this.customUserDetailsService = customUserDetailsService;
        this.pwdEncoder = pwdEncoder;
    }

    @Override
    public void run(String... args) {
        //customUserDetailsService.createUser("Admin", pwdEncoder.encode("Admin"), Collections.singletonList("ROLE_ADMIN"));
    }
}
