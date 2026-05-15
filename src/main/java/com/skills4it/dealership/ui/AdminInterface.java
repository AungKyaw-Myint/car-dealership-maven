package com.skills4it.dealership.ui;

import com.skills4it.dealership.data.DealershipFileManager;
import com.skills4it.dealership.models.Dealership;

import java.util.Scanner;

public class AdminInterface {
    private final Scanner scanner;
    private final DealershipFileManager fileManager;
    private Dealership dealership;
    private Helper helper;

    public AdminInterface() {
        this.scanner = new Scanner(System.in);
        this.fileManager = new DealershipFileManager();
        this.helper= new Helper();
    }
}
