package spotify;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FactoryManagerService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("students");

    public EntityManagerFactory getEmf() {
        return emf;
    }
}
