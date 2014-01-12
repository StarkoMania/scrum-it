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

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.service.ReleaseService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CreateReleaseController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;

	@Wire
	private Textbox codeBox;

	@Wire
	private Datebox releaseDateBox;
	
	@Wire
	private CKeditor descriptionBox;

	@WireVariable("releaseService")
	ReleaseService releaseService;
    
    @Listen("onClick=#create; onOK=#creationWin")
    public void saveAndClose(Event e) {
    	String code = codeBox.getValue();
    	Date releaseDate = releaseDateBox.getValue();
    	String description = descriptionBox.getValue();
    	
		ReleaseModel release = new ReleaseModel(code, releaseDate);
		release.setDescription(description);
    	releaseService.createReleaseOfCurrentProject(release);
    	
    	triggerEvent("onReleasesChangeEvent", null);
    	close(null);
    }
    
    @Listen("onClick = #close")
    public void close(Event e) {
    	getSelf().detach();
    }
}
