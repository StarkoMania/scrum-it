/*
 * Copyright (C) 2014  Robert Stark
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package de.rs.scrumit.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.rs.scrumit.entity.BaseEntityCodedModel;
import de.rs.scrumit.entity.BaseEntityModel;

@Repository
public class BaseEntityCRUDDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T extends BaseEntityModel> List<T> queryAllByClass(Class<T> entityClass) {
		Query query = entityManager.createQuery("SELECT t FROM "+ entityClass.getName() + " t");
		List<T> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T extends BaseEntityModel> T reload(T entity) {
		return (T) entityManager.find(entity.getClass(), entity.getId());
	}

	@Transactional(readOnly = true)
	public <T extends BaseEntityModel> T get(Class<T> entityClass, Long id) {
		return entityManager.find(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T extends BaseEntityCodedModel> T getByCode(Class<T> entityClass, String code) {
		Query query = entityManager.createQuery("SELECT t FROM "+ entityClass.getName() + " t where code = :code");
		query.setParameter("code", code);
		Object result = query.getSingleResult();
		return (T) result;
	}

	@Transactional
	public <T extends BaseEntityModel> T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Transactional
	public <T extends BaseEntityModel> T update(T entity) {
		entity = entityManager.merge(entity);
		return entity;
	}

	@Transactional
	public void delete(BaseEntityModel entity) {
		BaseEntityModel r = get(entity.getClass(), entity.getId());
		if (r != null) {
			entityManager.remove(r);
		}
	}
}
