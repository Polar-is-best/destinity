package com.destinity.app.sales.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class Sale {

    private ObjectId id;
    private String clienteId;
    private LocalDateTime fecha;
    private double total;
    private String metodoPago; // "transferencia", "tarjeta", "efectivo"
    private String estado; // "pendiente", "completado"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Sale(String clienteId, double total, String metodoPago, String estado) {
        this.id = new ObjectId();
        this.clienteId = clienteId;
        this.fecha = LocalDateTime.now();
        this.total = total;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
    
    public void updateModificationDate() {
        this.updatedAt = LocalDateTime.now();
    }
}
