package com.rdaniel.energyplatform.entities.enums;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_CLIENT");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
