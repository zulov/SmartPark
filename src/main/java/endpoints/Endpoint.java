package endpoints;


import entities.Document;
import repositories.DocumentRepository;
import response.ResponseList;

import javax.inject.Inject;
import javax.print.Doc;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


@Path("/document")
public class Endpoint {

    @Inject
    DocumentRepository documentRepository;

    @GET
    @Path("postgres")
    @Produces("application/json")
    public Response getAllPs() {
        long millisStart = Calendar.getInstance().getTimeInMillis();
        List<Document> documents = documentRepository.listAllPostgres();
        long millisEnd = Calendar.getInstance().getTimeInMillis();
        ResponseList rl= new ResponseList();
        rl.setHits(documents.size());
        rl.setDocuments(documents);
        rl.setTime(millisEnd-millisStart);

        return Response.status(200).entity(rl).build();
    }


    @POST
    @Path("add/{name}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(@PathParam("name") String name, Document document) throws IOException {
        Calendar cal = Calendar.getInstance();
        Random generator = new Random();

        cal.set( cal.YEAR, generator.nextInt(80)+1930 );
        cal.set( cal.MONTH, generator.nextInt(11)+1 );
        cal.set( cal.DATE, generator.nextInt(27)+1 );
        java.sql.Date sqlDate = new java.sql.Date( cal.getTime().getTime() );
        document.setTimestamp(sqlDate);
        documentRepository.addToPS(document);

        return Response.status(200).build();
    }


    @POST
    @Path("search/postgres")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response findPs(@FormParam("text") String text,@FormParam("field") String field) throws IOException {
        ResponseList rl =    documentRepository.findPs(text,field);

        return Response.status(200).entity(rl).build();
    }


}
