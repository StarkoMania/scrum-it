package de.rs.scrumit.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.rs.scrumit.entity.UserModel;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=true)
    public UserModel get(String account) {
		TypedQuery<UserModel> query = em.createQuery("select u from "+UserModel.class.getName()+" u where u.code = :code", UserModel.class);
        query.setParameter("code", account);
		return query.getSingleResult();
    }

}
