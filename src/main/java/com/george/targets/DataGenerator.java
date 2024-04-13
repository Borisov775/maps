package com.george.targets;

import java.io.FileWriter;
import java.io.IOException;

public class DataGenerator {

    public static void main(String[] args) {

        // Array of major US cities with their respective latitude and longitude
        String[][] cities = {
            {"New York", "40.712776", "-74.005974"},
            {"Los Angeles", "34.052235", "-118.243683"},
            {"Chicago", "41.878113", "-87.629799"},
            {"Houston", "29.760427", "-95.369804"},
            {"Phoenix", "33.448376", "-112.074036"},
            {"Philadelphia", "39.952583", "-75.165222"},
            {"San Antonio", "29.424122", "-98.493629"},
            {"San Diego", "32.715738", "-117.161084"},
            {"Dallas", "32.776665", "-96.796989"},
            {"San Jose", "37.338208", "-121.886329"},
            {"Austin", "30.267153", "-97.743057"},
            {"Jacksonville", "30.332184", "-81.655651"},
            {"San Francisco", "37.774929", "-122.419418"}
        };

        // Define the CSV file header
        String header = "City,Latitude,Longitude\n";

        // Define the path for the CSV file
        String filePath = "src/main/resources/us_cities_coordinates.csv";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Write the header to the CSV file
            fileWriter.append(header);

            // Write the city data to the CSV file
            for (String[] cityData : cities) {
                fileWriter.append(String.join(",", cityData));
                fileWriter.append("\n"); // New line after each row
            }

            System.out.println("CSV file created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
