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
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

@MappedSuperclass
public abstract class BaseEntityCodedModel extends BaseEntityModel {

	private static final long serialVersionUID = 1L;

	@Column(name="code", nullable=false, unique=true, length=255)
	@Audited(withModifiedFlag=true)
    private String code;  

	public BaseEntityCodedModel() {
		super();
	}
	
	public BaseEntityCodedModel(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BaseEntityCodedModel) {
			BaseEntityCodedModel model = (BaseEntityCodedModel) obj;
			if (code != null && model.getCode() != null) {
				return code.equals(model.getCode());
			}
		}
		return false;
	}
}
