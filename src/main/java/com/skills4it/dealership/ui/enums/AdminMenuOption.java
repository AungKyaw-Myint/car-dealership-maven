package com.skills4it.dealership.ui.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AdminMenuOption {
    ALL_CONTRACTS(1, "All contracts list"),
    SALES_CONTRACTS(2, "Sale contracts list"),
    LEASE_CONTRACTS(3, "Lease contracts list"),
    USER_CONSOLE(4, "User console"),
    QUIT(99, "Quit");

    private final int code;
    private final String label;

    AdminMenuOption(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static Optional<AdminMenuOption> fromCode(int code) {
        return Arrays.stream(values())
                .filter(option -> option.code == code)
                .findFirst();
    }
}
