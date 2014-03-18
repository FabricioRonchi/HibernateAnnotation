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
public class RoleDAO extends GenericDAO {

    @Override
    protected String getNamedQueryToFindAll() {
        return "ROLES.find.all";
    }
    
    @Override
    protected String getNamedQueryToFindById() {
        return "ROLES.id.equals";
    }

    @Override
    protected String getNamedQueryToFindByName() {
        return "ROLES.name.equals";
    }    
 
    @Override
    protected String getNamedQueryToCountAll() {
        return "ROLES.count.all";
    }     

    @Override
    protected String getNamedQueryToRemoveAll() {
        return "ROLES.remove.all";
    }     

    @Override
    protected String getNamedQueryToFindByRange() {
        return "ROLES.find.range";
    }     

    @Override
    public Object getNewInstance() {
        return new Role();
    }   
        
    protected String getNamedQueryToFindUser() {
        return "ROLES.find.user";
    }
    
    public List findUser(Role o) throws Exception {
        Session session = null;
        try {
            UserDAO user = new UserDAO();            
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.getNamedQuery(user.getNameQueryToFindUser());
            q.setParameter("id", o.getId());
            List lst = q.list();
            session.getTransaction().commit();
            return lst;
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }

    }                  
}