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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * The HelpDialog class provides what the name suggests, a dialog box
 * that displays help information.
 * 
 * To avoid multiple dialogs, it implements the singleton design pattern
 */
public class HelpDialog extends JDialog {

    private static final long serialVersionUID = -8773180054993490155L;
    private static final int _width = 400;
    private static final int _height = 400;
    private static HelpDialog _instance = null;
    private static final String _help = "HideANote Help\n\n>>> To disable hiding, hold down control and double click anywhere in the note\n\n>>> To resize the note, first disable hiding, then hold down control and drag an edge with your mouse (your mouse cursor will change when you're at an edge where you'll be able to resize)";
    
    /**
     * @return the one and only instance of this dialog (instantiate if necessary)
     */
    public synchronized static HelpDialog getInstance() {
        if (_instance == null){
            _instance = new HelpDialog(new JFrame());
        }
        
        return(_instance);
    }
    
    /**
     * private constructor to create and initialize the dialog the first time
     * @param parent
     */
    private HelpDialog(JFrame parent) {
        super(parent, "Help", true);
        
        Box b = Box.createVerticalBox();
        b.add(Box.createGlue());
        JTextPane content = new JTextPane();
        content.setText(_help);
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
