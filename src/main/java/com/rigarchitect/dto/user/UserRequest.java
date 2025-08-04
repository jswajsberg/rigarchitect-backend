package com.rigarchitect.dto.user;

import java.math.BigDecimal;

public record UserRequest(
        String name,
        String email,
        BigDecimal budget
) {}