/*
 * Copyright (C) ${year} Robert Stark
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
package de.rs.scrumit.component.sprint;

import java.util.Date;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.entity.SprintModel;
import de.rs.scrumit.service.ReleaseService;
import de.rs.scrumit.service.SprintService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CreateSprintController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;

	@Wire
	private Textbox codeBox;

	@Wire
	private Datebox startDateBox;

	@Wire
	private Datebox endDateBox;
	
	@Wire
	private CKeditor descriptionBox;

	@WireVariable("releaseService")
	private ReleaseService releaseService;
	
	@WireVariable("sprintService")
	private SprintService sprintService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
    
    @Listen("onClick=#create; onOK=#creationWin")
    public void saveAndClose(Event e) {
    	String code = codeBox.getValue();
    	Date startDate = startDateBox.getValue();
    	Date endDate = endDateBox.getValue();
    	String description = descriptionBox.getValue();
    	
    	ReleaseModel release = (ReleaseModel) getSelf().getPage().getAttribute("release");
    	release = releaseService.getRelease(release.getCode());
		SprintModel sprint = new SprintModel(code, release , startDate, endDate);
		sprint.setDescription(description);
		sprintService.createSprint(sprint );
    	
    	triggerEvent("onSprintCreatedEvent", null);
    	close(null);
    }
    
    @Listen("onClick = #close")
    public void close(Event e) {
    	getSelf().detach();
    }
}
