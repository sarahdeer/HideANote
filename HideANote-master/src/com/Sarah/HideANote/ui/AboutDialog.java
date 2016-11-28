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
import javax.swing.*;
import javax.swing.text.*;

/**
 * The AboutDialog class provides what the name suggests, a dialog box
 * that displays information about the application.
 * 
 * To avoid multiple dialogs, it implements the singleton design pattern
 */
public class AboutDialog extends JDialog {

    private static final long serialVersionUID = -8773180054993490155L;
    private static final int _width = 250;
    private static final int _height = 150;
    private static AboutDialog _instance = null;
    private static final String _about = "HideANote\nhttp://HideANote.Sarah.com\nCopyright 2016 Sarah J. Deer\nemail: sarahdeer121@gmail.com";
    
    /**
     * @return the one and only instance of this dialog (instantiates if necessary)
     */
    public synchronized static AboutDialog getInstance() {
        if (_instance == null){
            _instance = new AboutDialog(new JFrame());
        }
        
        return(_instance);
    }

    /**
     * private constructor to create the dialog
     * @param parent - the component to which the dialog is associated
     */
    private AboutDialog(JFrame parent) {
        
        super(parent, "About", true);

        Box b = Box.createVerticalBox();
        b.add(Box.createGlue());
        JTextPane content = new JTextPane();
        content.setText(_about);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        content.getStyledDocument().setParagraphAttributes(0,content.getText().length(), attributes, true);
        b.add(content);
        b.add(Box.createGlue());
        getContentPane().add(b, "Center");

        JPanel p2 = new JPanel();
        JButton ok = new JButton("Close");
        p2.add(ok);
        getContentPane().add(p2, "South");

        ok.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            setVisible(false);
          }
        });

        setSize(_width, _height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = ((screenSize.width/2) - (_width/2));
        int y = ((screenSize.height/2) - (_height/2));
        this.setLocation(x, y);
    }    
}
