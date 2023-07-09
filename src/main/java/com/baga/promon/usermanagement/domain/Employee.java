package com.baga.promon.usermanagement.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Employee(BigDecimal id, String address, String name, OffsetDateTime joinDate) {}
