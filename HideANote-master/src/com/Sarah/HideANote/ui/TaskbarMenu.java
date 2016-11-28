// HideANote - auto hide note thing
// Copyright 2016 by Sarah J. Deer
// All Rights Reserved.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//

package com.Sarah.HideANote.ui;

import java.awt.*;
import java.awt.event.*;

/**
 * The TaskbarMenu class provides all of the click targets
 * for the taskbar. e.g. About, Help, etc...
 */
public class TaskbarMenu extends PopupMenu {

    private static final long serialVersionUID = 6815348601297451742L;

    /**
     * Default constructor
     */
    public TaskbarMenu() {
        super();
		addSettings();
        addAbout();
        addHelp();
        addFiller();
        addExit();
    }

    /**
     * modularized helper for adding the settings menu and associated trigger
     */
    private void addSettings() {
        MenuItem settingsItem = new MenuItem("Settings");
        
        settingsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingsDialog.getInstance().setVisible(true);
            }
        });

        this.add(settingsItem);
    }

    /**
     * modularized helper for adding the help menu and associated trigger
     */
    private void addHelp() {
        MenuItem helpItem = new MenuItem("Help");
        
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpDialog.getInstance().setVisible(true);
            }
        });

        this.add(helpItem);
    }
    
    /**
     * modularized helper for adding the about menu and associated trigger
     */
    private void addAbout() {
        MenuItem aboutItem = new MenuItem("About");
        
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutDialog.getInstance().setVisible(true);
            }
        });

        this.add(aboutItem);
    }

    /**
     * modularized helper for adding the exit menu and associated trigger
     */
    private void addExit() {

        MenuItem exitItem = new MenuItem("Exit");

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(exitItem);
    }

    /**
     * modularized helper for adding filler to the menu
     */
    private void addFiller() {
        this.add(new MenuItem(""));
    }
}
