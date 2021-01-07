package si.fri.rso.gallery.exhibition.api.v1.filters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import si.fri.rso.gallery.exhibition.config.RestProperties;

@Provider
@ApplicationScoped
public class InfoFilter implements ContainerRequestFilter {

    @Inject
    private RestProperties restProperties;

    @Override
    public void filter(ContainerRequestContext ctx)  {
        if (!restProperties.getInfoMode() && ctx.getUriInfo().getAbsolutePath().toString().contains("/v1/exhibitions/info")) {
            ctx.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\" : \"Info mode disabled\"}")
                    .build());
        }
    }
}
