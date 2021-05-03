package com.gda.persistence;

import com.gda.domain.Cars;
import com.gda.dtos.Car;
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

    // actualizar car
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


    //obtener el car.
    public Cars getCar(Integer carId) {
        Cars reg = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            reg = session.get(Cars.class, carId);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw new RuntimeException(String.format("Error obtaining the Car (%d) from the DB.", carId), e);
            }

        } finally {
            session.close();
        }
        return reg;
    }

    // eliminar un car
    public Cars deleteCar(Integer carId) {
        Cars reg = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            reg = session.get(Cars.class, carId);

            if (reg != null) {
                session.delete(reg);
                session.flush();
                tx.commit();
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw new RuntimeException(String.format("Error deleting the Car (%d) from the DB.", carId), e);
            }

        } finally {
            session.close();
        }
        return reg;
    }

    // crear el car
    public void createCar(Car reg) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(reg);
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









}