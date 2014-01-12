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
package de.rs.scrumit.service;

import java.util.List;

import de.rs.scrumit.entity.IssueModel;

public interface IssueListService {

	public List<IssueModel> getIssueList();
	
	public IssueModel getIssue(Long id);
	
	public IssueModel saveIssue(IssueModel issue);
	
	public IssueModel updateIssue(IssueModel issue);
	
	public void deleteIssue(IssueModel issue);
	
}
