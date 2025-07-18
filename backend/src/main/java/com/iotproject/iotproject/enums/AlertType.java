package com.iotproject.iotproject.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AlertType {
    ABOVE, BELOW;

    @JsonCreator
    public static AlertType fromString(String value) {
        return value == null ? null : AlertType.valueOf(value.toUpperCase());
    }
}
