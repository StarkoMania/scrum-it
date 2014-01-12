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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="feature")
public class FeatureModel extends BaseEntityAuditModel {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=255)
	private String name;
	
	private String description;
	
	@ManyToOne(optional=false)
	private FeatureThemeModel theme;
	
	public FeatureModel(){}

	public FeatureModel(String code, String name, FeatureThemeModel theme) {
		super(code);
		this.name = name;
		this.theme = theme;
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

	public FeatureThemeModel getTheme() {
		return theme;
	}
	
	public void setTheme(FeatureThemeModel theme) {
		this.theme = theme;
	}
	
}
