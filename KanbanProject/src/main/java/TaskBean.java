import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class TaskBean {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Kanban");

    public void addTask(String name) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        TaskEntity task = new TaskEntity();
        task.setName(name);
        task.setStatus(Status.Backlog);

        em.persist(task);
        et.commit();
        em.close();
    }

    public List<TaskEntity> getBacklogs() {
        EntityManager em = emf.createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<TaskEntity> query = em.createQuery(
                "SELECT task " +
                        "FROM TaskEntity AS task " +
                        "WHERE task.status = Status.Backlog", TaskEntity.class);
        List<TaskEntity> result = query.getResultList();

        em.close();
        return result;
    }

    public List<TaskEntity> getInProgress() {
        EntityManager em = emf.createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<TaskEntity> query = em.createQuery(
                "SELECT task " +
                        "FROM TaskEntity AS task " +
                        "WHERE task.status = Status.InProgress", TaskEntity.class);
        List<TaskEntity> result = query.getResultList();

        em.close();
        return result;
    }

    public List<TaskEntity> getInReview() {
        EntityManager em = emf.createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<TaskEntity> query = em.createQuery(
                "SELECT task " +
                        "FROM TaskEntity AS task " +
                        "WHERE task.status = Status.InReview", TaskEntity.class);
        List<TaskEntity> result = query.getResultList();

        em.close();
        return result;
    }

    public List<TaskEntity> getTest() {
        EntityManager em = emf.createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<TaskEntity> query = em.createQuery(
                "SELECT task " +
                        "FROM TaskEntity AS task " +
                        "WHERE task.status = Status.Test", TaskEntity.class);
        List<TaskEntity> result = query.getResultList();

        em.close();
        return result;
    }

    public List<TaskEntity> getFinished() {
        EntityManager em = emf.createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<TaskEntity> query = em.createQuery(
                "SELECT task " +
                        "FROM TaskEntity AS task " +
                        "WHERE task.status = Status.Finished", TaskEntity.class);
        List<TaskEntity> result = query.getResultList();

        em.close();
        return result;
    }

    public void increment(TaskEntity taskPar) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("UPDATE TaskEntity SET status = :status WHERE taskId = :id");
        query.setParameter("status",taskPar.getStatus().increment(taskPar.getStatus()) );
        query.setParameter("id", taskPar.getTaskId());
        query.executeUpdate();

        et.commit();
        em.close();
    }

    public void decrement(TaskEntity taskPar) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("UPDATE TaskEntity SET status = :status WHERE taskId = :id");
        query.setParameter("status",taskPar.getStatus().decrement(taskPar.getStatus()));
        query.setParameter("id", taskPar.getTaskId());
        query.executeUpdate();

        et.commit();
        em.close();
    }
    public void decrement() {

    }

    @PreDestroy
    public void onDestroy() {
        emf.close();
    }

}