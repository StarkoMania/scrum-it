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
package de.rs.scrumit.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.UserModel;


@Service("userInitializator")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserInitializator implements Initializator {

	@Autowired
	private BaseEntityCRUDDao baseEntityCRUDDao;
	
	@Override
	public void initialize() {
		
		UserModel user = new UserModel("admin", "1234", "Administrator", "admin@scrum-it.com");
		baseEntityCRUDDao.save(user);
		
		
	}

}
