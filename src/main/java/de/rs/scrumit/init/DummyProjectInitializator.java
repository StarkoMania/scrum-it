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

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.ProjectModel;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.entity.ReleasePlanModel;
import de.rs.scrumit.entity.SprintModel;

@Service("dummyProjectInitializator")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class DummyProjectInitializator implements Initializator {

	@Autowired
	private BaseEntityCRUDDao baseDao;
	
	@Override
	public void initialize() {
		
		ProjectModel project = new ProjectModel("dummy", "Dummy Project", createDate(2013, 11, 1));
		ReleasePlanModel releasePlan = new ReleasePlanModel("dummy-rp", project);
		project.setReleasePlan(releasePlan);
		baseDao.save(project);
		
		{
			ReleaseModel release = new ReleaseModel("dummy-1.0.0.0", releasePlan, createDate(2014, 0, 31));
			release.setDescription("first dummy implementation with basic function and documentation");
			release.setReleasePlan(releasePlan);
			baseDao.save(release);
			
			{
				SprintModel sprint = new SprintModel("Initial-Sprint-1", release, createDate(2013, 11, 1), createDate(2013, 11, 20));
				sprint.setDescription("First initial sprint, setup environment");
				baseDao.save(sprint);
			}
			{
				SprintModel sprint = new SprintModel("Initial-Sprint-2", release, createDate(2014, 0, 1), createDate(2014, 0, 31));
				sprint.setDescription("Second initial sprint for setting up the environment");
				baseDao.save(sprint);
			}
			{
				SprintModel sprint = new SprintModel("Initial-Sprint-3", release, createDate(2014, 1, 1), createDate(2014, 1, 28));
				sprint.setDescription("Second initial sprint for setting up the environment");
				baseDao.save(sprint);
			}
		}
		
		{
			ReleaseModel release = new ReleaseModel("dummy-1.0.1.0", releasePlan, createDate(2014, 3, 31));
			release.setDescription("basic scrum functionality");
			release.setReleasePlan(releasePlan);
			baseDao.save(release);
			
			{
				SprintModel sprint = new SprintModel("S3", release, createDate(2014, 2, 1), createDate(2014, 2, 31));
				baseDao.save(sprint);
			}
			{
				SprintModel sprint = new SprintModel("S4", release, createDate(2014, 3, 1), createDate(2014, 3, 30));
				baseDao.save(sprint);
			}
		}
	}

	private Date createDate(int year, int month, int day) {
		return new GregorianCalendar(year, month, day).getTime();
	}

}
