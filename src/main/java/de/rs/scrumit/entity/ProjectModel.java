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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="project")
public class ProjectModel extends BaseEntityCodedModel {

	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=128)
	String name;

	String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	Date startdate;

	@Temporal(TemporalType.TIMESTAMP)
	Date enddate;

	@OneToOne(optional=false, cascade=CascadeType.ALL, mappedBy="project", targetEntity=ReleasePlanModel.class)
	ReleasePlanModel releasePlan;

	@OneToMany(mappedBy="project", targetEntity=FeatureThemeModel.class, fetch=FetchType.LAZY)
	Set<FeatureThemeModel> themes;

	public ProjectModel() {}

	public ProjectModel(String code, String name, Date startdate) {
		super(code);
		this.name = name;
		this.startdate = startdate;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public ReleasePlanModel getReleasePlan() {
		return releasePlan;
	}
	
	public void setReleasePlan(ReleasePlanModel releasePlan) {
		this.releasePlan = releasePlan;
	}
	
	public Set<FeatureThemeModel> getThemes() {
		return themes;
	}
	
	public void setThemes(Set<FeatureThemeModel> themes) {
		this.themes = themes;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
