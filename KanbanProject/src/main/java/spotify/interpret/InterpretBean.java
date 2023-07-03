package spotify.interpret;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import students.FactoryManagerService;

import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class InterpretBean {

    private final FactoryManagerService fMS = new FactoryManagerService();
    public void createInterpret(String name, String description){
        EntityManager em = fMS.getEmf().createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        InterpretEntity interpret = new InterpretEntity();
        interpret.setName(name);
        interpret.setDescription(description);
        em.persist(interpret);
        et.commit();
        em.close();
    }
    public List<InterpretEntity> getAllInterprets(){
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<InterpretEntity> query = em.createQuery("SELECT interprets FROM InterpretEntity AS interprets", InterpretEntity.class);
        List<InterpretEntity> result = query.getResultList();
        em.close();
        return result;
    }
    public void deleteInterpret(long id){
        EntityManager em = fMS.getEmf().createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("DELETE FROM InterpretEntity ie WHERE ie.interpretId = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        et.commit();
        em.close();
    }
    public void editInterpret(String name, String description){
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<InterpretEntity> query = em.createQuery("SELECT interprets FROM InterpretEntity AS interprets WHERE interprets.interpretId = :id", InterpretEntity.class);
        query.setParameter("id", getIDParam());
        InterpretEntity result = query.getSingleResult();

        EntityTransaction et = em.getTransaction();
        et.begin();


        result.setName(name);
        result.setDescription(description);

        em.persist(result);
        et.commit();
        em.close();
    }
    public Integer getIDParam() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return Integer.parseInt(params.get("id"));
    }

}
