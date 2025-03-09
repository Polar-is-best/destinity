package com.destinity.app.users.repositories;

import com.destinity.app.users.model.User;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.types.ObjectId;

@ApplicationScoped
public class UserRepository{
    
    private MongoCollection<Document> collection;
    
    public UserRepository() {
    }
    
    @Inject
    public void setColletion(MongoCollection<Document> userCollection) {
        this.collection = userCollection;
    }
    
    public void save(User user) {
        Document userDoc = new Document(
                "_id", user.getId() != null ? user.getId() : new ObjectId())
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("middleName", user.getMiddleName() != null ? user.getMiddleName(): "")
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("role", user.getRole())
                .append("status", user.getStatus())
                .append("createdAt", user.getCreatedAt())
                .append("updatedAt", user.getUpdatedAt());
        
        if (user.getEmployeeData() != null && user.getRole().equals("employee")) {
            Document employeeDoc = new Document()
                    .append("position", user.getEmployeeData().getPosition())
                    .append("department", user.getEmployeeData().getDepartment())
                    .append("salary", user.getEmployeeData().getSalary())
                    .append("position", user.getEmployeeData().getHireDate());
            userDoc.append("employeeData", employeeDoc);
        }
        
        if (user.getProviderData()!= null && user.getRole().equals("provider")) {
            Document providerDoc = new Document()
                    .append("company", user.getProviderData().getCompany())
                    .append("serviceType", user.getProviderData().getServiceType())
                    .append("contactPhone", user.getProviderData().getContactPhone());
            userDoc.append("providerData", providerDoc);
        }

        collection.insertOne(userDoc);
    }
    
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(documentToUser(doc));
        }
        return users;
    }
    
    public Optional<User> findById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc != null ? Optional.of(documentToUser(doc)) : Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        Document doc = collection.find(new Document("email", email)).first();
        return doc != null ? Optional.of(documentToUser(doc)) : Optional.empty();
    }

    public void update(User user) {
        Document filter = new Document("_id", user.getId());
        Document update = new Document("$set", new Document()
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("middleName", user.getMiddleName())
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("role", user.getRole())
                .append("status", user.getStatus())
                .append("updatedAt", user.getUpdatedAt()));

        collection.updateOne(filter, update);
    }

    public void deleteById(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }

    private User documentToUser(Document doc) {
        User user = new User();
        user.setId(doc.getObjectId("_id"));
        user.setFirstName(doc.getString("firstName"));
        user.setLastName(doc.getString("lastName"));
        user.setMiddleName(doc.getString("middleName"));
        user.setEmail(doc.getString("email"));
        user.setPassword(doc.getString("password"));
        user.setRole(doc.getString("role"));
        user.setStatus(doc.getString("status"));
        user.setCreatedAt(doc.getDate("createdAt") != null ? doc.getDate("createdAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
        user.setUpdatedAt(doc.getDate("updatedAt") != null ? doc.getDate("updatedAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
        return user;
    }
}
