package si.fri.rso.gallery.exhibition.models.converters;

import si.fri.rso.gallery.exhibition.lib.ExhibitionMetadata;
import si.fri.rso.gallery.exhibition.models.entities.ExhibitionMetadataEntity;

public class ExhibitionMetadataConverter {

    public static ExhibitionMetadata toDto(ExhibitionMetadataEntity entity) {

        ExhibitionMetadata dto = new ExhibitionMetadata();
        dto.setExhibitionId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setImages(entity.getImages());
        dto.setPriceInEuro(entity.getPriceInEuro());

        return dto;

    }

    public static ExhibitionMetadataEntity toEntity(ExhibitionMetadata dto) {

        ExhibitionMetadataEntity entity = new ExhibitionMetadataEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setImages(dto.getImages());
        entity.setPriceInEuro(dto.getPriceInEuro());

        return entity;

    }

}
