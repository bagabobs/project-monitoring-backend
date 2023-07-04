package com.baga.promon.usermanagement.application.port.in;

import java.time.LocalDateTime;

public final class SaveEmployeeCommand {
    private final String name;
    private final String address;
    private final LocalDateTime joinDate;

    public SaveEmployeeCommand(final String name, final String address, final LocalDateTime joinDate) {
        this.name = name;
        this.address = address;
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }
}
