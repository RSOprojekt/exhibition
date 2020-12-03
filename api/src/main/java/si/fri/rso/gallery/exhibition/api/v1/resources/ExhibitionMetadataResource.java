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
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import si.fri.rso.gallery.exhibition.lib.ExhibitionMetadata;
import si.fri.rso.gallery.exhibition.services.beans.ExhibitionMetadataBean;

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
        JSONObject rezultat = new JSONObject();

        JSONArray clani = new JSONArray();
        clani.put("an2006");
        clani.put("gd4667");
        rezultat.put("clani", clani);

        rezultat.put("opis_projekta", "Nas projekt implementira digitalno galerijo, aplikacijo za ogled slik na spletu.");

        JSONArray mikrostoritve = new JSONArray();
        mikrostoritve.put("TO DO");
        mikrostoritve.put("TO DO");
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
