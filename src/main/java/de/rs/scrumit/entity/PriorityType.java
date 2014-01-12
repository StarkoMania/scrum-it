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

public enum PriorityType {
	HIGH(0, "High"), MEDIUM(1, "Medium"), LOW(2, "Low");

	private int priority;
	private String label;

	private PriorityType(int priority, String label) {
		this.priority = priority;
		this.label = label;
	}

	public int getPriority() {
		return priority;
	}

	public String getLabel() {
		return label;
	}
}