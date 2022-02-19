package itmo.gorshkov.repository;

import itmo.gorshkov.config.SessionFactoryBuilder;
import itmo.gorshkov.entity.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    private final SessionFactory sessionFactory;

    public LabelRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<Label> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM LABEL").list();
    }

    @Override
    public void save(Label coordinates) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(coordinates);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public Label findById(String name) {
        Session session = sessionFactory.openSession();
        return session.get(Label.class, name);
    }

    @Override
    public Label update(Label newValue) {
        Session session = sessionFactory.openSession();
        session.update(newValue);
        return newValue;
    }

    @Override
    public void delete(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.createQuery("delete from itmo.gorshkov.entity.Label where name=:name").setParameter("name", name).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
