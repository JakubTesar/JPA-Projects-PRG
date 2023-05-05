package students;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import recenze.RecenzeEntity;

import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class StudentBean {
    private final FactoryManagerService fMS = new FactoryManagerService();

    private float avgGradeStudent = 0;

    public List<StudentEntity> getAllStudents(){
        EntityManager em = fMS.getEmf().createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<StudentEntity> query = em.createQuery("SELECT students FROM StudentEntity AS students", StudentEntity.class);
        List<StudentEntity> result = query.getResultList();
        em.close();
        return result;
    }

    public void addNewStudent(String firstName, String lastName, String dateOfBirth, int grade){
        EntityManager em = fMS.getEmf().createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        StudentEntity student = new StudentEntity();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setDateOfBirth(dateOfBirth);
        student.setGrade(grade);
        em.persist(student);
        et.commit();
        em.close();
    }
    public void deleteStudent(long id){
        EntityManager em = fMS.getEmf().createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("DELETE FROM StudentEntity WHERE studentId = :id");
        query.setParameter("id", id);
        query.executeUpdate();

        et.commit();
        em.close();
    }
    public void editStudent(String firstName, String lastName, String dateOfBirth, int grade){
        EntityManager em = fMS.getEmf().createEntityManager();

        TypedQuery<StudentEntity> query = em.createQuery("SELECT students FROM StudentEntity AS students WHERE students.studentId = :id", StudentEntity.class);
        query.setParameter("id", getIDParam());
        StudentEntity result = query.getSingleResult();

        EntityTransaction et = em.getTransaction();
        et.begin();

        result.setFirstName(firstName);
        result.setLastName(lastName);
        result.setDateOfBirth(dateOfBirth);
        result.setGrade(grade);

        em.persist(result);
        et.commit();
        em.close();
    }

    public Integer getIDParam() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return Integer.parseInt(params.get("id"));
    }


    public float getAvgGradeStudent() {
        EntityManager em = fMS.getEmf().createEntityManager();

        // SELECT * FROM ArticleEntity
        TypedQuery<StudentEntity> query = em.createQuery("SELECT students FROM StudentEntity AS students", StudentEntity.class);
        List<StudentEntity> result = query.getResultList();
        int sum = 0;
        int count = 0;
        for (StudentEntity s: result) {
            sum += s.getGrade();
            count++;
        }
        if (count != 0) {
            avgGradeStudent = sum / count;
        }
        em.close();
        return avgGradeStudent;
    }

    public void setAvgGradeStudent(float avgGradeStudent) {
        this.avgGradeStudent = avgGradeStudent;
    }
}
