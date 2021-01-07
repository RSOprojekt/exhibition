package si.fri.rso.gallery.exhibition.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.gallery.exhibition.lib.ExhibitionMetadata;
import si.fri.rso.gallery.exhibition.models.converters.ExhibitionMetadataConverter;
import si.fri.rso.gallery.exhibition.models.entities.ExhibitionMetadataEntity;


@RequestScoped
public class ExhibitionMetadataBean {

    private Logger log = Logger.getLogger(ExhibitionMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    @Timed
    public List<ExhibitionMetadata> getExhibitionMetadata() {

        TypedQuery<ExhibitionMetadataEntity> query = em.createNamedQuery(
                "ExhibitionMetadataEntity.getAll", ExhibitionMetadataEntity.class);

        List<ExhibitionMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(ExhibitionMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<ExhibitionMetadata> getExhibitionMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                                                         .build();

        return JPAUtils.queryEntities(em, ExhibitionMetadataEntity.class, queryParameters).stream()
                       .map(ExhibitionMetadataConverter::toDto).collect(Collectors.toList());
    }

    public ExhibitionMetadata getExhibitionMetadata(Integer id) {
        log.info("To bi moglo iti");
        ExhibitionMetadataEntity exhibitionMetadataEntity = em.find(ExhibitionMetadataEntity.class, id);

        if (exhibitionMetadataEntity == null) {
            throw new NotFoundException();
        }

        ExhibitionMetadata exhibitionMetadata = ExhibitionMetadataConverter.toDto(exhibitionMetadataEntity);

        return exhibitionMetadata;
    }

    public ExhibitionMetadata createExhibitionMetadata(ExhibitionMetadata exhibitionMetadata) {

        ExhibitionMetadataEntity exhibitionMetadataEntity = ExhibitionMetadataConverter.toEntity(exhibitionMetadata);

        try {
            beginTx();
            em.persist(exhibitionMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (exhibitionMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ExhibitionMetadataConverter.toDto(exhibitionMetadataEntity);
    }

    public ExhibitionMetadata putExhibitionMetadata(Integer id, ExhibitionMetadata exhibitionMetadata) {

        ExhibitionMetadataEntity c = em.find(ExhibitionMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        ExhibitionMetadataEntity updatedExhibitionMetadataEntity = ExhibitionMetadataConverter.toEntity(exhibitionMetadata);

        try {
            beginTx();
            updatedExhibitionMetadataEntity.setId(c.getId());
            updatedExhibitionMetadataEntity = em.merge(updatedExhibitionMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ExhibitionMetadataConverter.toDto(updatedExhibitionMetadataEntity);
    }

    public boolean deleteExhibitionMetadata(Integer id) {

        ExhibitionMetadataEntity exhibitionMetadata = em.find(ExhibitionMetadataEntity.class, id);

        if (exhibitionMetadata != null) {
            try {
                beginTx();
                em.remove(exhibitionMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
