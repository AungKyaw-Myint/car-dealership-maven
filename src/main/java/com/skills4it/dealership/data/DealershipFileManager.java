package com.skills4it.dealership.data;

import com.skills4it.dealership.models.Contract;
import com.skills4it.dealership.models.Dealership;
import com.skills4it.dealership.models.Vehicle;
import com.skills4it.dealership.models.enums.VehicleType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DealershipFileManager {
    private static final Path INVENTORY_PATH = Path.of("src", "main", "resources", "inventory.csv");
    private static final Path CONTRACTS_PATH = Path.of("src", "main", "resources", "contracts.csv");
    private static final String DELIMITER = "\\|";
    private static final String INVENTORY_FILENAME= "inventory";
    private static final String CONTRACTS_FILENAME= "contracts";

    public Dealership getDealership() {
        ensureFileExists(INVENTORY_PATH);

        try (BufferedReader reader = Files.newBufferedReader(INVENTORY_PATH)) {
            String dealershipLine = reader.readLine();

            if (dealershipLine == null || dealershipLine.isBlank()) {
                throw new IllegalStateException("The inventory file is empty.");
            }

            String[] dealershipFields = dealershipLine.split(DELIMITER);
            if (dealershipFields.length != 3) {
                throw new IllegalStateException("Invalid dealership line. Expected: name|address|phone");
            }

            Dealership dealership = new Dealership(dealershipFields[0], dealershipFields[1], dealershipFields[2]);

            String vehicleLine;
            while ((vehicleLine = reader.readLine()) != null) {
                if (vehicleLine.isBlank()) {
                    continue;
                }
                dealership.addVehicle(parseVehicle(vehicleLine));
            }

            return dealership;
        } catch (IOException e) {
            throw new IllegalStateException("Could not read inventory file: " + INVENTORY_PATH, e);
        }
    }

    public void saveDealership(Dealership dealership) {
        ensureFileExists(INVENTORY_PATH);
        createBackupFile(INVENTORY_PATH, INVENTORY_FILENAME);

        try (BufferedWriter writer = Files.newBufferedWriter(INVENTORY_PATH)) {
            writer.write(dealership.toCsvHeaderLine());
            writer.newLine();

            List<Vehicle> vehicles = dealership.getAllVehicles();
            for (Vehicle vehicle : vehicles) {
                writer.write(vehicle.toCsvLine());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not save inventory file: " + INVENTORY_PATH, e);
        }
    }

    private Vehicle parseVehicle(String vehicleLine) {
        String[] fields = vehicleLine.split(DELIMITER);
        if (fields.length != 8) {
            throw new IllegalStateException("Invalid vehicle line. Expected 8 fields: " + vehicleLine);
        }

        try {
            int vin = Integer.parseInt(fields[0]);
            int year = Integer.parseInt(fields[1]);
            String make = fields[2];
            String model = fields[3];
            VehicleType vehicleType = VehicleType.fromString(fields[4])
                    .orElseThrow(() -> new IllegalStateException("Invalid vehicle type in vehicle line: " + vehicleLine));
            String color = fields[5];
            int odometer = Integer.parseInt(fields[6]);
            double price = Double.parseDouble(fields[7]);

            return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid number in vehicle line: " + vehicleLine, e);
        }
    }

    private void ensureFileExists(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalStateException("File not found at: " + path.toAbsolutePath());
        }
    }

    private void createBackupFile(Path path, String fileName) {
        try {
            Path backupDirectory = path.getParent().resolve("backups");
            Files.createDirectories(backupDirectory);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            Path backupPath = backupDirectory.resolve(fileName +"-"+ timestamp + ".csv");

            Files.copy(path, backupPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create "+fileName+" backup file.", e);
        }
    }

    public void saveContract(Contract contract) {


        try (BufferedWriter writer = Files.newBufferedWriter(
                CONTRACTS_PATH,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            createBackupFile(CONTRACTS_PATH, CONTRACTS_FILENAME);
            writer.write(contract.toCsvHeaderLine());

            Vehicle vehicle = contract.getVehicle();
            writer.write(vehicle.toCsvLine());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException("Could not save contract file: " + CONTRACTS_PATH, e);
        }
    }
}
