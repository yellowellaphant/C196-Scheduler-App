package com.example.c196schedulingapp.Helper;

import java.util.UUID;

public class GenerateID {
    public static int generateUniqueID() {
        // Generate a UUID
        UUID uuid = UUID.randomUUID();
        // Get the least significant bits of the UUID as an integer
        int uniqueID = (int) uuid.getLeastSignificantBits();
        // Ensure the generated ID is positive
        uniqueID = Math.abs(uniqueID);
        return uniqueID;
    }


    public static void main(String[] args) {
        // Generate a unique ID
        int id = generateUniqueID();
        System.out.println("Unique ID: " + id);
    }

}
