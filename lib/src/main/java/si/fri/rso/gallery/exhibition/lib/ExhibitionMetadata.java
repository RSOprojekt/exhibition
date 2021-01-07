package si.fri.rso.gallery.exhibition.lib;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionMetadata {

    private Integer exhibitionId;
    private String title;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private ArrayList<Integer> images;
    private Double priceInEuro;
    private Double priceInUsd;


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

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setImages(ArrayList<Integer> images) {
        this.images = images;
    }

    public Double getPriceInEuro() {
        return priceInEuro;
    }

    public void setPriceInEuro(Double priceInEuro) {
        this.priceInEuro = priceInEuro;
    }

    public Double getPriceInUsd() {
        return priceInUsd;
    }

    public void setPriceInUsd(Double priceInUsd) {
        this.priceInUsd = priceInUsd;
    }
}
