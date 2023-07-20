package com.baga.promon.usermanagement.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class UserManagementUtils {

    private UserManagementUtils() {}

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
