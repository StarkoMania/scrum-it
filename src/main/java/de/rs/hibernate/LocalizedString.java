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
package de.rs.hibernate;

import java.io.Serializable;
import java.util.Locale;

public class LocalizedString implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String value;

	private Locale locale;
	
	public LocalizedString() {
		super();
	}
	
	public LocalizedString(String value, Locale locale) {
		super();
		this.value = value;
		this.locale = locale;
		
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	public int hashCode() {
		return new String(value+locale.toString()).hashCode();
	}
	
	@Override
	public String toString() {
		return value;
	}
}
