
package com.destinity.app.sales.services;

import com.destinity.app.sales.dto.SalesDTO;
import com.destinity.app.sales.model.Sale;
import com.destinity.app.sales.repositories.SaleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

@ApplicationScoped
public class SaleService {

    private SaleRepository saleRepository;
    
    public SaleService() {
    }
    
    @Inject
    public void setSaleRepository(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void registerSale(Sale sale) {
        saleRepository.save(sale);
    }

    /* public List<SalesDTO> listSales() {
        return saleRepository.findAll().stream()
                .map(SalesDTO::new)
                .collect(Collectors.toList());
    }
    */
public List<SalesDTO> listSales() {
    List<Sale> sales = saleRepository.findAll();
    sales.forEach(s -> System.out.println("Venta encontrada: " + s));
    return sales.stream().map(SalesDTO::new).collect(Collectors.toList());
}

    
    public Optional<SalesDTO> getSaleById(ObjectId id) {
        return saleRepository.findById(id)
                .map(SalesDTO::new);
    }

    public void updateSale(Sale sale) {
        sale.setUpdatedAt(java.time.LocalDateTime.now());
        saleRepository.update(sale);
    }

    public void deleteSale(ObjectId saleId) {
        saleRepository.deleteById(saleId);
    }
}
