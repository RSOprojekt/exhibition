package si.fri.rso.gallery.exhibition.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "exhibition_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "ExhibitionMetadataEntity.getAll",
                        query = "SELECT im FROM ExhibitionMetadataEntity im")
        })
public class ExhibitionMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "startTime")
    private Instant startTime;

    @Column(name = "endTime")
    private Instant endTime;

    @Column(name = "images")
    private Integer images;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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