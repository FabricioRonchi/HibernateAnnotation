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
public class UserDAO extends GenericDAO {

    @Override
    protected String getNamedQueryToFindAll() {
        return "user.find.all";
    }
    
    @Override
    protected String getNamedQueryToFindById() {
        return "user.id.equals";
    }

    @Override
    protected String getNamedQueryToFindByName() {
        return "user.name.equals";
    }    
 
    @Override
    protected String getNamedQueryToCountAll() {
        return "user.count.all";
    }     

    @Override
    protected String getNamedQueryToRemoveAll() {
        return "user.remove.all";
    }     

    @Override
    protected String getNamedQueryToFindByRange() {
        return "user.find.range";
    }  
          
    protected String getNamedQueryToRemoveRoleUser() {
        return "user.remove.role";
    }  
    
     protected String getNamedQueryToTemRole() {
        return "user.has.temrole";
    }  

    @Override
    public Object getNewInstance() {
        return new User();
    }
    
    protected String getNamedQueryToFindRoles() {
        return "ROLES.find.rolesUser";
    }
    
    protected String getNameQueryToFindUser() {
        return "user.find.role";
    }
        
   public boolean hasRole(Role role, User user) throws Exception {
       Session session = null;
        try {
            boolean retorno = false;
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.getNamedQuery(getNamedQueryToTemRole());
            q.setParameter("role", role.getId());
            q.setParameter("user", user.getId());
            List lst = q.list();
            session.getTransaction().commit();
            if(lst.size() > 0){
                retorno = true;
            }            
            return retorno;
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
        
    }
            
    public void removeRole(User user, Role role) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query q = session.getNamedQuery(getNamedQueryToRemoveRoleUser());
            q.setParameter("role", role.getId());
            q.executeUpdate();
        } catch (HibernateException e) {
            throw new Exception(e.getCause().getMessage());
        } finally {
            releaseSession(session);
        }
    }
       
}