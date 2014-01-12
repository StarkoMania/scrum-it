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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OrderBy;
import org.hibernate.envers.Audited;

@Entity
@Table(name="release")
public class ReleaseModel extends BaseEntityAuditModel {
	
	private static final long serialVersionUID = 1L;
	
	@Audited(withModifiedFlag=true)
	private String description;
	
	@ManyToOne(optional=false)
	private ReleasePlanModel releasePlan;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@Audited(withModifiedFlag=true)
	private Date releaseDate;

	@OneToMany(mappedBy="release", targetEntity=SprintModel.class, fetch=FetchType.LAZY)
	@OrderBy(clause="startdate")
	private List<SprintModel> sprints;
	
	public ReleaseModel(){}

	public ReleaseModel(String code, Date releaseDate) {
		super(code);
		this.releaseDate = releaseDate;
	}
	
	public ReleaseModel(String code, ReleasePlanModel releasePlan, Date releaseDate) {
		super(code);
		this.releasePlan = releasePlan;
		this.releaseDate = releaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReleasePlanModel getReleasePlan() {
		return releasePlan;
	}

	public void setReleasePlan(ReleasePlanModel releasePlan) {
		this.releasePlan = releasePlan;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<SprintModel> getSprints() {
		return sprints;
	}

	public void setSprints(List<SprintModel> sprints) {
		this.sprints = sprints;
	}
	
}
