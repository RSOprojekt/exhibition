package si.fri.rso.gallery.exhibition.api.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.kumuluz.ee.logs.cdi.Log;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import si.fri.rso.gallery.exhibition.lib.ExhibitionMetadata;
import si.fri.rso.gallery.exhibition.services.beans.ExhibitionMetadataBean;

@Log
@ApplicationScoped
@Path("/exhibitions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExhibitionMetadataResource {

    private Logger log = Logger.getLogger(ExhibitionMetadataResource.class.getName());

    @Inject
    private ExhibitionMetadataBean exhibitionMetadataBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("/info")
    public Response getInfo() {

        log.info("Called /info endpoint.");
        JSONObject rezultat = new JSONObject();

        JSONArray clani = new JSONArray();
        clani.put("an2006");
        clani.put("gd4667");
        rezultat.put("clani", clani);

        rezultat.put("opis_projekta", "Nas projekt implementira digitalno galerijo, aplikacijo za ogled slik na spletu.");

        JSONArray mikrostoritve = new JSONArray();
        mikrostoritve.put("http://20.62.152.21:8080/v1/exhibitions");
        mikrostoritve.put("http://40.88.193.138:8080/v1/gallery");
        rezultat.put("mikrostoritve", mikrostoritve);

        JSONArray github = new JSONArray();
        github.put("https://github.com/RSOprojekt/exhibition");
        github.put("https://github.com/RSOprojekt/imageHandling");
        rezultat.put("github", github);

        JSONArray travis = new JSONArray();
        travis.put("https://travis-ci.com/github/RSOprojekt/exhibition");
        travis.put("https://travis-ci.com/github/RSOprojekt/imageHandling");
        rezultat.put("travis", travis);

        JSONArray dockerhub = new JSONArray();
        dockerhub.put("https://hub.docker.com/r/rsogalerija/exhibition");
        dockerhub.put("https://hub.docker.com/r/rsogalerija/imagehandling");
        rezultat.put("dockerhub", dockerhub);

        return  Response.status(Response.Status.OK).entity(rezultat.toString()).build();
    }

    @GET
    public Response getExhibitionMetadata() {

        List<ExhibitionMetadata> exhibitionMetadata = exhibitionMetadataBean.getExhibitionMetadataFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(exhibitionMetadata).build();
    }

    @GET
    @Path("/{exhibitionMetadataId}")
    public Response getExhibitionMetadata(@PathParam("exhibitionMetadataId") Integer exhibitionMetadataId) {

        ExhibitionMetadata exhibitionMetadata = exhibitionMetadataBean.getExhibitionMetadata(exhibitionMetadataId);

        if (exhibitionMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String price = exhibitionMetadata.getPriceInEuro().toString();
        String currencyFrom = "EUR";
        String currencyTo = "USD";

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://currency26.p.rapidapi.com/convert/" + currencyFrom + "/" + currencyTo + "/" + price)
                .setHeader("x-rapidapi-key", "b3df9d2e25msh9fba6529f904711p1ec1dajsn1098cd943046")
                .setHeader("x-rapidapi-host", "currency26.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    if(response.getStatusCode() == 200) {
                        // Processing of string
                        String[] processing = response.getResponseBody().split(",");
                        String newPrice;
                        if (processing[0].contains("vl")) {newPrice = processing[0];} else {newPrice = processing[1];}
                        newPrice = newPrice.split(":")[1];
                        System.out.println(price + currencyFrom + " = " + newPrice + currencyTo);
                        exhibitionMetadata.setPriceInUsd(Double.parseDouble(newPrice));
                    }
                })
                .join();

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Response.status(Response.Status.OK).entity(exhibitionMetadata).build();
    }

    @POST
    public Response createExhibitionMetadata(ExhibitionMetadata exhibitionMetadata) {

        if ((exhibitionMetadata.getTitle() == null || exhibitionMetadata.getDescription() == null || exhibitionMetadata.getStartTime() == null || exhibitionMetadata.getEndTime() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            exhibitionMetadata = exhibitionMetadataBean.createExhibitionMetadata(exhibitionMetadata);
        }

        return Response.status(Response.Status.OK).entity(exhibitionMetadata).build();

    }

    @PUT
    @Path("{exhibitionMetadataId}")
    public Response putExhibitionMetadata(@PathParam("exhibitionMetadataId") Integer exhibitionMetadataId,
                                          ExhibitionMetadata exhibitionMetadata) {

        exhibitionMetadata = exhibitionMetadataBean.putExhibitionMetadata(exhibitionMetadataId, exhibitionMetadata);

        if (exhibitionMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @DELETE
    @Path("{exhibitionMetadataId}")
    public Response deleteExhibitionMetadata(@PathParam("exhibitionMetadataId") Integer exhibitionMetadataId) {

        boolean deleted = exhibitionMetadataBean.deleteExhibitionMetadata(exhibitionMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
