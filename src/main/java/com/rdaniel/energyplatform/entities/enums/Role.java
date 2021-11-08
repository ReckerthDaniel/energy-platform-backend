package com.rdaniel.energyplatform.entities.enums;

public enum Role {

    ADMIN("Admin"),
    CLIENT("Client");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
