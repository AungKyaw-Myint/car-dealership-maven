package com.skills4it.dealership.ui;

import com.skills4it.dealership.data.DealershipFileManager;
import com.skills4it.dealership.models.Contract;
import com.skills4it.dealership.models.Dealership;
import com.skills4it.dealership.models.LeaseContract;
import com.skills4it.dealership.models.SalesContract;
import com.skills4it.dealership.ui.enums.AdminMenuOption;
import com.skills4it.dealership.ui.enums.ContractOption;
import com.skills4it.dealership.ui.enums.MenuOption;

import java.util.List;
import java.util.Scanner;

public class AdminInterface {
    private final Scanner scanner;
    private final DealershipFileManager fileManager;
    private List<Contract> contracts;
    private Helper helper;

    public AdminInterface() {
        this.scanner = new Scanner(System.in);
        this.fileManager = new DealershipFileManager();
        this.helper= new Helper();
    }

    public void display(){
        init();
        AdminMenuOption selectedOption;
        do {
            displayHeader();
            displayMenu();
            int choice = helper.readInt("Choose an option: ");
            selectedOption = AdminMenuOption.fromCode(choice).orElse(null);
            handleMenuChoice(selectedOption);
        } while (selectedOption != AdminMenuOption.QUIT);

        System.out.println("Goodbye!");
    }
    private void init() {
        this.contracts = fileManager.getContracts();
    }
    private void displayHeader() {
        System.out.println();
        System.out.println("=============================================");
        System.out.println("================ADMIN CONSOLE================");
        System.out.println("=============================================");
    }
    private void displayMenu() {
        for (AdminMenuOption option : AdminMenuOption.values()) {
            System.out.printf("%-3d - %s%n", option.getCode(), option.getLabel());
        }
        System.out.println();
    }
    private void handleMenuChoice(AdminMenuOption option) {
        if (option == null) {
            System.out.println("Invalid option. Please try again.");
            return;
        }

        switch (option) {
            case ALL_CONTRACTS-> contractsList(true, true);
            case SALES_CONTRACTS -> contractsList(true, false);
            case LEASE_CONTRACTS -> contractsList(false,true);
            case USER_CONSOLE -> new UserInterface().display();
            case QUIT -> { }
        }
    }

    private void contractsList(boolean isSale, boolean isLease){
//        contracts.stream().forEach(System.out::println);
        contracts.forEach(contract ->
        {
            if(isLease && contract instanceof LeaseContract){
                System.out.println(ContractOption.LEASE + " "+contract);
            } else if (isSale && contract instanceof SalesContract) {
                System.out.println(ContractOption.SALES + " "+contract);
            }
        });
    }
}
