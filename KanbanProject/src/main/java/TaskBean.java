import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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

    @PreDestroy
    public void onDestroy() {
        emf.close();
    }

}