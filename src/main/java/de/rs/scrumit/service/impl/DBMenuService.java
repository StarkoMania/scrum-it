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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.ApplicationMenuModel;
import de.rs.scrumit.entity.ApplicationNavigationEntryModel;
import de.rs.scrumit.service.MenuService;

@Component("menuService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DBMenuService implements MenuService {

	@Autowired
	private BaseEntityCRUDDao baseEntityCRUDDao;

	@Override
	public ApplicationMenuModel getMenuByCode(String code) {
		return baseEntityCRUDDao.getByCode(ApplicationMenuModel.class, code);
	}

	@Override
	public ApplicationNavigationEntryModel getCurrentPage() {
		Session session = Sessions.getCurrent();
		ApplicationNavigationEntryModel navigationEntry = (ApplicationNavigationEntryModel) session.getAttribute("currentPage");
		if (navigationEntry == null) {
			navigationEntry = getMenuByCode("sidebar").getDefaultNavigationEntry();
			session.setAttribute("currentPage", navigationEntry);
		}
		return navigationEntry;
	}
	
	@Override
	public void setCurrentPageByCode(String code) {
		ApplicationNavigationEntryModel navigationEntry = baseEntityCRUDDao.getByCode(ApplicationNavigationEntryModel.class, code);
		Session session = Sessions.getCurrent();
		session.setAttribute("currentPage", navigationEntry);
	}

}
