package com.example.Pathology.Entity;

public enum Role {
    PATIENT,
    ADMIN;

    public boolean equalsIgnoreCase(String roleString) {
        if (roleString == null) {
            return false;
        }
        return this.name().equalsIgnoreCase(roleString);
    }
}
