package configs;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.hibernate.cfg.Configuration;


@org.springframework.context.annotation.Configuration
public class SessionFactoryConfig {
    @Bean
    public SessionFactory sessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
