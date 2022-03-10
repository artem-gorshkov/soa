package itmo.gorshkov.repository;

import itmo.gorshkov.config.FilterConfiguration;
import itmo.gorshkov.config.SessionFactoryBuilder;
import itmo.gorshkov.entity.MusicBand;
import itmo.gorshkov.entity.MusicGenre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MusicBandRepositoryImpl implements MusicBandRepository {
    public static Logger logger = LoggerFactory.getLogger(MusicBandRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public MusicBandRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<MusicBand> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM MUSIC_BAND").list();
    }

    @Override
    public void save(MusicBand band) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(band);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public MusicBand findById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.get(MusicBand.class, id);
    }

    @Override
    public MusicBand update(MusicBand newValue) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.update(newValue);
            transaction.commit();
            return newValue;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.createQuery("delete from itmo.gorshkov.entity.MusicBand where id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public List<Object[]> countByNumberOfParticipants() {
        Session session = sessionFactory.openSession();
        List<Object[]> resultList = session.createNativeQuery("SELECT NUMBER_OF_PARTICIPANTS, count(*) FROM music_band group by NUMBER_OF_PARTICIPANTS").list();
        return resultList;
    }

    @Override
    public List<MusicBand> findAll(FilterConfiguration filterConfiguration) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<MusicBand> criteria = criteriaBuilder.createQuery(MusicBand.class);
        Root<MusicBand> root = criteria.from(MusicBand.class);

        orderQuery(filterConfiguration, criteriaBuilder, criteria, root);

        filterQuery(filterConfiguration, criteriaBuilder, criteria, root);

        Query<MusicBand> query = session.createQuery(criteria);

        countAndPageQuery(filterConfiguration, query);

        return query.list();
    }

    private void orderQuery(FilterConfiguration filterConfiguration, CriteriaBuilder criteriaBuilder, CriteriaQuery<MusicBand> criteria, Root<MusicBand> root) {
        if (filterConfiguration.getOrder() != null) {
            List<Order> orders = new ArrayList<>();

            for (String order : filterConfiguration.getOrder()) {
                String[] parts = order.split(",");
                if (parts.length == 2) {
                    if (parts[1].equals("d")) {
                        orders.add(criteriaBuilder.desc(root.get(parts[0])));
                    } else if (parts[1].equals("a")) {
                        orders.add(criteriaBuilder.asc(root.get(parts[0])));
                    }
                } else {
                    logError(parts);
                }
            }

            criteria.orderBy(orders);
        }
    }

    private void filterQuery(FilterConfiguration filterConfiguration, CriteriaBuilder criteriaBuilder, CriteriaQuery<MusicBand> criteria, Root<MusicBand> root) {
        if (filterConfiguration.getFilter() != null) {
            List<Predicate> predicates = new ArrayList<>();

            for (String filter : filterConfiguration.getFilter()) {
                String[] parts = filter.split(",");

                if (parts.length == 3) {
                    switch (parts[1]) {
                        case "==":
                            equalFilter(criteriaBuilder, root, predicates, parts);
                            break;
                        case "<=":
                            lessOrEqualFilter(criteriaBuilder, root, predicates, parts);
                            break;
                        case ">=":
                            greaterOrEqualFilter(criteriaBuilder, root, predicates, parts);
                            break;
                        case "<":
                            lessFilter(criteriaBuilder, root, predicates, parts);
                            break;
                        case ">":
                            greaterFilter(criteriaBuilder, root, predicates, parts);
                            break;
                        case "contains":
                            predicates.add(criteriaBuilder.like(root.get(parts[0]), "%" + parts[2] + "%"));
                            break;
                    }
                } else {
                    logError(parts);
                }
            }

            criteria.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        }
    }

    private void greaterFilter(CriteriaBuilder criteriaBuilder, Root<MusicBand> root, List<Predicate> predicates, String[] parts) {
        if (parts[0].equals("creationDate")) {
            predicates.add(criteriaBuilder.greaterThan(root.get(parts[0]), LocalDate.parse(parts[2])));
        } else {
            predicates.add(criteriaBuilder.greaterThan(root.get(parts[0]), parts[2]));
        }
    }

    private void lessFilter(CriteriaBuilder criteriaBuilder, Root<MusicBand> root, List<Predicate> predicates, String[] parts) {
        if (parts[0].equals("creationDate")) {
            predicates.add(criteriaBuilder.lessThan(root.get(parts[0]), LocalDate.parse(parts[2])));
        } else {
            predicates.add(criteriaBuilder.lessThan(root.get(parts[0]), parts[2]));
        }
    }

    private void greaterOrEqualFilter(CriteriaBuilder criteriaBuilder, Root<MusicBand> root, List<Predicate> predicates, String[] parts) {
        if (parts[0].equals("creationDate")) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parts[0]), LocalDate.parse(parts[2])));
        } else {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(parts[0]), parts[2]));
        }
    }

    private void lessOrEqualFilter(CriteriaBuilder criteriaBuilder, Root<MusicBand> root, List<Predicate> predicates, String[] parts) {
        if (parts[0].equals("creationDate")) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parts[0]), LocalDate.parse(parts[2])));
        } else {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(parts[0]), parts[2]));
        }
    }

    private void equalFilter(CriteriaBuilder criteriaBuilder, Root<MusicBand> root, List<Predicate> predicates, String[] parts) {
        if (parts[0].equals("creationDate")) {
            predicates.add(criteriaBuilder.equal(root.<Date>get(parts[0]), LocalDate.parse(parts[2])));
        } else if (parts[0].equals("genre")) {
            predicates.add(criteriaBuilder.equal(root.get(parts[0]), MusicGenre.valueOf(parts[2])));
        } else {
            predicates.add(criteriaBuilder.equal(root.get(parts[0]), parts[2]));
        }
    }

    private void countAndPageQuery(FilterConfiguration filterConfiguration, Query<MusicBand> query) {
        if (filterConfiguration.getCount() != null && filterConfiguration.getPage() != null) {
            query.setMaxResults(filterConfiguration.getCount());
            query.setFirstResult(filterConfiguration.getCount() * (filterConfiguration.getPage() - 1));
        }
    }


    private void logError(String[] parts) {
        logger.warn("Invalid number of arguments: {}, {}", parts.length, Arrays.toString(parts));
    }
}
