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

package com.Sarah.HideANote;

import javax.swing.*;
import java.io.*;
import com.Sarah.HideANote.core.*;
import com.Sarah.HideANote.persistence.*;

/**
 * Get the party started
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                if (new StateLock("HideANote").isAppActive()) {
					System.out.println("Application is currently locked. Manually remove " + 
						System.getProperty("user.home") + File.separator + ".HideANote.lock");
                    return;
                }

                new Application();
            }
        });
    }
}
