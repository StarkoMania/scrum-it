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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import de.rs.hibernate.LocalizedString;
import de.rs.hibernate.LocalizedStringType;
import de.rs.scrumit.AppConst;

@Entity
@Table(name = "navigation")
@TypeDef(name = "localizedString", typeClass = LocalizedStringType.class)
public class ApplicationNavigationEntryModel extends BaseEntityCodedModel {

	private static final long serialVersionUID = 7973991948157498976L;

	@ManyToOne(optional = false)
	private ApplicationMenuModel menu;

	@ManyToOne(optional = true)
	private ApplicationNavigationEntryModel parent;

	@OneToMany(mappedBy = "parent", targetEntity = ApplicationNavigationEntryModel.class, fetch = FetchType.EAGER)
	@OrderBy(clause="position")
	private List<ApplicationNavigationEntryModel> subEntries;

	@Type(type = "localizedString")
	@Columns(columns = { @Column(name = "label_value", length=255), @Column(name = "label_locale")})
	private LocalizedString label;

	@Column(nullable = true, length = 255)
	private String target;

	@Column(nullable = false, length = 255)
	private String icon;

	@Column(nullable = false)
	private Integer position;

	public ApplicationNavigationEntryModel() {
		super();
	}

	public ApplicationNavigationEntryModel(String code, ApplicationMenuModel menu,
			LocalizedString label) {
		super(code);
		this.menu = menu;
		this.label = label;
		this.icon = AppConst.Entity.APPLICATION_NAVIGATION_ICON_DEFAULT;
	}

	public ApplicationNavigationEntryModel(String code, ApplicationMenuModel menu,
			ApplicationNavigationEntryModel parent,
			List<ApplicationNavigationEntryModel> subEntries, LocalizedString label,
			String target, String icon, Integer position) {
		super(code);
		this.menu = menu;
		this.parent = parent;
		this.subEntries = subEntries;
		this.label = label;
		this.target = target;
		this.icon = icon;
		this.position = position;
	}

	@PrePersist
	void beforeInsert() {
		if (icon == null) {
			icon = AppConst.Entity.APPLICATION_NAVIGATION_ICON_DEFAULT;
		}
		if (position == null) {
			position = 0;
		}
	}

	public ApplicationMenuModel getMenu() {
		return menu;
	}

	public void setMenu(ApplicationMenuModel menu) {
		this.menu = menu;
	}

	public ApplicationNavigationEntryModel getParent() {
		return parent;
	}

	public void setParent(ApplicationNavigationEntryModel parent) {
		this.parent = parent;
	}

	public List<ApplicationNavigationEntryModel> getSubEntries() {
		return subEntries;
	}

	public void setSubEntries(List<ApplicationNavigationEntryModel> subEntries) {
		this.subEntries = subEntries;
	}

	public LocalizedString getLabel() {
		return label;
	}

	public void setLabel(LocalizedString label) {
		this.label = label;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer menuPosition) {
		this.position = menuPosition;
	}

}
