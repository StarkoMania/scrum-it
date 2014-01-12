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

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import de.rs.scrumit.AppConst;
import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.ProjectModel;
import de.rs.scrumit.entity.ReleasePlanModel;
import de.rs.scrumit.service.EntryAlreadyExists;
import de.rs.scrumit.service.ProjectService;

@Service("projectService")
@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class DefaultProjectService implements ProjectService {

	@Autowired
	private BaseEntityCRUDDao dao;
	
	@Override
	public List<ProjectModel> getAllProjects() {
		return dao.queryAllByClass(ProjectModel.class);
	}
	
	@Override
	public ProjectModel getCurrentProject() {
		Session session = Sessions.getCurrent();
		ProjectModel project = (ProjectModel) session.getAttribute(AppConst.Session.CURRENT_PROJECT);
		if (project == null) {
			project = getAllProjects().get(0);
			setCurrentProject(project);
		} else {
			project = dao.reload(project);
		}
		return project;
	}
	
	@Override
	public void setCurrentProject(ProjectModel project) {
		Session session = Sessions.getCurrent();
		session.setAttribute(AppConst.Session.CURRENT_PROJECT, project);
	}

	@Override
	public ProjectModel createProject(String code, String name, String description, Date startDate) throws EntryAlreadyExists {
		try {
			if (dao.getByCode(ProjectModel.class, code) != null) {
				throw new EntryAlreadyExists("Entry with code: "+code+" already exists");
			}
		} catch (NoResultException e) {
			// all right, that's what we want here
		}
		ProjectModel project = new ProjectModel(code, name, startDate);
		project.setDescription(description);
		project.setReleasePlan(new ReleasePlanModel(code+"-rp", project));
		project = dao.save(project);
		return project;
	}

}
