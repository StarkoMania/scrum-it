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
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

@Entity
@Table(name="sprint")
public class SprintModel extends BaseEntityAuditModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional=false)
	@Audited(withModifiedFlag=true)
	private ReleaseModel release;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Audited(withModifiedFlag=true)
	private Date startdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Audited(withModifiedFlag=true)
	private Date enddate;
	
	@Audited(withModifiedFlag=true)
	private String description;

	public SprintModel() {
		super();
	}
	
	public SprintModel(String code, ReleaseModel release, Date startdate, Date enddate) {
		super(code);
		this.release = release;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	public ReleaseModel getRelease() {
		return release;
	}

	public void setRelease(ReleaseModel release) {
		this.release = release;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
