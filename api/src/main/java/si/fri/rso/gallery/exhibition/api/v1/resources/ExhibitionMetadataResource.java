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

        return Response.status(Response.Status.CONFLICT).entity(exhibitionMetadata).build();

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
