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

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.rs.scrumit.entity.BaseEntityAuditModel;

@Repository
public class BaseEntityAuditDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityAuditDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public <T extends BaseEntityAuditModel> T getByRevision(Class<T> modelClass, Number revision) {
		AuditReader reader = AuditReaderFactory.get(entityManager);
		T model = reader.findRevision(modelClass, revision);
		return model;
	}
	
	@Transactional(readOnly = true)
	public <T extends BaseEntityAuditModel> void printChangeHistory(Class<T> modelClass, String code) {
		AuditReader reader = AuditReaderFactory.get(entityManager);

		List<?> results = reader.createQuery()
			    .forRevisionsOfEntity(modelClass, false, true)
			    .add(AuditEntity.property("code").eq(code))
			    .addOrder(AuditEntity.property("changedAt").asc())
			    .getResultList();
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		for (Object entry : results) {
			BaseEntityAuditModel model = (BaseEntityAuditModel) ((Object[]) entry)[0];
			RevisionType type = (RevisionType) ((Object[]) entry)[2];
			String msg = type+" entry with code "+model.getCode()+" at time: "+format.format(model.getChangedAt());
			LOGGER.debug(msg);
			System.out.println(msg);
		}
	}
}
