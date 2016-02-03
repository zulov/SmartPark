package endpoints;


import algorithm.Algorithm;
import repositories.DocumentRepository;
import request.FindRequest;
import response.FindListResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("/find")
public class FindEndpoint {

    @Inject
    private DocumentRepository documentRepository;
    @Inject
    private Algorithm algorithm;


    @POST
    @Path("find")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findPath(FindRequest request) throws IOException {
        FindListResponse rl = algorithm.find(request);

        return Response.status(200).entity(rl).build();
    }

    @POST
    @Path("parking")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findParking(FindRequest request) throws IOException {
        FindListResponse rl = algorithm.findParking(request);

        return Response.status(200).entity(rl).build();
    }



}
