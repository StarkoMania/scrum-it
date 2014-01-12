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
package de.rs.scrumit.controller;

import java.util.LinkedList;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Timer;

import de.rs.scrumit.init.Initializator;
import de.rs.scrumit.service.InitializationSelectionEntry;
import de.rs.scrumit.service.InitializationService;

@VariableResolver(DelegatingVariableResolver.class)
public class InitializationController extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	
	private static final String PROCESSING_TEXT = "Processing...";
	 
    @Wire
    private Listbox nameList, downList;
    @Wire
    private Label selectedText;
    @Wire
    private Button fetchBtn;
    @Wire
    private Groupbox server;
    @Wire
    private Image serverimg;
    @Wire
    private Timer timer;
    
    @WireVariable
    private InitializationService initializationService;
 
    private LinkedList<Listitem> fetchingItems = new LinkedList<Listitem>(); // Items need fetch
 
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        ListModelList<InitializationSelectionEntry> multipleList = new ListModelList<InitializationSelectionEntry>(initializationService.getAllPossibleEntries());
        multipleList.setMultiple(true); // New way to set mutiple selection
        nameList.setModel(multipleList);
    }
 
    @Listen("onSelect = #nameList")
    public void countSelectItem(Event e) {
        selectedText.setValue(nameList.getSelectedItems().size() + " item(s)");
    }
 
    @Listen("onClick = #fetchBtn")
    public void fetchFileFromServer(Event e) {
        Set<Listitem> selectedList = nameList.getSelectedItems();
        if (selectedList.isEmpty()) {
            alert("No Name Selected");
            return;
        }
        fetchingItems.addAll(selectedList);
        nameList.getItems().removeAll(selectedList);
        selectedText.setValue("");
 
        Events.echoEvent("onAddNameEvent", timer, null);
 
        Clients.showBusy(server, PROCESSING_TEXT + "(" + fetchingItems.size() + ")");
        serverimg.setSrc("/imgs/webserver_busy.png");
    }
 
    @Listen("onAddNameEvent = #timer")
    public void processingFiles() {
        timer.start();
    }
 
    @Listen("onTimer = #timer")
    public void fetchingSimulatorTimer() {
        Listitem item = ((Listitem) fetchingItems.pop());
        InitializationSelectionEntry entry = (InitializationSelectionEntry) item.getValue();
        Listcell c = new Listcell();
        c.appendChild(new A(entry.getDescription()));
        item.appendChild(c);
        item.setParent(downList);
 
        Clients.showBusy(server, PROCESSING_TEXT + entry.getLabel());
        try {
        	Initializator initializator = (Initializator) SpringUtil.getBean(entry.getBeanName());
        	initializator.initialize();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        if (fetchingItems.isEmpty()) {
            Clients.clearBusy(server);
            serverimg.setSrc("/imgs/webserver.png");
        } else {
        	timer.start();
        }
    }
}