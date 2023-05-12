package spotify.interpret;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import spotify.album.AlbumEntity;

import java.util.List;

@Entity
@RequestScoped
@Named
public class InterpretEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interpretId;
    @Column
    private String name;
    @Column
    private String description;

    @OneToMany(mappedBy = "interpret")
    private List<AlbumEntity> albumEntities;

    public int getInterpretId() {
        return interpretId;
    }

    public void setInterpretId(int interpretId) {
        interpretId = interpretId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AlbumEntity> getAlbumEntities() {
        return albumEntities;
    }

    public void setAlbumEntities(List<AlbumEntity> albumEntities) {
        this.albumEntities = albumEntities;
    }
}
