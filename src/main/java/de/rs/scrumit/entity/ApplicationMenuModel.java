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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "menu")
public class ApplicationMenuModel extends BaseEntityCodedModel {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "menu", targetEntity = ApplicationNavigationEntryModel.class, fetch = FetchType.EAGER)
	@OrderBy(clause="position")
	private List<ApplicationNavigationEntryModel> navigationEntries;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "default_navigation_entry_id")
	private ApplicationNavigationEntryModel defaultNavigationEntry;
	
	public ApplicationMenuModel() {
		super();
	}

	public ApplicationMenuModel(String code) {
		super(code);
	}

	public ApplicationMenuModel(String code,
			List<ApplicationNavigationEntryModel> navigationEntries,
			ApplicationNavigationEntryModel defaultNavigationEntry) {
		super(code);
		this.navigationEntries = navigationEntries;
		this.defaultNavigationEntry = defaultNavigationEntry;
	}

	public List<ApplicationNavigationEntryModel> getNavigationEntries() {
		return navigationEntries;
	}

	public void setNavigationEntries(
			List<ApplicationNavigationEntryModel> navigationEntries) {
		this.navigationEntries = navigationEntries;
	}

	public ApplicationNavigationEntryModel getDefaultNavigationEntry() {
		return defaultNavigationEntry;
	}

	public void setDefaultNavigationEntry(
			ApplicationNavigationEntryModel defaultNavigationEntry) {
		this.defaultNavigationEntry = defaultNavigationEntry;
	}
	
}
