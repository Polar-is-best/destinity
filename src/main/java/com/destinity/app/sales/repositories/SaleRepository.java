
package com.destinity.app.sales.repositories;

import com.destinity.app.database.SalesCollection;
import com.destinity.app.sales.model.Sale;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.types.ObjectId;

@ApplicationScoped
public class SaleRepository {
    
    private MongoCollection<Document> collection;
    
    public SaleRepository() {
    }
    
    @Inject
    public void setCollection(@SalesCollection MongoCollection<Document> saleCollection) {
        this.collection = saleCollection;
    }
    
    public void save(Sale sale) {
        Document saleDoc = new Document("_id", sale.getId() != null ? sale.getId() : new ObjectId())
                .append("clienteId", sale.getClienteId())
                .append("fecha", sale.getFecha())
                .append("total", sale.getTotal())
                .append("metodoPago", sale.getMetodoPago())
                .append("estado", sale.getEstado())
                .append("createdAt", sale.getCreatedAt())
                .append("updatedAt", sale.getUpdatedAt());
        
        collection.insertOne(saleDoc);
    }
    
    public List<Sale> findAll() {
        List<Sale> sales = new ArrayList<>();
        for (Document doc : collection.find()) {
            sales.add(documentToSale(doc));
        }
        return sales;
    }
    
    public Optional<Sale> findById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc != null ? Optional.of(documentToSale(doc)) : Optional.empty();
    }
    
    public void update(Sale sale) {
        Document filter = new Document("_id", sale.getId());
        Document update = new Document("$set", new Document()
                .append("clienteId", sale.getClienteId())
                .append("fecha", sale.getFecha())
                .append("total", sale.getTotal())
                .append("metodoPago", sale.getMetodoPago())
                .append("estado", sale.getEstado())
                .append("updatedAt", sale.getUpdatedAt()));
        
        collection.updateOne(filter, update);
    }
    
    public void deleteById(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }
    
  /*  private Sale documentToSale(Document doc) {
        Sale sale = new Sale();
        sale.setId(doc.getObjectId("_id"));
        sale.setClienteId(doc.getString("clienteId"));
        sale.setFecha(doc.getDate("fecha").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        sale.setTotal(doc.getDouble("total"));
        sale.setMetodoPago(doc.getString("metodoPago"));
        sale.setEstado(doc.getString("estado"));
        sale.setCreatedAt(doc.getDate("createdAt") != null ? doc.getDate("createdAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
        sale.setUpdatedAt(doc.getDate("updatedAt") != null ? doc.getDate("updatedAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
        return sale;
    }
    private Sale documentToSale(Document doc) {
    Sale sale = new Sale();
    sale.setId(doc.getObjectId("_id"));
    sale.setClienteId(doc.getString("clienteId"));
    sale.setFecha(doc.getDate("fecha") != null ? 
        doc.getDate("fecha").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() 
        : null);
    sale.setTotal(doc.getDouble("total"));
    sale.setMetodoPago(doc.getString("metodoPago"));
    sale.setEstado(doc.getString("estado"));

    // Manejar createdAt y updatedAt correctamente
    sale.setCreatedAt(doc.getDate("createdAt") != null ? 
        doc.getDate("createdAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() 
        : null);

    sale.setUpdatedAt(doc.getDate("updatedAt") != null ? 
        doc.getDate("updatedAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() 
        : null);

    return sale;
}

    private Sale documentToSale(Document doc) {
    Sale sale = new Sale();
    sale.setId(doc.getObjectId("_id"));
    sale.setClienteId(doc.getString("clienteId"));
    sale.setFecha(doc.getDate("fecha") != null ? 
        doc.getDate("fecha").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() 
        : null);
    
    // Validación para evitar null en total
    sale.setTotal(doc.getDouble("total") != null ? doc.getDouble("total") : 0.0);

    sale.setMetodoPago(doc.getString("metodoPago"));
    sale.setEstado(doc.getString("estado"));

    sale.setCreatedAt(doc.getDate("createdAt") != null ? 
        doc.getDate("createdAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() 
        : null);

    sale.setUpdatedAt(doc.getDate("updatedAt") != null ? 
        doc.getDate("updatedAt").toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() 
        : null);

    return sale;
}
*/
//    private Sale documentToSale(Document doc) {
//    Sale sale = new Sale();
//    sale.setId(doc.getObjectId("_id"));
//    sale.setClienteId(doc.getString("clienteId"));
//    sale.setFecha(doc.getDate("fecha") != null ? 
//        doc.getDate("fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
//    sale.setTotal(doc.containsKey("total") ? doc.getDouble("total") : 0.0);
//    sale.setMetodoPago(doc.getString("metodoPago"));
//    sale.setEstado(doc.getString("estado"));
//    sale.setCreatedAt(doc.getDate("createdAt") != null ? 
//        doc.getDate("createdAt").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
//    sale.setUpdatedAt(doc.getDate("updatedAt") != null ? 
//        doc.getDate("updatedAt").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
//
//    return sale;
//}
private Sale documentToSale(Document doc) {
    Sale sale = new Sale();
    sale.setId(doc.getObjectId("_id"));
    sale.setClienteId(doc.getString("clienteId"));
    sale.setFecha(doc.getDate("fecha") != null ? 
        doc.getDate("fecha").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);

    // Manejo de "total" para evitar ClassCastException
    if (doc.containsKey("total")) {
        Object totalValue = doc.get("total");
        if (totalValue instanceof Number) {
            sale.setTotal(((Number) totalValue).doubleValue());
        } else {
            sale.setTotal(0.0); // Si el valor no es numérico, asigna 0.0
        }
    } else {
        sale.setTotal(0.0); // Si el campo no existe, asigna 0.0
    }

    sale.setMetodoPago(doc.getString("metodoPago"));
    sale.setEstado(doc.getString("estado"));
    sale.setCreatedAt(doc.getDate("createdAt") != null ? 
        doc.getDate("createdAt").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
    sale.setUpdatedAt(doc.getDate("updatedAt") != null ? 
        doc.getDate("updatedAt").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);

    return sale;
}

}

