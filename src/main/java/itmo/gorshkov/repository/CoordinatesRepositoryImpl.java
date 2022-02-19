package itmo.gorshkov.repository;

import itmo.gorshkov.config.SessionFactoryBuilder;
import itmo.gorshkov.entity.Coordinates;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CoordinatesRepositoryImpl implements CoordinatesRepository {

    private final SessionFactory sessionFactory;

    public CoordinatesRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<Coordinates> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM COORDINATES").list();
    }

    @Override
    public void save(Coordinates coordinates) {
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
    public Coordinates findById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.get(Coordinates.class, id);
    }

    @Override
    public Coordinates update(Coordinates newValue) {
        Session session = sessionFactory.openSession();
        session.update(newValue);
        return newValue;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.createQuery("delete from itmo.gorshkov.entity.Coordinates where id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
