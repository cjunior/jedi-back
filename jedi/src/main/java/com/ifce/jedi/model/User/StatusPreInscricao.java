package com.ifce.jedi.model.User;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPreInscricao {
    COMPLETO,
    INCOMPLETO,
    TODOS;

    @JsonCreator
    public static StatusPreInscricao forValue(String value) {
        if (value == null) {
            return null;
        }
        try {
            return StatusPreInscricao.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + value);
        }
    }

    @JsonValue
    public String getValue() {
        return name();
    }
}