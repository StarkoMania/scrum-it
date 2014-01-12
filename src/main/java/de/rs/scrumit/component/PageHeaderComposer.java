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
package de.rs.scrumit.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.A;
import org.zkoss.zul.Label;

import de.rs.scrumit.entity.ApplicationNavigationEntryModel;
import de.rs.scrumit.entity.ProjectModel;
import de.rs.scrumit.service.MenuService;
import de.rs.scrumit.service.ProjectService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PageHeaderComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	private Label pageHeaderLabel;
	
	@Wire
	private A projectNameLabel;

	@WireVariable("menuService")
	private MenuService menuService;
	
	@WireVariable("projectService")
	private ProjectService projectService;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		ApplicationNavigationEntryModel navigationEntry = menuService.getCurrentPage();
		pageHeaderLabel.setValue(navigationEntry.getLabel().toString());
		
		ProjectModel project = projectService.getCurrentProject();
		projectNameLabel.setLabel(project.getName());
	}
}
