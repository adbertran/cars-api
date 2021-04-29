package com.gda.persistence;

import com.gda.domain.Cars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public enum DaoService {
    INSTANCE;

    private final SessionFactory sessionFactory;

    DaoService() {
        this.sessionFactory = HibernateSessionFactory.INSTANCE.buildSessionFactory();

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;

    }

    public void merge(Object object) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.merge(object);

            session.flush();
            tx.commit();

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();

            throw e;

        } finally {
            session.close();

        }

    }


    public void deleteCar(Integer carId) {
        Cars cars;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            cars = session.get(Cars.class, carId);

            if (cars != null) {
                session.delete(cars);

                session.flush();
                tx.commit();
            }


        } catch (Exception e) {
            if (tx != null)
                tx.rollback();

            throw new RuntimeException(String.format("Error deleting the Car (%d) from the DB.", carId), e);

        } finally {
            session.close();

        }

    }

}