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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import de.rs.scrumit.dao.BaseEntityAuditDao;
import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.ProjectModel;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.service.ProjectService;
import de.rs.scrumit.service.ReleaseService;

@Service("releaseService")
@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class DefaultReleaseService implements ReleaseService {

	@Autowired
	private BaseEntityCRUDDao baseDao;
	
	@Autowired
	private BaseEntityAuditDao auditDao;
	
	@Autowired
	private ProjectService projectService;
	
	@Override
	public ReleaseModel getRelease(String code) {
		return baseDao.getByCode(ReleaseModel.class, code);
	}
	
	@Override
	public List<ReleaseModel> getReleaseOfCurrentProject() {
		ProjectModel currentProject = projectService.getCurrentProject();
		projectService.setCurrentProject(currentProject);
		List<ReleaseModel> releases = currentProject.getReleasePlan().getReleases();
		
		return releases;
	}
	
	@Override
	public ReleaseModel updateRelease(ReleaseModel release) {
		return baseDao.update(release);
	}
	
	@Override
	public void updateReleaseDescription(String code, String description) {
		ReleaseModel release = getRelease(code);
		release.setDescription(description);
		baseDao.update(release);
	}

	@Override
	public void createReleaseOfCurrentProject(ReleaseModel release) {
		ProjectModel currentProject = projectService.getCurrentProject();
		release.setReleasePlan(currentProject.getReleasePlan());
		currentProject.getReleasePlan().getReleases().add(release);
		baseDao.save(release);
	}
}
