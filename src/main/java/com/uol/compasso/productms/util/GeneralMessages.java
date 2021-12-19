package com.uol.compasso.productms.util;

public enum GeneralMessages {
    SUCCESS_TO_DELETE("Item deleted successfully");

    private final String message;

    GeneralMessages (String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
