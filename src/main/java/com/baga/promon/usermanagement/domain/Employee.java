package com.baga.promon.usermanagement.domain;

import java.time.OffsetDateTime;

public record Employee(Long id, String address, String name, OffsetDateTime joinDate) {}
