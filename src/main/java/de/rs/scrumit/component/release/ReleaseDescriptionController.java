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

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

import de.rs.scrumit.component.BaseComponentComposer;
import de.rs.scrumit.entity.ReleaseModel;
import de.rs.scrumit.service.ReleaseService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReleaseDescriptionController extends BaseComponentComposer {

	private static final long serialVersionUID = 1L;

	@Wire
    Window modalDialog;
	
	@Wire
	CKeditor descriptionEditor;
	
	@WireVariable("releaseService")
	ReleaseService releaseService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);

		String releaseCode = (String) comp.getDesktop().getAttribute("release");
		ReleaseModel release = releaseService.getRelease(releaseCode);
		descriptionEditor.setValue(release.getDescription());
		descriptionEditor.setAttribute("release", release);
	}
     
    @Listen("onClick = #saveBtn")
    public void saveAndClose(Event e) {
    	String description = descriptionEditor.getValue();
    	ReleaseModel release = (ReleaseModel) descriptionEditor.getAttribute("release");
    	release.setDescription(description);
    	releaseService.updateRelease(release);
    	triggerEvent("onReleasesChangeEvent", null);
    	
    	e.getTarget().getDesktop().removeAttribute("release");
        modalDialog.detach();
    }
}
