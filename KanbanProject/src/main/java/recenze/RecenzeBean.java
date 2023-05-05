package recenze;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import kanban.TaskEntity;
import recenze.RecenzeEntity;

import java.util.List;
@Named
@RequestScoped
public class RecenzeBean {

    @Inject
    private RecenzeDTO dto;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("recenze");

    public void addRecenze(String content) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        RecenzeEntity task = new RecenzeEntity();
        task.setContent(content);
        em.persist(task);
        et.commit();
        em.close();
    }
    public List<RecenzeEntity> getAllrecenze() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<recenze.RecenzeEntity> query = em.createQuery(
                "SELECT recenze " +
                        "FROM RecenzeEntity AS recenze ", RecenzeEntity.class);
        List<RecenzeEntity> result = query.getResultList();

        em.close();
        return result;
    }

    public void deleteRecenze(long Id){
        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createQuery("DELETE FROM RecenzeEntity WHERE recenzeId = :id");
        query.setParameter("id", Id);
        query.executeUpdate();
        et.commit();

        em.close();
    }
    public void updateRecenze (long Id){
        EntityManager em = emf.createEntityManager();

        TypedQuery<RecenzeEntity> query = em.createQuery("SELECT recenze FROM RecenzeEntity AS recenze WHERE recenze.recenzeId = :id", RecenzeEntity.class);
        query.setParameter("id", Id);
        RecenzeEntity result = query.getSingleResult();

        EntityTransaction et = em.getTransaction();
        et.begin();

        result.setContent(dto.getContent());
        System.out.println("HERE");
        System.out.println("HERE");
        System.out.println("HERE");
        System.out.println(result);
        System.out.println(result.getRecenzeId());
        System.out.println(dto.getContent());
        em.persist(result);
        et.commit();
        em.close();
    }
}
