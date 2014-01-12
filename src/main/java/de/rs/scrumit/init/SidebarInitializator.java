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
package de.rs.scrumit.init;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import de.rs.hibernate.LocalizedString;
import de.rs.scrumit.dao.BaseEntityCRUDDao;
import de.rs.scrumit.entity.ApplicationMenuModel;
import de.rs.scrumit.entity.ApplicationNavigationEntryModel;

@Service("sidebarInitializator")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SidebarInitializator implements Initializator {

	@Autowired
	private BaseEntityCRUDDao baseEntityCRUDDao;
	
	@Override
	public void initialize() {
		
		ApplicationMenuModel sidebar = new ApplicationMenuModel("sidebar");
		baseEntityCRUDDao.save(sidebar);
		
		ApplicationNavigationEntryModel dashboard = createDashboard(sidebar, 0);
		createSystem(sidebar, 1);
		createTeam(sidebar, 2);
		createMeetings(sidebar, 3);
		createReleaseplan(sidebar, 4);
		createSprints(sidebar, 5);
		createBacklog(sidebar, 6);
		createEpics(sidebar, 7);
		createDefects(sidebar, 8);
		createDocumentation(sidebar, 9);
		
		sidebar.setDefaultNavigationEntry(dashboard);
		baseEntityCRUDDao.update(sidebar);
	}

	private ApplicationNavigationEntryModel createTeam(ApplicationMenuModel sidebar, Integer position) {
		return ceateNavigationEntry("team", sidebar, "Projektteam", "/page/team.zul", "leaf", position);
	}

	private ApplicationNavigationEntryModel createDashboard(ApplicationMenuModel sidebar, Integer position) {
		return ceateNavigationEntry("dashboard", sidebar, "Dashboard", "/page/dashboard.zul", "dashboard", position);
	}

	private void createReleaseplan(ApplicationMenuModel sidebar, Integer position) {
		ApplicationNavigationEntryModel releaseplan = ceateParentNavigationEntry("releaseplan", sidebar, "Releaseplan", "road", position);
		ceateSubNavigationEntry("release-overview", sidebar, "Releaseübersicht", releaseplan, "/page/release/overview.zul", 0);
		ceateSubNavigationEntry("release-deplyoment", sidebar, "Deploymentprozesse", releaseplan, "/page/release/deplyoment.zul", 1);
		ceateSubNavigationEntry("release-delivery", sidebar, "Lieferungen", releaseplan, "/page/release/delivery.zul", 2);
	}
	
	private void createSystem(ApplicationMenuModel sidebar, Integer position) {
		ApplicationNavigationEntryModel releaseplan = ceateParentNavigationEntry("system", sidebar, "System", "globe", position);
		ceateSubNavigationEntry("system-conclusion", sidebar, "Zusammenfassung", releaseplan, "/page/system/conclusion.zul", 0);
		ceateSubNavigationEntry("system-environments", sidebar, "Umgebungen", releaseplan, "/page/system/environments.zul", 1);
		ceateSubNavigationEntry("system-components", sidebar, "Komponenten", releaseplan, "/page/system/components.zul", 2);
		ceateSubNavigationEntry("system-features", sidebar, "Funktionen", releaseplan, "/page/system/features.zul", 3);
		ceateSubNavigationEntry("system-processes", sidebar, "Prozesse", releaseplan, "/page/system/processes.zul", 4);
	}
	
	
	private void createDocumentation(ApplicationMenuModel sidebar, Integer position) {
		ApplicationNavigationEntryModel documentation = ceateParentNavigationEntry("documentation", sidebar, "Dokumentation", "edit", position);
		ceateSubNavigationEntry("documentation-architecture", sidebar, "Technische Doku.", documentation, "/page/documentation/architecture.zul", 0);
		ApplicationNavigationEntryModel operationsGuide = ceateBranchSubNavigationEntry("documentation-operation", sidebar, "Betriebshandung", documentation, 1);
		ceateSubNavigationEntry("documentation-operation-installation", sidebar, "Installation", operationsGuide, "/page/documentation/operation/installation.zul", 0);
		ceateSubNavigationEntry("documentation-operation-configuration", sidebar, "Konfiguration", operationsGuide, "/page/documentation/operation/configuration.zul", 1);
		ceateSubNavigationEntry("documentation-operation-monitoring", sidebar, "Überwachung", operationsGuide, "/page/documentation/operation/monitoring.zul", 2);
		ceateSubNavigationEntry("documentation-operation-logging", sidebar, "Protokollierung", operationsGuide, "/page/documentation/operation/logging.zul", 3);
		ceateSubNavigationEntry("documentation-usersGuide", sidebar, "Benutzungshandbuch", documentation, "/page/documentation/usersGuide.zul", 2);
	}
	
	private void createSprints(ApplicationMenuModel sidebar, Integer position) {
		ApplicationNavigationEntryModel sprints = ceateParentNavigationEntry("sprints", sidebar, "Sprints", "repeat", position);
		ceateSubNavigationEntry("sprint-overview", sidebar, "Sprintübersicht", sprints, "/page/sprint/overview.zul", 0);
	}
	
	private ApplicationNavigationEntryModel createBacklog(ApplicationMenuModel sidebar, Integer position) {
		return ceateNavigationEntry("backlog", sidebar, "Backlog", "/page/backlog.zul", "list", position);
	}
	
	private ApplicationNavigationEntryModel createEpics(ApplicationMenuModel sidebar, Integer position) {
		return ceateNavigationEntry("epics", sidebar, "Epics", "/page/epics.zul", "folder-open", position);
	}
	
	private ApplicationNavigationEntryModel createDefects(ApplicationMenuModel sidebar, Integer position) {
		return ceateNavigationEntry("defects", sidebar, "Fehler", "/page/defects.zul", "fire", position);
	}

	private void createMeetings(ApplicationMenuModel sidebar, Integer position) {
		ApplicationNavigationEntryModel meetings = ceateParentNavigationEntry("meetings", sidebar, "Termine", "comment", position);
		ApplicationNavigationEntryModel dailyMeeting = ceateBranchSubNavigationEntry("meeting-daily", sidebar, "Daily Meeting", meetings, 0);
		ceateSubNavigationEntry("meeting-daily-today", sidebar, "Heute", dailyMeeting, "/page/meeting/daily/today.zul", 0);
		ceateSubNavigationEntry("meeting-daily-yesterday", sidebar, "Gestern", dailyMeeting, "/page/meeting/daily/yesterday.zul", 1);
		ceateSubNavigationEntry("meeting-daily-beforeYesterday", sidebar, "Vorgestern", dailyMeeting, "/page/meeting/daily/beforeYesterday.zul", 2);
		ceateSubNavigationEntry("meeting-daily-lastWeek", sidebar, "Letzte Woche", dailyMeeting, "/page/meeting/daily/lastWeek.zul", 3);
		ceateSubNavigationEntry("meeting-daily-all", sidebar, "Alle", dailyMeeting, "/page/meeting/daily/all.zul", 4);
		ApplicationNavigationEntryModel groomingMeeting = ceateBranchSubNavigationEntry("meeting-grooming", sidebar, "Grooming Meeting", meetings, 1);
		ceateSubNavigationEntry("meeting-grooming-new", sidebar, "Neues Meeting", groomingMeeting, "/page/meeting/grooming/new.zul", 0);
		ceateSubNavigationEntry("meeting-grooming-goThrough", sidebar, "Durchführen", groomingMeeting, "/page/meeting/grooming/goThrough.zul", 1);
		ceateSubNavigationEntry("meeting-grooming-archiv", sidebar, "Archiv", groomingMeeting, "/page/meeting/grooming/archiv.zul", 2);
		ApplicationNavigationEntryModel reviewMeeting = ceateBranchSubNavigationEntry("meeting-review", sidebar, "Review Meeting", meetings, 2);
		ceateSubNavigationEntry("meeting-review-new", sidebar, "Neues Meeting", reviewMeeting, "/page/meeting/review/new.zul", 0);
		ceateSubNavigationEntry("meeting-review-goThrough", sidebar, "Durchführen", reviewMeeting, "/page/meeting/review/goThrough.zul", 1);
		ceateSubNavigationEntry("meeting-review-archiv", sidebar, "Archiv", reviewMeeting, "/page/meeting/review/archiv.zul", 2);
		ApplicationNavigationEntryModel retrospectiveMeeting = ceateBranchSubNavigationEntry("meeting-retrospective", sidebar, "(Retro-/)Prospective Meeting", meetings, 3);
		ceateSubNavigationEntry("meeting-retrospective-new", sidebar, "Neues Meeting", retrospectiveMeeting, "/page/meeting/retrospective/new.zul", 0);
		ceateSubNavigationEntry("meeting-retrospective-goThrough", sidebar, "Durchführen", retrospectiveMeeting, "/page/meeting/retrospective/goThrough.zul", 1);
		ceateSubNavigationEntry("meeting-retrospective-archiv", sidebar, "Archiv", retrospectiveMeeting, "/page/meeting/retrospective/archiv.zul", 2);
		ApplicationNavigationEntryModel defectMeeting = ceateBranchSubNavigationEntry("meeting-defect", sidebar, "Defect Meeting", meetings, 4);
		ceateSubNavigationEntry("meeting-defect-new", sidebar, "Neues Meeting", defectMeeting, "/page/meeting/defect/new.zul", 0);
		ceateSubNavigationEntry("meeting-defect-goThrough", sidebar, "Durchführen", defectMeeting, "/page/meeting/defect/goThrough.zul", 1);
		ceateSubNavigationEntry("meeting-defect-archiv", sidebar, "Archiv", defectMeeting, "/page/meeting/defect/archiv.zul", 2);
		ApplicationNavigationEntryModel lessonsLearnedMeeting = ceateBranchSubNavigationEntry("meeting-lessonsLearned", sidebar, "Lessons Learned Meeting", meetings, 4);
		ceateSubNavigationEntry("meeting-lessonsLearned-new", sidebar, "Neues Meeting", lessonsLearnedMeeting, "/page/meeting/lessonsLearned/new.zul", 0);
		ceateSubNavigationEntry("meeting-lessonsLearned-goThrough", sidebar, "Durchführen", lessonsLearnedMeeting, "/page/meeting/lessonsLearned/goThrough.zul", 1);
		ceateSubNavigationEntry("meeting-lessonsLearned-archiv", sidebar, "Archiv", lessonsLearnedMeeting, "/page/meeting/lessonsLearned/archiv.zul", 2);
	}

	private ApplicationNavigationEntryModel ceateParentNavigationEntry(String code, ApplicationMenuModel menu, String label, String icon, Integer menuPosition) {
		ApplicationNavigationEntryModel navEntry = new ApplicationNavigationEntryModel(code, menu, createLabel(label));
		navEntry.setIcon(icon);
		navEntry.setPosition(menuPosition);
		baseEntityCRUDDao.save(navEntry);
		
		return navEntry;
	}

	private LocalizedString createLabel(String label) {
		return new LocalizedString(label, Locale.GERMAN);
	}
	
	private ApplicationNavigationEntryModel ceateBranchSubNavigationEntry(String code, ApplicationMenuModel menu, String label, ApplicationNavigationEntryModel parent, Integer menuPosition) {
		ApplicationNavigationEntryModel navEntry = new ApplicationNavigationEntryModel(code, menu, createLabel(label));
		navEntry.setParent(parent);
		navEntry.setPosition(menuPosition);
		baseEntityCRUDDao.save(navEntry);
		
		return navEntry;
	}
	
	private ApplicationNavigationEntryModel ceateSubNavigationEntry(String code, ApplicationMenuModel menu, String label, ApplicationNavigationEntryModel parent, String target, Integer menuPosition) {
		ApplicationNavigationEntryModel navEntry = new ApplicationNavigationEntryModel(code, menu, createLabel(label));
		navEntry.setParent(parent);
		navEntry.setTarget(target);
		navEntry.setPosition(menuPosition);
		baseEntityCRUDDao.save(navEntry);
		
		return navEntry;
	}
	
	private ApplicationNavigationEntryModel ceateNavigationEntry(String code, ApplicationMenuModel menu, String label, String target, String icon, Integer menuPosition) {
		ApplicationNavigationEntryModel navEntry = new ApplicationNavigationEntryModel(code, menu, createLabel(label));
		navEntry.setTarget(target);
		navEntry.setIcon(icon);
		navEntry.setPosition(menuPosition);
		baseEntityCRUDDao.save(navEntry);
		
		return navEntry;
	}

}
