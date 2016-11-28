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

import com.Sarah.HideANote.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.text.*;

/**
 * The Note class deals with persistence of the note's content, initializing its
 * components and generally modularizes hiding operations
 */
public class Note {

    // what is the note housed in
    private Component _parent;
    
    // actual content area of the application
    private JEditorPane _note;
    
    // so logical content can be larger than physical window
    private JScrollPane _noteScrollPane;
    
    // how events will be monitored and responded to
    private NoteMouseListener _mouseListener;
    
    // should I hide or should I go now
    private static boolean _hiding_enabled = true;

    private static final long serialVersionUID = -3113393354034915899L;

    /**
     * default constructor, that does not initialize anything
     * assumes the caller knows what to do
     */
    public Note() {
        startBackgroundContentSaver();
    }
    
    /**
     * Constructor that initializes based on a given parent component
     * @param parent - the parent component in which this component lives
     */
    public Note(Component parent) {
        super();
        _parent = parent;
        initializeSelf();
    }
    
    /**
     * set the parent to the given component
     * @param parent - the parent component in which this component lives
     */
    public void setParent(Component parent) {
        _parent = parent;
        initializeSelf();
    }
    
    /**
     * modularizes the setup bits
     */
    private void initializeSelf() {
        _note = new JEditorPane();
        _mouseListener = new NoteMouseListener(_parent, _note, this);
        _note.addMouseListener(_mouseListener);
        _note.addMouseMotionListener(_mouseListener);
        _note.setEditable(true);
        _note.setEnabled(true);
        _note.setBackground(new Color(255,255,153));
        _noteScrollPane = new JScrollPane(_note);
        _noteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        _noteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        _noteScrollPane.setPreferredSize(new Dimension(250, 250));
        ((JWindow)_parent).getContentPane().add(_noteScrollPane, BorderLayout.CENTER);

		restoreContent();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
				saveContent();
            }
        });
    }
    
    public static void toggleHiding() {
        _hiding_enabled = !_hiding_enabled;
    }
    
    public static boolean isHidingEnabled() {
        return(_hiding_enabled);
    }

    /**
     * fires up a thread in the background to save the note's
     * content to disk every so often just to be sure it stays
     * current
     */
    private void startBackgroundContentSaver() {
        Thread t = new Thread() {
            public void run(){
                while(true) {
                    try {
                        long sleepDuration = (1000) * (60); //sleep for a minute, sleep call takes millis
                        Thread.sleep(sleepDuration);
                        saveContent();
                    } catch (InterruptedException ignore) {
                        // just move on to the next iteration,
                        // don't care that we were interrupted
                    }
                }
            }
        };
        
        t.start();
    }
    
    /**
     * restores the previous content of the note from the last time it was
     * exited. this method is private intentionally, to prevent other external
     * foo from nuking existing content. only this class has that ability.
     */
	private void restoreContent() {
		
		try {
        	File saveFile = new File(Settings.get("savedContent"));
			BufferedReader input = new BufferedReader(new FileReader(saveFile));
			StringBuffer restore = new StringBuffer();
		
			while( true ) {
				String line = input.readLine();
				if( line == null ) { break; }
				restore.append(line);
				restore.append("\n");
			}
		
			_note.setText(restore.toString());
			input.close();
		} catch (FileNotFoundException fnfx) {
			//TODO: add a popup to taskbar for unable to open file
		} catch (IOException iox) {
		}
	}

	/**
	 * stores the current content of the note so it can be restored
	 * on next start. anybody should be able to initiate a save to disk.
	 */
	public void saveContent() {
	    
		try {
        	File saveFile = new File(Settings.get("savedContent"));
			BufferedWriter output = new BufferedWriter(new FileWriter(saveFile));
			output.write(_note.getText());
			output.flush();
			output.close();
		} catch(FileNotFoundException fnfx) {
			//TODO: add a popup to taskbar for unable save content
		} catch(IOException iox) {
		}
	}
}
