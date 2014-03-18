package persistence.entities;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Fabrício Ronchi
 *
 */
public abstract class GenericDAO implements IDAO {


    @Override
    public Object create(Object o) throws Exception {
        Session session = null;
        try {
            session = obtainSession();
            session.beginTransaction();
            session.persist(o);
            session.getTransaction().commit();
            return o;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public void save(Object o) {
        Session session = obtainSession();
        try {
            session.beginTransaction();
            session.merge(o);            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } 
    }

    @Override
    public void delete(Object o) throws Exception {
        Session session = null;
        try {
            session = obtainSession();
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }
    
    @Override
    public void update(Object o) throws Exception {
        Session session = null;
        try {
            session = obtainSession();
            session.beginTransaction();
            session.persist(o);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public Object findById(Long theId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.getNamedQuery(getNamedQueryToFindById());
            q.setParameter("id", theId);
            Object o = q.uniqueResult();
            return o;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        return null;
    }

    @Override
    public Object findByName(String theName) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            //Query q = session.getNamedQuery("name.igual");
            Query q = session.getNamedQuery(getNamedQueryToFindByName());
            q.setString("name", theName);
            Object o = q.uniqueResult();
            return o;
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List findAll() throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.getNamedQuery("find.all");
            Query q = session.getNamedQuery(getNamedQueryToFindAll());
            List lst = q.list();
            session.getTransaction().commit();
            return lst;
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List list(Integer firstResult, Integer maxResults) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.getNamedQuery("find.all");
            Query q = session.getNamedQuery(getNamedQueryToFindByRange());
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            List lst = q.list();
            return lst;
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public Long countAll() throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.getNamedQuery("count.all");
            Query q = session.getNamedQuery(getNamedQueryToCountAll());
            Long count = (Long) q.uniqueResult();
            session.getTransaction().commit();
            return count;
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public void removeAll() throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.getNamedQuery(getNamedQueryToRemoveAll());
            q.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }

    protected Session obtainSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    protected void releaseSession(Session session) {
    }

    /*
     * Abstract Members...
     */
    protected abstract String getNamedQueryToFindAll();

    protected abstract String getNamedQueryToFindById();

    protected abstract String getNamedQueryToFindByName();

    protected abstract String getNamedQueryToCountAll();

    protected abstract String getNamedQueryToRemoveAll();

    protected abstract String getNamedQueryToFindByRange();
}