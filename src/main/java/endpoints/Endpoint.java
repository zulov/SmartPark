package endpoints;


import algorithm.Algorithm;
import entities.CordNode;
import entities.AbstractEntity;
import repositories.DocumentRepository;
import request.Request;
import response.ResponseList;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;


@Path("/test")
public class Endpoint {

    @Inject
    private DocumentRepository documentRepository;
    @Inject
    private Algorithm algorithm;

    @POST
    @Path("find")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findPath(Request request) throws IOException {
        ResponseList rl = algorithm.find(request);

        return Response.status(200).entity(rl).build();
    }

    @POST
    @Path("findParking")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findParking(Request request) throws IOException {
        ResponseList rl = algorithm.findParking(request);

        return Response.status(200).entity(rl).build();
    }

}
