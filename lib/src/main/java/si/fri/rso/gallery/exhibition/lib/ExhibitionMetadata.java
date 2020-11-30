package si.fri.rso.gallery.exhibition.lib;

import java.time.Instant;

public class ExhibitionMetadata {

    private Integer exhibitionId;
    private String title;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Integer images;


    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getImages() {
        return images;
    }

    public void setImages(Integer images) {
        this.images = images;
    }
}
