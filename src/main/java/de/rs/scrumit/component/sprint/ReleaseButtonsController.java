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
package de.rs.scrumit.component.sprint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zul.Window;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ReleaseModel;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReleaseButtonsController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;
	
	private ReleaseModel release;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		subscribe("onReleaseSelected", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				release = (ReleaseModel) event.getData();
			}
		});
	}
	
	@Listen("onClick = #editReleaseBtn")
    public void onEditRelease(Event event) {
		//TODO find release in parent element
        Window window = (Window)Executions.createComponents("", null, null);
        window.doModal();
    }
	
	@Listen("onClick = #addSprintBtn")
	public void onAddSprint(Event event) {
		getSelf().getPage().setAttribute("release", release);
		Window window = (Window)Executions.createComponents("/widgets/sprint/new_sprint_dialog.zul", getSelf().getPage().getFirstRoot(), null);
		window.doModal();
	}
}
