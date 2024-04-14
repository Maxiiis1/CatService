package configs;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class EntityManagerConfig {
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("my-database");
    }
}
