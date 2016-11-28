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

package com.Sarah.HideANote.core;

import java.awt.*;
import java.net.*;
import com.sarah.HideANote.ui.*;

/**
 * A class to centralize the things an application cares about, such as
 * taskbar icon and menus.
 */
public class Application {

    /**
     * constructor that creates all the necessary components for this 
     * app, connects the dots and provides a taskbar icon
     */
    public Application() {
        BorderlessWindow window = new BorderlessWindow();
        Note note = new Note();
        note.setParent(window);
        window.setVisible(true);
        setupSystemTray();
    }

    /**
     * method to modularize system tray operations
     */
    private void setupSystemTray() {

        final TrayIcon trayIcon;
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();
            URL imageURL = ClassLoader.getSystemResource("tray.png");
            Image image = Toolkit.getDefaultToolkit().createImage(imageURL);

            trayIcon = new TrayIcon(image, "HideANote", new TaskbarMenu());
            trayIcon.setImageAutoSize(true);

            try {
                tray.add(trayIcon);

            } catch (AWTException awtx) {
                //TODO: handle this and present error to user somehow
            }
        } else {
            //TODO: present lack of sys tray support to user, so they know they'll have to forcefully kill the app somehow
        }
    }
}
