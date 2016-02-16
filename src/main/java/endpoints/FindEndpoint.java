package endpoints;


import algorithm.Algorithm;
import algorithm.SensorMock;
import algorithm.park.ParkManager;
import entities.CordNode;
import repositories.DocumentRepository;
import request.FindRequest;
import response.FindListResponse;
import response.ParkResponse;

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
    @Inject
    private SensorMock sensorMock;
    @Inject
    private ParkManager parkManager;

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
        if(sensorMock.ifPark(request.getUserId(),new CordNode(null,Double.parseDouble(request.getLat()),Double.parseDouble(request.getLon())))){
            ParkResponse rl = parkManager.park(request);
            return Response.status(200).entity(rl).build();
        }else{
            FindListResponse rl = algorithm.findParking(request);
            return Response.status(200).entity(rl).build();
        }


    }



}
