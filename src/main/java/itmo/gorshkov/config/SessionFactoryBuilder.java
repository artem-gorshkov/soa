package itmo.gorshkov.config;


import itmo.gorshkov.entity.Coordinates;
import itmo.gorshkov.entity.Label;
import itmo.gorshkov.entity.MusicBand;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class SessionFactoryBuilder {

    private static final SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/soa");
        properties.put(Environment.USER, "postgres");
        properties.put(Environment.PASS, "postgres");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.SHOW_SQL, true);

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(MusicBand.class);
        configuration.addAnnotatedClass(Label.class);
        configuration.addAnnotatedClass(Coordinates.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
