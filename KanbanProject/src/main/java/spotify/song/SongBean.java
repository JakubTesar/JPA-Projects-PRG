package spotify.song;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import spotify.album.AlbumEntity;
import spotify.interpret.InterpretEntity;
import students.FactoryManagerService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SongBean {
    private final FactoryManagerService fMS = new FactoryManagerService();

    public List<SongEntity> getSongsByID() {
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<SongEntity> query = em.createQuery("SELECT song FROM SongEntity AS song WHERE song.album.AlbumId = :id", SongEntity.class);
        query.setParameter("id", getIDAlbumParam());
        List<SongEntity> result = query.getResultList();
        em.close();
        return result;
    }

    public void createSong(String name, String description) {
        EntityManager em = fMS.getEmf().createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        SongEntity song = new SongEntity();
        song.setName(name);
        song.setDescription(description);
        song.setDateOfRelease(LocalDate.now().toString());

        TypedQuery<AlbumEntity> query = em.createQuery("SELECT album FROM AlbumEntity AS album WHERE album.AlbumId = :id", AlbumEntity.class);
        query.setParameter("id", getIDAlbumParam());
        List<AlbumEntity> result = query.getResultList();
        song.setAlbum(result.stream().findFirst().get());

        em.persist(song);
        et.commit();
        em.close();
    }

    public void deleteSong(long id) {
        EntityManager em = fMS.getEmf().createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("DELETE FROM SongEntity WHERE SongId = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        et.commit();
        em.close();
    }

    public void editSong(String name, String description) {
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<SongEntity> query = em.createQuery("SELECT song FROM SongEntity AS song WHERE song.SongId = :id", SongEntity.class);
        query.setParameter("id", getIDSongParam());
        SongEntity result = query.getSingleResult();

        EntityTransaction et = em.getTransaction();
        et.begin();

        result.setName(name);
        result.setDescription(description);

        em.persist(result);
        et.commit();
        em.close();
    }
    public Integer getAlbumIdFromSong(){
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<SongEntity> query = em.createQuery("SELECT song FROM SongEntity AS song WHERE song.SongId = :id", SongEntity.class);
        query.setParameter("id", getIDSongParam());
        SongEntity result = query.getSingleResult();
        em.close();
        return result.getAlbum().getAlbumId();

    }

    public Integer getIDAlbumParam() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return Integer.parseInt(params.get("albumId"));
    }
    public Integer getIDSongParam(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return Integer.parseInt(params.get("songId"));
    }
}
