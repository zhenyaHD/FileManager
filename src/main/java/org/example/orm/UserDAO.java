package org.example.orm;

import org.example.accounts.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.List;

public class UserDAO {
    public UserDAO() {
    }

    public User getByLogin(String login) {
        try {
            return (User) HibernateSessionFactory.getSessionFactory().openSession().createCriteria(User.class)
                    .add(Restrictions.eq("login", login)).uniqueResult();
        } catch (HibernateException e) {
            return null;
        }
    }

    public User get(long id) {
        return (User) HibernateSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    public List<User> getAll() {
        try {
            return HibernateSessionFactory.getSessionFactory().openSession().createCriteria(User.class).list();
        } catch (HibernateException e) {
            return Collections.EMPTY_LIST;
        }
    }

    public void add(User dataSet) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dataSet);
        transaction.commit();
        session.close();
    }
}