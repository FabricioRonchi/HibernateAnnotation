import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import persistence.entities.Role;
import persistence.entities.RoleDAO;
import persistence.entities.User;
import persistence.entities.UserDAO;

public class TestarPersistence {
 
    public static void main(String[] args) {
        try {
            //createUsers();            
            //showAllUser();
            //createRoles();
            //showAllRoles();                        
            adicionarRole(); 
            //buscarRole();
            //removeRole();
            //temRole();     
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    private static void createUsers() throws Exception {
        UserDAO dao = new UserDAO();
        //dao.removeAll();
        User f1 = (User) dao.getNewInstance();
        User f2 = (User) dao.getNewInstance();
        User f3 = (User) dao.getNewInstance();
        f1.setName("Diego Cavalieri");        
        f2.setName("Fred");      
        f3.setName("Walter");
        dao.save(f1);
        dao.save(f2);
        dao.save(f3);
    } 
    
    private static void createRoles() throws Exception {
        RoleDAO role = new RoleDAO(); 
        Role role1 = (Role) role.getNewInstance();
        Role role2 = (Role) role.getNewInstance();
        role1.setName("Administrador");       
        role2.setName("Super Usuário");
        role.save(role1);
        role.save(role2);
    } 
    
    private static void showAllUser() throws Exception {         
        UserDAO dao = new UserDAO();        
        ArrayList users = (ArrayList) dao.findAll();
        User o;
        
        System.out.println("Listando Usuários...");
        
        for (int i=0; i<users.size(); i++) {
            o = (User) users.get(i);
            System.out.println("Id: " + o.getId() + " - " +  "Nome: " + o.getName()+  " - Regras: " + o.getRoles());
        }                
        
        User admin = (User) dao.getNewInstance();       
        
    }  
    
    private static void adicionarRole() throws Exception {                       
        //createUsers();
        //createRoles();
        Set setRole1 = new HashSet();
        RoleDAO role = new RoleDAO();
        Role role1 = (Role) role.findById(1L);
        Role role2 = (Role) role.findById(2L);        
        setRole1.add(role1);
        setRole1.add(role2);        
        Set setRoles2 = new HashSet();
        setRoles2.add(role1);
        UserDAO dao = new UserDAO();
        User f1 = (User) dao.findById(1L);
        User f2 = (User) dao.findById(2L);
        User f3 = (User) dao.findById(3L);
        f1.setRoles(setRole1);
        f2.setRoles(setRoles2);
        f3.setRoles(setRole1);       
        dao.save(f1);
        showAllUser();      
        dao.save(f2);
        showAllUser();
        dao.save(f3);
        showAllUser();        
        buscarRole(role1);          
    }  

     private static void showAllRoles() throws Exception {         
        RoleDAO dao = new RoleDAO();        
        ArrayList users = (ArrayList) dao.findAll();
        Role o;
        
        System.out.println("Listando Regras...");
        
        for (int i=0; i<users.size(); i++) {
            o = (Role) users.get(i);
            System.out.println("Id: " + o.getId() + " - " +  "Regra: " + o.getName()+ " - " +  "Usuários: " + o.getUsers());
        }                
        
        Role admin = (Role) dao.getNewInstance();       
        
    }
     
     private static void removeRole() throws Exception {                        
        RoleDAO roledao = new RoleDAO();
        roledao.removeAll();        
    } 
     
     private static void buscarRole(Role role) throws Exception {                                       
        RoleDAO dao = new RoleDAO();
        ArrayList roles = (ArrayList) dao.findUser(role);
        User o;
        for (int i = 0; i < roles.size(); i++) {
            o = (User) roles.get(i);
            System.out.println("Id: " + o.getId() + " - " +  "Nome: " + o.getName()+  " - Regras: " + o.getRoles());
        }      
    } 
     
     private static void temRole() throws Exception {                        
        UserDAO userd = new UserDAO();
        RoleDAO roled = new RoleDAO();
        Role role = (Role) roled.findById(2L); 
        User user = (User) userd.findById(2L);         
        boolean rel = userd.hasRole(role, user);   
        if(rel == true){
            System.err.println("Tem uma regra pra esse Usuário!");
        }else{
            System.err.println("Não existe uma regra pra esse Usuário!");
        }        
    }          
}
