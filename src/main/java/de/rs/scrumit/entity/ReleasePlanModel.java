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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name="releaseplan")
public class ReleasePlanModel extends BaseEntityCodedModel {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(optional=false)
	private ProjectModel project;
	
	@OneToMany(mappedBy="releasePlan", targetEntity=ReleaseModel.class, fetch=FetchType.EAGER)
	@OrderBy(clause="releaseDate")
	private List<ReleaseModel> releases;

	public ReleasePlanModel() {}

	public ReleasePlanModel(String code, ProjectModel project) {
		super(code);
		this.project = project;
	}
	
	public ProjectModel getProject() {
		return project;
	}
	
	public void setProject(ProjectModel project) {
		this.project = project;
	}

	public List<ReleaseModel> getReleases() {
		return releases;
	}

	public void setReleases(List<ReleaseModel> releases) {
		this.releases = releases;
	}

}
