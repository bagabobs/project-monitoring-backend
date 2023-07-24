package com.baga.promon.usermanagement.application.port.in;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record SaveEmployeeCommand(String name, String address,
                                  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date joinDate) {
}
