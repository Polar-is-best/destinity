
package com.destinity.app.sales.controllers;

import com.destinity.app.sales.dto.SalesDTO;
import com.destinity.app.sales.model.Sale;
import com.destinity.app.sales.services.SaleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Path("/sales")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SalesController {

    @Inject
    private SaleService saleService;

    @POST
    public Response addSale(Sale sale) {
        if (sale.getClienteId() == null || sale.getMetodoPago() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cliente y método de pago son requeridos").build();
        }
        saleService.registerSale(sale);
        return Response.status(Response.Status.CREATED).entity("Venta registrada satisfactoriamente").build();
    }

   /* @GET
    public List<SalesDTO> getSales() {
        return saleService.listSales();
    }
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSales() {
        List<SalesDTO> sales = saleService.listSales();
        System.out.println("Ventas encontradas: " + sales.size()); 
        sales.forEach(s -> System.out.println(s)); 
        return Response.ok(sales).build();
}



    @GET
    @Path("/{id}")
    public Response getSaleById(@PathParam("id") String id) {
        Optional<SalesDTO> sale = saleService.getSaleById(new ObjectId(id));
        return sale.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
    
    

    @PUT
    @Path("/{id}")
    public Response updateSale(@PathParam("id") String id, Sale sale) {
        sale.setId(new ObjectId(id));
        saleService.updateSale(sale);
        return Response.ok(new SalesDTO(sale)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSale(@PathParam("id") String id) {
        saleService.deleteSale(new ObjectId(id));
        return Response.noContent().build();
    }
}
