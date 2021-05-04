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

    public Cars getCar(Integer carId) {
        Cars record;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            record = session.get(Cars.class, carId);

            tx.commit();

        } catch (Exception e) {
            if (tx != null)
                tx.rollback();

            throw new RuntimeException(String.format("Error obtaining the Car (%d) from the DB.", carId), e);

        } finally {
            session.close();

        }

        return record;

    }

    public void deleteCar(Integer carId) {
        Cars car;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            car = session.get(Cars.class, carId);

            if (car != null) {
                session.delete(car);

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