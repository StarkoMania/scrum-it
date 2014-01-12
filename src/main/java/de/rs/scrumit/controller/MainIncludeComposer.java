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
package de.rs.scrumit.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Include;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ApplicationNavigationEntryModel;
import de.rs.scrumit.service.MenuService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MainIncludeComposer extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;

	@WireVariable("menuService")
	MenuService menuService;
	
	@Wire
	Include mainInclude;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		subscribe("onProjectSelectionChange", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String src = mainInclude.getSrc();
				mainInclude.setSrc(null);
				mainInclude.setSrc(src);
			}
		});
		
		ApplicationNavigationEntryModel navigationEntry = menuService.getCurrentPage();
		String target = navigationEntry.getTarget();
		mainInclude.setSrc(target);
	}
}
