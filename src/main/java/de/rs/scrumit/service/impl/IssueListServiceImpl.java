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

import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.IssueModel;
import de.rs.scrumit.service.IssueListService;

@Service("issueListService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class IssueListServiceImpl implements IssueListService {

	@Autowired
	BaseEntityCRUDDao dao;
	
	public List<IssueModel>getIssueList() {
		return dao.queryAllByClass(IssueModel.class);
	}
	
	public IssueModel getIssue(Long id){
		return dao.get(IssueModel.class, id);
	}
	
	public IssueModel saveIssue(IssueModel issue){
		dao.save(issue);
		return issue;
	}
	
	public IssueModel updateIssue(IssueModel issue){
		return dao.update(issue);
	}
	
	public void deleteIssue(IssueModel issue){
		dao.delete(issue);
	}

}
