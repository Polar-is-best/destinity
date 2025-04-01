package com.destinity.app.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.bson.Document;

@ApplicationScoped
public class MongoProducer {

    private final MongoDatabase database;
    private final MongoClient mongoClient;

    @Inject
    public MongoProducer() {
        this.mongoClient = MongoDBConnection.getMongoClient();
        this.database = MongoDBConnection.getDatabase();
    }

    @Produces
    public MongoCollection<Document> produceUserCollection() {
        return database.getCollection("users");
    }

    @Produces
    @SalesCollection
    public MongoCollection<Document> produceSalesCollection() {
        return database.getCollection("sales");
    }


    @PreDestroy
    public void closeMongoConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed on Payara shutdown.");
        }
    }
}
