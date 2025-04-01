package com.destinity.app.sales.dto;

import com.destinity.app.sales.model.Sale;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SalesDTO {

    private String id;
    private String clienteId;
    private LocalDateTime fecha;
    private double total;
    private String metodoPago;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public SalesDTO(Sale sale){
        this.id = sale.getId().toHexString();
        this.clienteId = sale.getClienteId();
        this.fecha = sale.getFecha();
        this.total = sale.getTotal();
        this.metodoPago = sale.getMetodoPago();
        this.estado = sale.getEstado();
        this.createdAt = sale.getCreatedAt();
        this.updatedAt = sale.getUpdatedAt();
    }
}
