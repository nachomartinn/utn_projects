package repository.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbContext {
    // Singleton instance, only one instance of DbContext will exist
    public static DbContext instance = null;
    private final EntityManager em;
    private final EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT_NAME = "EventosPU";

    private DbContext() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    public static DbContext getInstance() {
        if (instance == null) {
            instance = new DbContext();
        }
        return instance;
    }

    public EntityManager getManager() {
        return this.em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Cierra el EntityManager y el EntityManagerFactory. Útil para terminar ejecuciones CLI.
     */
    public void close() {
        try {
            if (this.em != null && this.em.isOpen()) {
                this.em.close();
            }
        } catch (Exception ignore) { }

        try {
            if (this.emf != null && this.emf.isOpen()) {
                this.emf.close();
            }
        } catch (Exception ignore) { }

        // liberar la instancia singleton
        instance = null;
    }
}
