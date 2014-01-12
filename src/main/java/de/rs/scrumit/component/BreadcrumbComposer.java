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
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import de.rs.scrumit.entity.ApplicationNavigationEntryModel;
import de.rs.scrumit.service.MenuService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BreadcrumbComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	Div breadcrumb;

	@WireVariable("menuService")
	MenuService menuService;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		ApplicationNavigationEntryModel navigationEntry = menuService.getCurrentPage();
		Label label = new Label(navigationEntry.getLabel().toString());
		breadcrumb.getChildren().add(label);
	}
}
