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
package de.rs.scrumit.component.release;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.service.ReleaseService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class OverviewTableController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OverviewTableController.class);
	
	@Wire
	private Rows releaseOverviewRows;
	
	@WireVariable("releaseService")
	private ReleaseService releaseService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		subscribe("onReleasesChangeEvent", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				reloadGridRows();
			}
		});
		
		reloadGridRows();
	}

	private void reloadGridRows() {
		releaseOverviewRows.getChildren().clear();
		List<ReleaseModel> releases = releaseService.getReleaseOfCurrentProject();
		for (final ReleaseModel release : releases) {
			Row row = createGridRow(release);
			releaseOverviewRows.getChildren().add(row );
		}
	}

	private Row createGridRow(final ReleaseModel release) {
		EventListener<Event> codeChangeListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				ReleaseModel release = (ReleaseModel) event.getTarget().getParent().getAttribute("release");
				String oldValue = release.getCode();
				try {
					String code = ((Textbox) event.getTarget()).getValue();
					release.setCode(code);
					release = releaseService.updateRelease(release);
				} catch (Exception e) {
					LOGGER.error("fail to update release code", e);
					((Textbox) event.getTarget()).setValue(oldValue);
					Clients.alert("Leider konnte der Wert nicht übernommen werden.", "Fail to update release", Clients.NOTIFICATION_TYPE_ERROR);
				}
			}
		};
		EventListener<Event> releaseDateChangeListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				ReleaseModel release = (ReleaseModel) event.getTarget().getParent().getAttribute("release");
				Date oldValue = release.getReleaseDate();
				try {
					Date date = ((Datebox) event.getTarget()).getValue();
					release.setReleaseDate(date);
					release = releaseService.updateRelease(release);
				} catch (Exception e) {
					LOGGER.error("fail to update release date", e);
					((Datebox) event.getTarget()).setValue(oldValue);
					Clients.alert("Leider konnte der Wert nicht übernommen werden.", "Fail to update release", Clients.NOTIFICATION_TYPE_ERROR);
				}
			}
		};
		EventListener<Event> descriptionEvent = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Map<String, Object> arguments = new HashMap<String, Object>();
				arguments.put("release", release.getCode());
				event.getTarget().getDesktop().setAttribute("release", release.getCode());
				Window window = (Window)Executions.createComponents("/widgets/release/editDescription.zul", null, arguments );
				window.doModal();
			}
		};
		EventListener<Event> addSprintEvent = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				
			}
		};
		
		Row row = new Row();
		row.setAttribute("release", release);
		Textbox codeBox = new Textbox(release.getCode());
		codeBox.setWidth("99%");
		codeBox.setInplace(true);
		codeBox.addEventListener("onChange", codeChangeListener);
		row.getChildren().add(codeBox);
		Datebox releaseDateBox = new Datebox(release.getReleaseDate());
		releaseDateBox.setWidth("99%");
		releaseDateBox.setFormat("dd.MM.yyyy");
		releaseDateBox.setInplace(true);
		releaseDateBox.addEventListener("onChange", releaseDateChangeListener);
		row.getChildren().add(releaseDateBox);
		String description = release.getDescription();
		if (StringUtils.isEmpty(description)) {
			description = "<i>Noch keine Beschreibung hinzugefügt</i>";
		}
		Html descriptionBox = new Html(description);
		descriptionBox.setWidth("99%");
		descriptionBox.addEventListener("onClick", descriptionEvent);
		row.getChildren().add(descriptionBox);
		
		Div buttonGroup = new Div();
		Button addSprint = new Button("Add Sprint");
		addSprint.setIconSclass("z-icon-repeat");
		addSprint.setSclass("btn btn-sm btn-info");
		addSprint.addEventListener("onClick", addSprintEvent);
		buttonGroup.getChildren().add(addSprint);
		row.getChildren().add(buttonGroup);
		return row;
	}
}
