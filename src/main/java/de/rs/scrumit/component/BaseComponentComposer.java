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
package de.rs.scrumit.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;

public class BaseComponentComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 7673611284920835761L;
	
	private EventQueue<Event> eventQueue = EventQueues.lookup("connection", true);

	public void triggerEvent(String name, Object data) {
		eventQueue.publish(new Event(name, null, data));
	}
	
	public void subscribe(final String eventName, final EventListener<Event> listener) {
		eventQueue.subscribe(new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (eventName.equals(event.getName())) {
					listener.onEvent(event);
				}
			}
		});
	}
}
