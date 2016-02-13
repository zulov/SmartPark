package endpoints;

import algorithm.park.ParkManager;
import request.ParkRequest;
import response.ParkListResponse;
import response.ParkResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Tomek on 2016-02-03.
 */
@Path("/parking")
public class ParkEndpoint {
    @Inject
    private ParkManager parkManager;

    @POST
    @Path("")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getClosest(ParkRequest request) throws IOException {
        ParkListResponse plr = parkManager.getParkings(request.getLat(), request.getLon());
        return Response.status(200).entity(plr).build();
    }

    @POST
    @Path("park")
    @Consumes("application/json")
    @Produces("application/json")
    public Response park(ParkRequest request) throws IOException {
        ParkResponse rl = parkManager.park(request);

        return Response.status(200).entity(rl).build();
    }

    @POST
    @Path("unpark")
    @Consumes("application/json")
    @Produces("application/json")
    public Response unpark(ParkRequest request) throws IOException {
        ParkResponse rl = parkManager.unpark(request);

        return Response.status(200).entity(rl).build();
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public Response getAll() throws IOException {
        ParkListResponse plr = parkManager.getAllParkings();
        return Response.status(200).entity(plr).build();
    }

}
