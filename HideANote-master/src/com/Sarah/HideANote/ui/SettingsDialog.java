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
import javax.swing.event.*;
import java.util.*;
import com.Sarah.HideANote.core.*;

public class SettingsDialog extends JDialog {

    private static final long serialVersionUID = -8773180054993490123L;
    private static final int _width = 250;
    private static final int _height = 250;
    private static SettingsDialog _instance = null;
    
    /**
     * @return the one and only instance of this dialog (instantiates if necessary)
     */
    public synchronized static SettingsDialog getInstance() {
        if (_instance == null){
            _instance = new SettingsDialog(new JFrame());
        }
        
        return(_instance);
    }

    /**
     * private constructor to create the dialog
     * @param parent - the component to which the dialog is associated
     */
    private SettingsDialog(JFrame parent) {
        
        super(parent, "Settings", true);

        Box b = Box.createVerticalBox();
        b.add(Box.createVerticalGlue());

		JRadioButton pinLeft = new JRadioButton("Pin to left side of screen");
		JRadioButton pinRight = new JRadioButton("Pin to right side of screen");
		
		if(Settings.get("pinLeft").equals("true")) {
			pinLeft.setSelected(true);
		} else if (Settings.get("pinRight").equals("true")) {
			pinRight.setSelected(true);
		} else {
			pinRight.setSelected(true);
		}
		
		ButtonGroup pinButtonGroup = new ButtonGroup();
		pinButtonGroup.add(pinLeft);
		pinButtonGroup.add(pinRight);
		
		pinLeft.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		BorderlessWindow.pinLeft();
          	}
        });

		pinRight.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		BorderlessWindow.pinRight();
          	}
        });

		b.add(pinLeft);
		b.add(pinRight);
        b.add(Box.createVerticalGlue());
        b.add(Box.createVerticalGlue());

		//vertical percentage of screen to use 0-100%
        int defaultVertPerc = Integer.valueOf(Settings.get("verticalPercentage"));
		final JSlider verticalPercentage = new JSlider(JSlider.HORIZONTAL,20,100,defaultVertPerc);
		verticalPercentage.setMajorTickSpacing(20);
		verticalPercentage.setPaintTicks(true);
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer,JLabel>();
		labels.put(new Integer(20), new JLabel("20%"));
		labels.put(new Integer(40), new JLabel("40%"));
		labels.put(new Integer(60), new JLabel("60%"));
		labels.put(new Integer(80), new JLabel("80%"));
		labels.put(new Integer(100), new JLabel("100%"));
		verticalPercentage.setLabelTable(labels);
		verticalPercentage.setPaintLabels(true);
		
		verticalPercentage.addChangeListener(new ChangeListener() {
			public final void stateChanged(ChangeEvent e) {
				Settings.put("verticalPercentage",String.valueOf((int)verticalPercentage.getValue()));
				BorderlessWindow.updateHeight((int)verticalPercentage.getValue());
			}
		});

		b.add(new JLabel("Vertical Screen Percentage Used"));
        b.add(Box.createGlue());
		b.add(verticalPercentage);
        b.add(Box.createGlue());
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
