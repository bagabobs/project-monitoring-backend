package com.baga.promon.usermanagement.application.port.in;

import com.baga.promon.usermanagement.generated.db.DefaultCatalog;

import java.time.LocalDateTime;

public class SaveEmployeeCommand {
    private String name;
    private String address;
    private LocalDateTime joinDate;

    public SaveEmployeeCommand(String name, String address, LocalDateTime joinDate) {
        this.name = name;
        this.address = address;
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
