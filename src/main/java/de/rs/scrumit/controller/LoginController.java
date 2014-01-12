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

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import de.rs.scrumit.service.AuthenticationService;
import de.rs.scrumit.service.UserCredential;

@VariableResolver(DelegatingVariableResolver.class)
public class LoginController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	@Wire
	Textbox account;
	@Wire
	Textbox password;
	@Wire
	Label message;
	
	@WireVariable
	AuthenticationService authService;

	
	@Listen("onClick=#login; onOK=#loginWin")
	public void doLogin(){
		String nm = account.getValue();
		String pd = password.getValue();
		
		if(!authService.login(nm,pd)){
			message.setValue("account or password are not correct.");
			return;
		}
		UserCredential cre= authService.getUserCredential();
		message.setValue("Welcome, "+cre.getName());
		message.setSclass("");
		
		Executions.sendRedirect("/");
		
	}
}
