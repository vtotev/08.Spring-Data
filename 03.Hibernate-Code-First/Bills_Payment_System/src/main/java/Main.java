import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager bps = Persistence
                .createEntityManagerFactory("Bills_Payment_System")
                .createEntityManager();
    }
}
