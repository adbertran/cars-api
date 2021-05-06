package com.gda.persistence;

import com.gda.domain.CarsDb;
import com.gda.dtos.CarJson;
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
    public CarsDb getCar(Integer carId) {
        CarsDb reg = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            reg = session.get(CarsDb.class, carId);
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
    public void deleteCar(Integer carId) {
        CarsDb reg = null;
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            reg = session.get(CarsDb.class, carId);

            if (reg != null) {
                session.delete(reg);
                session.flush();
                tx.commit();
            } else {
                throw new RuntimeException("No se puede borrar el auto inexistente.");


            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw new RuntimeException(String.format("Error deleting the Car (%d) from the DB.", carId), e);
            }

        } finally {
            session.close();
        }

    }

    // crear el car
    public void createCar(CarsDb reg) {
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