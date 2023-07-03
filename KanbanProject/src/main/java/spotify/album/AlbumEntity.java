package spotify.album;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import spotify.interpret.InterpretEntity;
import spotify.song.SongEntity;

import java.util.List;

@Entity
@RequestScoped
@Named
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AlbumId;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String dateOfRelease;

    @ManyToOne()
    private InterpretEntity interpret;

    public List<SongEntity> getSongEntities() {
        return songEntities;
    }

    public void setSongEntities(List<SongEntity> songEntities) {
        this.songEntities = songEntities;
    }

    @OneToMany(mappedBy ="album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongEntity> songEntities;

    public int getAlbumId() {
        return AlbumId;
    }

    public void setAlbumId(int albumId) {
        AlbumId = albumId;
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

    public String getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(String dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public InterpretEntity getInterpret() {
        return interpret;
    }

    public void setInterpret(InterpretEntity interpret) {
        this.interpret = interpret;
    }
}
