package persistence.entities;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SetAttribute<Role, User> users;
	public static volatile SingularAttribute<Role, String> name;

}

