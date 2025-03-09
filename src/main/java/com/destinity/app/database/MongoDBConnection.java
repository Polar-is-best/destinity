package com.destinity.app.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.util.Properties;

public class MongoDBConnection {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static final String PROPERTIES_FILE = "/config.properties";

    static {
        try {
            String uri = System.getenv("MONGO_URI");
            String dbName = System.getenv("MONGO_DATABASE");

            // Si no hay variables de entorno, usa config.properties
            if (uri == null || dbName == null) {
                Properties props = new Properties();
                props.load(MongoDBConnection.class.getResourceAsStream(PROPERTIES_FILE));

                uri = props.getProperty("MONGO_URI");
                dbName = props.getProperty("MONGO_DATABASE");
            }

            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(dbName);

        } catch (IOException e) {
            throw new RuntimeException("Error loading MongoDB configuration: " + e.getMessage());
        }
    }

    public static MongoDatabase getDatabase() {
        return database;
    }
    
    public static MongoClient getMongoClient() {
        return mongoClient;
    }    

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed.");
        }
    }
}
