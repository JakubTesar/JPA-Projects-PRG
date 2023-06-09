package spotify.album;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import spotify.interpret.InterpretEntity;
import students.FactoryManagerService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class AlbumBean {
    private final FactoryManagerService fMS = new FactoryManagerService();

    public List<AlbumEntity> getAlbumsByID() {
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<AlbumEntity> query = em.createQuery("SELECT album FROM AlbumEntity AS album WHERE album.interpret.interpretId = :id", AlbumEntity.class);
        query.setParameter("id", getIDInterpretParam());
        List<AlbumEntity> result = query.getResultList();
        em.close();
        return result;
    }

    public void createAlbum(String name, String description) {
        EntityManager em = fMS.getEmf().createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        AlbumEntity album = new AlbumEntity();
        album.setName(name);
        album.setDescription(description);
        album.setDateOfRelease(LocalDate.now().toString());

        TypedQuery<InterpretEntity> query = em.createQuery("SELECT interpret FROM InterpretEntity AS interpret WHERE interpret.interpretId = :id", InterpretEntity.class);
        query.setParameter("id", getIDInterpretParam());
        List<InterpretEntity> result = query.getResultList();
        album.setInterpret(result.stream().findFirst().get());

        em.persist(album);
        et.commit();
        em.close();
    }

    public void deleteAlbum(long id) {
        EntityManager em = fMS.getEmf().createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("DELETE FROM AlbumEntity WHERE AlbumId = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        et.commit();
        em.close();
    }

    public void editAlbum(String name, String description) {
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<AlbumEntity> query = em.createQuery("SELECT album FROM AlbumEntity AS album WHERE album.AlbumId = :id", AlbumEntity.class);
        query.setParameter("id", getIDAlbumParam());
        AlbumEntity result = query.getSingleResult();

        EntityTransaction et = em.getTransaction();
        et.begin();

        result.setName(name);
        result.setDescription(description);

        em.persist(result);
        et.commit();
        em.close();
    }

    public Integer getInterpretIdFromAlbum(){
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<AlbumEntity> query = em.createQuery("SELECT album FROM AlbumEntity AS album WHERE album.AlbumId = :id", AlbumEntity.class);
        query.setParameter("id", getIDAlbumParam());
        AlbumEntity result = query.getSingleResult();
        em.close();
        return result.getInterpret().getInterpretId();

    }

    public Integer getIDInterpretParam() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return Integer.parseInt(params.get("interpretId"));
    }
    public Integer getIDAlbumParam() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return Integer.parseInt(params.get("albumId"));
    }
}
