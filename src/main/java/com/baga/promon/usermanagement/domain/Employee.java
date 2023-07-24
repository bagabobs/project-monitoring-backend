package com.baga.promon.usermanagement.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Employee(BigDecimal id, String address, String name, LocalDateTime joinDate) {}
