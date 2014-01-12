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
package de.rs.scrumit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import de.rs.scrumit.service.InitializationSelectionEntry;
import de.rs.scrumit.service.InitializationService;

@Service("initializationService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class DummyInitializationService implements InitializationService {

	@Override
	public List<InitializationSelectionEntry> getAllPossibleEntries() {
		List<InitializationSelectionEntry> list = new ArrayList<InitializationSelectionEntry>();
		list.add(new InitializationSelectionEntry("userInitializator", "Initial Users", "add initial users"));
		list.add(new InitializationSelectionEntry("sidebarInitializator", "Menu-Sidebar", "initialize navigation mennu entries of sidebar"));
		list.add(new InitializationSelectionEntry("dummyProjectInitializator", "Dummy Project", "initialize dummy project"));
		return list;
	}

}
