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

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Columnchildren;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.entity.SprintModel;
import de.rs.scrumit.service.ReleaseService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SprintOverviewController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;

	@Wire
	private Component releaseTable;
	
	@WireVariable("releaseService")
	private ReleaseService releaseService;
	
	public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception {
		super.doAfterCompose(comp);
		subscribe("onReleasesChangeEvent", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				reloadTableData();
			}
		});
		reloadTableData();
	}

	private void reloadTableData() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		
		releaseTable.getChildren().clear();
		List<ReleaseModel> releases = releaseService.getReleaseOfCurrentProject();
		addReleaseDropZone();
		for (ReleaseModel release : releases) {
			Columnchildren column = new Columnchildren();
			column.setHflex("1");
			column.setVflex("1");
			column.setDraggable("release");
			Panel releasePanel = new Panel();
			releasePanel.setBorder("normal");
			releasePanel.setSclass("traffic");
			column.getChildren().add(releasePanel);
			Caption releaseCaption = new Caption(release.getCode()+" ("+format.format(release.getReleaseDate())+")");
			releaseCaption.setIconSclass("z-icon-film");
			releaseCaption.setSclass("small");
			releasePanel.getChildren().add(releaseCaption);
			Button actionButton = new Button("Actions");
			actionButton.setSclass("btn btn-minier btn-primary pull-right");
			actionButton.setPopup("releasepp, position=after_end");
			releaseCaption.getChildren().add(actionButton);
			Panelchildren releaseContentArea = new Panelchildren();
			releasePanel.getChildren().add(releaseContentArea);
			Vlayout sprintArea = new Vlayout();
			sprintArea.setVflex("1");
			sprintArea.setHflex("1");
			sprintArea.setSpacing("5px");
			releaseContentArea.getChildren().add(sprintArea);
			if (release.getSprints() != null) {
				for (SprintModel sprint : release.getSprints()) {
					Panel sprintPrePanel = new Panel();
					sprintPrePanel.setId(sprint.getCode()+"-zone");
					sprintPrePanel.setHeight("15px");
					sprintPrePanel.setBorder("none");
					sprintPrePanel.setDroppable("sprint");
					sprintArea.getChildren().add(sprintPrePanel);
					
					Panel sprintPanel = new Panel();
					sprintPanel.setId(sprint.getCode());
					sprintPanel.setHeight("150px");
					sprintPanel.setHflex("1");
					sprintPanel.setBorder("normal");
					sprintPanel.setDraggable("sprint");
					sprintArea.getChildren().add(sprintPanel);
					
					Caption sprintCaption = new Caption(sprint.getCode()+" ("+format.format(sprint.getStartdate())+" - "+format.format(sprint.getEnddate())+")");
					sprintCaption.setIconSclass("z-icon-repeat");
					sprintCaption.setSclass("small");
					sprintPanel.getChildren().add(sprintCaption);
					Panelchildren sprintContent = new Panelchildren();
					sprintPanel.getChildren().add(sprintContent);
					Label label = new Label(sprint.getCode());
					sprintContent.getChildren().add(label);
				}
			}
			
			Panel dropZone = createDropZone();
			sprintArea.getChildren().add(dropZone);
			
//			<columnchildren style="padding: 10px 10px 10px 0px;" hflex="1">
//			<panel border="normal" sclass="traffic">
//				<caption iconSclass="z-icon-film" label="Release 1" sclass="small">
//					<button label="Actions" sclass="btn btn-minier btn-primary pull-right" popup="releasepp, position=after_end"/>
//				</caption>
//				<panelchildren>
//					<vlayout vflex="1" hflex="1" spacing="5px">
//						<panel id="panel1-zone" height="15px" border="none" droppable="true">
//							<panelchildren></panelchildren>
//						</panel>
//						<panel id="panel1" height="150px" hflex="1" border="normal" draggable="true">
//							<panelchildren>Panel C1-P1</panelchildren>
//						</panel>
			
			//add sprints of release
			
			releaseTable.getChildren().add(column);
			addReleaseDropZone();
		}
	}

	private void addReleaseDropZone() {
		Columnchildren releaseDropZone = new Columnchildren();
		releaseDropZone.setWidth("15px");
		releaseDropZone.setVflex("1");
		releaseDropZone.setDroppable("release");
		releaseTable.getChildren().add(releaseDropZone);
	}

	private Panel createDropZone() {
		Panel dropZone = new Panel();
		dropZone.setHeight("15px");
		dropZone.setBorder("none");
		dropZone.setDroppable("sprint");
		Panelchildren child = new Panelchildren();
		dropZone.getChildren().add(child);
		return dropZone;
	};
	
	@Listen("onClick = #addReleaseBtn")
    public void showModal(Event e) {
        Window window = (Window)Executions.createComponents("/widgets/release/new_release_dialog.zul", null, null);
        window.doModal();
    }
	
	@Listen("onDrop = panel")
	public void positionChanged(DropEvent event) {
		Component dragged = event.getDragged();
		Component target = event.getTarget();
		target.getParent().insertBefore(dragged, target);
		
		Component dropZone = Selectors.iterable(releaseTable, "#"+dragged.getId()+"-zone").iterator().next();
		dragged.getParent().insertBefore(dropZone, dragged);
		
		Component main = Selectors.iterable(target.getPage(), "#main").iterator().next();
		Clients.resize(main);
	}
	
}
