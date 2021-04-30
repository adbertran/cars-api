package com.gda.persistence;

import com.gda.domain.Cars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DaoService {
    private static SessionFactory sessionFactory = null;

    //Singleton implementation in replace of the Enum type class -----
    private static DaoService daoService;

    public static DaoService getDaoService() {
        if (daoService == null) {
            daoService = new DaoService();
        }

        return daoService;
    }
    //----------------------------------------------------------------

    //Private constructor. Initializes the sessionFactory.
    private DaoService() {
        //Create session only if it was not previously created.
        this.sessionFactory = HibernateSessionFactory.INSTANCE.buildSessionFactory();
    }

    //Public class methods -------------------------------------------
    //Return current session.
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //(GET) Select a car by its ID.
    public Cars getCar(Integer carId) {
        Cars car;
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = currentSession.beginTransaction();
            car = currentSession.get(Cars.class, carId);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
            throw new RuntimeException(String.format("Error obtaining the Car (%d) from the DB.", carId), e);

        } finally {
            currentSession.close();
        }

        return car;
    }

    //(POST) Update DB
    public void merge (Object object) {
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = currentSession.beginTransaction();
            currentSession.merge(object);
            currentSession.flush();
            transaction.commit();

        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
            throw e;
        }

        finally {
            currentSession.close();
        }
    }

    //(DELETE) Delete a car by its ID.
    public void deleteCar(Integer carId) {
        Cars car;
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = currentSession.beginTransaction();
            car = currentSession.get(Cars.class, carId);

            //If car exists, delete it.
            if (car !=null) {
                currentSession.delete(car);
                currentSession.flush();
                transaction.commit();
            }

        } catch (Exception e) {
            if(transaction !=null)
                transaction.rollback();

            e.printStackTrace();
            throw e;

        } finally {
            currentSession.close();
        }
    }
    //End Public class methods ---------------------------------------


    //   DaoService() {
    //       this.sessionFactory=HibernateSessionFactory.INSTANCE.buildSessionFactory();
}
