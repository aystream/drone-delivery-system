package com.musala.services.drone.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DataInitializer {

    private final DataSource dataSource;

    public DataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //TODO remove this method after implementing the database and system migration
    @PostConstruct
    public void init() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO drone (serial_number, model, weight_limit, battery_capacity, state) VALUES " +
                    "('DRONE_BH071', 'LIGHTWEIGHT', 150, 100, 'IDLE'), " +
                    "('DRONE_HLI7J', 'LIGHTWEIGHT', 150, 100, 'IDLE'), " +
                    "('DRONE_BH072', 'MIDDLEWEIGHT', 250, 100, 'IDLE'), " +
                    "('DRONE_HLI7K', 'MIDDLEWEIGHT', 250, 100, 'IDLE'), " +
                    "('DRONE_PLI8A', 'CRUISERWEIGHT', 350, 100, 'IDLE'), " +
                    "('DRONE_OLI8B', 'CRUISERWEIGHT', 350, 100, 'IDLE'), " +
                    "('DRONE_QLI8C', 'HEAVYWEIGHT', 500, 100, 'IDLE'), " +
                    "('DRONE_RLI8D', 'HEAVYWEIGHT', 500, 100, 'IDLE'), " +
                    "('DRONE_20L29', 'MIDDLEWEIGHT', 250, 100, 'IDLE'), " +
                    "('DRONE_30L2A', 'LIGHTWEIGHT', 150, 100, 'IDLE');";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}