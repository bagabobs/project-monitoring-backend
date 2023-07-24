package com.baga.promon.usermanagement;

import org.springframework.boot.SpringApplication;

class UserManagementApplicationTests {
    public static void main(String[] args) {
        SpringApplication.from(UserManagementApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }
}
