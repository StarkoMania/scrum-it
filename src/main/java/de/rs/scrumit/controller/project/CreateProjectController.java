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
package de.rs.scrumit.controller.project;

import java.util.Date;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ProjectModel;
import de.rs.scrumit.service.EntryAlreadyExists;
import de.rs.scrumit.service.ProjectService;

@VariableResolver(DelegatingVariableResolver.class)
public class CreateProjectController extends BaseComponentComposer {
	
	private static final long serialVersionUID = 1L;
	
	@Wire
	Textbox code;
	
	@Wire
	Textbox name;
	
	@Wire
	Textbox description;
	
	@Wire
	Datebox startDate;
	
	@Wire
	Label message;
	
	@WireVariable
	ProjectService projectService;
	
	@Wire
    Window createProjectWin;

	public CreateProjectController() {
		super();
		System.out.println("new "+getClass().getName()+" was created");
	}

	@Listen("onClick=#create; onOK=#createProjectWin")
	public void createProject(){
		System.out.println("start project creation: "+this);
		String codeValue = code.getValue();
		String nameValue = name.getValue();
		String descriptionValue = description.getValue();
		Date startDateValue = startDate.getValue();
		
		try {
		ProjectModel project = projectService.createProject(codeValue, nameValue, descriptionValue, startDateValue);
			message.setValue("Das Projekt "+project.getName()+" wurde erfolgreich angelegt.");
			message.setSclass("");
			triggerEvent("onProjectsChangeEvent", null);
			close(null);
		} catch (EntryAlreadyExists e) {
			message.setValue("Es existiert bereits ein Projekt mit dem Code '"+codeValue+"'");
		}
	}
	
	@Listen("onClick = #close")
    public void close(Event e) {
		createProjectWin.detach();
    }
	
}
