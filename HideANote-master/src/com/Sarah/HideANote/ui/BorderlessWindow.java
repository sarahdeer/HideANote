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
import javax.swing.*;
import com.Sarah.HideANote.core.*;

/**
 * The BorderlessWindow class implements a decoration-less JWindow
 */
public class BorderlessWindow extends JWindow {

    private static JFrame _frame;
    private static final long serialVersionUID = 8515365993272218057L;
	private static BorderlessWindow _self;
	private static final int _width = 300;

    /**
     * default constructor
     */
    public BorderlessWindow() {

        super(_frame = new JFrame() {
            private static final long serialVersionUID = 138914985931428290L;

            public boolean isShowing() {
                return true;
            }
        });

        initializeSelf();
    }

	/**
	 * updates the height of the borderless window
	 * @param percentage the integer percentage of the screen vertical to utilize e.g. 80 => 80%
	 */
	public static void updateHeight(int percentage) {
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float percent = (float)((float)percentage / 100.0f);
		float buffer = screenSize.height - (screenSize.height * percent);
		
        _self.setSize(_width, screenSize.height - (int)buffer);
        int x = _self.getX();
        int y = ((int)buffer / 2);

        _self.setLocation(x, y);
		_self.repaint();
	}
	
	public static void pinLeft() {
		Settings.put("pinLeft","true");
		Settings.put("pinRight","false");
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float percentage = Float.valueOf(Settings.get("verticalPercentage"));
        float percent = (float)(percentage / 100.0f);
		int buffer = (int)(screenSize.height - (screenSize.height * percent));
		
		int x = 0;
        int y = (buffer / 2);

        _self.setLocation(x, y);
        _self.repaint();
	}

	public static void pinRight() {
		Settings.put("pinLeft","false");
		Settings.put("pinRight","true");
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float percentage = Float.valueOf(Settings.get("verticalPercentage"));
        float percent = (float)(percentage / 100.0f);
		int buffer = (int)(screenSize.height - (screenSize.height * percent));
		
    	int x = (screenSize.width - _width);
        int y = (buffer / 2);
        
        _self.setLocation(x, y);
        _self.repaint();
	}

	/**
     * helper method to modularize initialization code out a bit
     */
    private void initializeSelf() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float percentage = Float.valueOf(Settings.get("verticalPercentage"));
        float percent = (float)(percentage / 100.0f);
		int buffer = (int)(screenSize.height - (screenSize.height * percent));

        _frame.setUndecorated(true);
        _frame.setTitle("HideANote");
        _frame.setAlwaysOnTop(true);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(255, 255, 153));
        this.setFocusable(true);
        this.setEnabled(true);
        this.setFocusableWindowState(true);
        this.setSize(_width, screenSize.height - buffer);
        
        int x = 0;
        if (Settings.get("pinRight").equals("true")) {
        	x = (screenSize.width - _width);
        }
        
        int y = (buffer / 2);
        this.setLocation(x, y);
        startOnTopper();
        _frame.setVisible(true);
		_self = this;
    }
    
    /**
     * a method to ensure the note is always on top
     * the problem is that if anything (external even) at any point sends the window
     * to the back, it will no longer be on top, this should
     * ensure it always is.
     */
    private void startOnTopper() {
        Thread t = new Thread() {
            public void run() {
                while( true ) {
                    _frame.setAlwaysOnTop(true);
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException ignore) {
                        // no need to respond to it, just move on to next iteration
                    }
                }
            }
        };
        
        t.start();
    }
}
