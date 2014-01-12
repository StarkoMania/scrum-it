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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;

import de.rs.scrumit.AppConst;
import de.rs.scrumit.entity.ApplicationMenuModel;
import de.rs.scrumit.entity.ApplicationNavigationEntryModel;
import de.rs.scrumit.service.MenuService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SidebarComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SidebarComposer.class);

	@Wire
	Div sidebar;
	@Wire
	Navbar navbar;
	@Wire
	Navitem calitem;
	@Wire
	A toggler;
	
	@WireVariable("menuService")
	MenuService menuService;

	private ApplicationNavigationEntryModel currentPage;
	
	private Navitem selectedItem;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		ApplicationMenuModel sidebar = menuService.getMenuByCode("sidebar");
		currentPage = menuService.getCurrentPage();
		
		for (ApplicationNavigationEntryModel navEntry : sidebar.getNavigationEntries()) {
			LOGGER.debug("nav: "+navEntry.getCode());
			if (navEntry.getParent() == null) {
				Component component = null;
				if (navEntry.getSubEntries() != null && navEntry.getSubEntries().size() > 0) {
					component = createNav(navEntry);
				} else {
					component = createNavItem(navEntry);
				}
				navbar.getChildren().add(component);
				navbar.selectItem(selectedItem);
			}
		}
	}

	private Component createNav(ApplicationNavigationEntryModel navEntry) {
		Nav nav = new Nav(navEntry.getLabel().toString());
		nav.setIconSclass(AppConst.UI.ICON_SCLASS_PREFIX+navEntry.getIcon());
		for (ApplicationNavigationEntryModel subEntry : navEntry.getSubEntries()) {
			if (subEntry != null) {
				Component component = null;
				if (subEntry.getSubEntries() != null && subEntry.getSubEntries().size() > 0) {
					component = createNav(subEntry);
				} else {
					component = createNavItem(subEntry);
				}
				nav.getChildren().add(component);
			}
		}
		return nav;
	}

	private Component createNavItem(ApplicationNavigationEntryModel navEntry) {
		Navitem navItem = new Navitem();
		navItem.setAttribute("navigationEntryModel", navEntry);
		navItem.setLabel(navEntry.getLabel().toString());
		navItem.setIconSclass(AppConst.UI.ICON_SCLASS_PREFIX+navEntry.getIcon());
		if (navEntry.getTarget() != null && navEntry.getTarget().length() > 0) {
			navItem.setTarget(navEntry.getTarget());
		}
		if (navEntry.getCode().equals(currentPage.getCode())) {
			selectedItem = navItem;
		}
		return navItem;
	}
	
	@Listen("onClick = #toggler")
	public void toggleSidebarCollapsed() {
		if (navbar.isCollapsed()) {
			sidebar.setSclass("sidebar");
			navbar.setCollapsed(false);
			if (calitem != null) {
				calitem.setTooltip("calpp, position=end_center, delay=0");
			}
			toggler.setIconSclass("z-icon-angle-double-left");
		} else {
			sidebar.setSclass("sidebar sidebar-min");
			navbar.setCollapsed(true);
			if (calitem != null) {
				calitem.setTooltip("");
			}
			toggler.setIconSclass("z-icon-angle-double-right");
		}
		Clients.resize(getMain());
	}
	
	@Listen("onClick = navitem")
	public void navitemSelected(Event event) {
    	Navitem navitem = (Navitem) event.getTarget();
    	navbar.selectItem(navitem);
		ApplicationNavigationEntryModel navigationEntry = (ApplicationNavigationEntryModel) navitem.getAttribute("navigationEntryModel");
		menuService.setCurrentPageByCode(navigationEntry.getCode());
		String target = navitem.getTarget();
    	loadPage(target);
    }
	
	@Listen("onClick = button")
	public void buttonClicked(Event event) {
		navbar.setSelectedItem(null);
		String target = ((Button) event.getTarget()).getTarget();
		loadPage(target);
	}
	
	private void loadPage(String target) {
		if (target != null) {
			Include include = getMainInclude();
			include.setSrc(target);
		}
	}

	private Component getMain() {
		return Selectors.iterable(sidebar.getPage(), "#main").iterator().next();
	}
	
	private Include getMainInclude() {
		return (Include)Selectors.iterable(sidebar.getPage(), "#mainInclude").iterator().next();
	}
}