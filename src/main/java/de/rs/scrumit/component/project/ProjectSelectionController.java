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
package de.rs.scrumit.component.project;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ProjectModel;
import de.rs.scrumit.service.ProjectService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProjectSelectionController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;
	
	@Wire
	Combobox projectsCombobox;
	
	@WireVariable("projectService")
	ProjectService projectService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		subscribe("onProjectsChangeEvent", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				reloadData();
			}
		});
		
		reloadData();
	}
	
    public void reloadData() {
		List<ProjectModel> projects = projectService.getAllProjects();
		ListModelList<ProjectModel> model = new ListModelList<ProjectModel>(projects, true);
		projectsCombobox.setModel(model);
		
		ProjectModel project = projectService.getCurrentProject();
		int index = projects.indexOf(project);
		projectsCombobox.setSelectedIndex(index);
    }
    
	@Listen("onSelect = #projectsCombobox")
    public void projectSelected() {
    	Comboitem selectedItem = projectsCombobox.getSelectedItem();
   		ProjectModel project = (ProjectModel) selectedItem.getValue();
   		projectService.setCurrentProject(project);
   		triggerEvent("onProjectSelectionChange", null);
    }
}
