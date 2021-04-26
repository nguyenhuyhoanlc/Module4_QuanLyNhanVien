package service;
import model.Staff;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements IStaffService{
    List<Staff> staffList = new ArrayList<>();
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
//    static {
//        try {
//            SessionFactory sessionFactory = new Configuration()
//                    .configure("hibernate.conf.xml")
//                    .buildSessionFactory();
//            sessionFactory.close();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//    }

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Staff> findAll() {
        String queryStr = "SELECT c FROM Staff AS c";
        TypedQuery<Staff> query = entityManager.createQuery(queryStr,Staff.class);
        return query.getResultList();
    }

    @Override
    public void save(Staff staff) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(staff);
        transaction.commit();

    }

    @Override
    public Staff findById(long id) {
        String queryStr = "SELECT c FROM Staff AS c WHERE c.id = :id";
        TypedQuery<Staff> query = entityManager.createQuery(queryStr, Staff.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Staff update(Staff staff) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Staff staff1 = findById(staff.getId());
            staff1.setName(staff.getName());
            staff1.setDate(staff.getDate());
            staff1.setGender(staff.isGender());
            session.saveOrUpdate(staff1);
            transaction.commit();
            return staff1;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            if(session !=null){
                session.close();
            }
        }
        return null;
    }

    @Override
    public void remove(long id) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getId() == id) {
                staffList.remove(i);
                break;
            }
        }
    }
}
