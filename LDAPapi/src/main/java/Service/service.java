package Service;

import java.util.ArrayList;
import java.util.Map;

import Data.trancategory;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/entry")
public interface service {
    
    @Path("/add")
    @POST
    public Response addEntry(ArrayList<trancategory> tranCategory);

    @GET
    @Path("/search")
    public Response searchEntry(Map<String, String> searchCriteria);

    @DELETE
    @Path("/delete/{transactionGroupId}")
    public Response deleteEntry(@PathParam("transactionGroupId") String transactionGroupId);

    @PUT
    @Path("/update/{transactionGroupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEntryAttributes(@PathParam("transactionGroupId") String transactionGroupId, Map<String, String> attributes);

    
}