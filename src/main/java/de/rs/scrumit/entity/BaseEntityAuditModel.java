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
package de.rs.scrumit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@MappedSuperclass
public abstract class BaseEntityAuditModel extends BaseEntityCodedModel {

	private static final long serialVersionUID = 1L;

    @Column(name = "updated_at")  
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    private Date changedAt;  
  
    @ManyToOne(optional=true)
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    @AuditOverride(forClass=UserModel.class, isAudited=false)
    private UserModel changedBy;

    public BaseEntityAuditModel() {
		super();
	}
    
	public BaseEntityAuditModel(String code) {
		super(code);
	}
	
	@PreUpdate @PrePersist
	public void setCurrentDateOnUpdate() {
		this.changedAt = new Date();
	}

	public Date getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

	public UserModel getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(UserModel changedBy) {
		this.changedBy = changedBy;
	}

}
