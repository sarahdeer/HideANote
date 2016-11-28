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

package com.Sarah.HideANote.event;

import com.Sarah.HideANote.core.*;
import java.awt.*;
import java.awt.event.*;

public class NoteMouseListener implements MouseListener, MouseMotionListener {
    private Note _note;
    private Component _parent;
    private Component _target;
    private static final int _initial_width = 300;
    private static final int _resize_buffer = 10;
    private static final int _not_resizing = 0;
    private static final int _top_resizing = 1;
    private static final int _bottom_resizing = 2;
    private static final int _left_resizing = 3;
    private static final int _right_resizing = 4;
    private static int _screen_x;
    private static int _screen_y;
    private static boolean _resizing = false;

    /**
     * constructor that initializes based on a given child and parent component
     * @param parent - the enclosing component (window)
     * @param target - the inner component (note)
     */
    public NoteMouseListener(Component parent, Component target, Note note) {
        this._note = note;
        this._parent = parent;
        this._target = target;
        _screen_x = Toolkit.getDefaultToolkit().getScreenSize().width - _initial_width;
    }

    public void mouseClicked(MouseEvent e) {
        
        //if they're holding down ctrl, ignore the request
        if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != InputEvent.CTRL_DOWN_MASK) {
            return;
        }

        //if they've double clicked
        if ( e.getClickCount() == 2 ) {
            Note.toggleHiding();
        }
    }

    public void mouseEntered(MouseEvent e) {
        
        if ( !Note.isHidingEnabled() ) {
            return;
        }
        
        if (Settings.get("pinRight").equals("true")) {
        	_parent.setLocation(_screen_x, _parent.getLocationOnScreen().y);
        } else {
        	_parent.setLocation(0, _parent.getLocationOnScreen().y);
        }
    }

    public void mouseExited(MouseEvent e) {

        if ( !Note.isHidingEnabled() ) {
            return;
        }

        //if they're holding down ctrl, ignore the request
        if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK) {
            return;
        }

        int psx = _parent.getLocationOnScreen().x;
        int psy = _parent.getLocationOnScreen().y;
        int psw = _parent.getSize().width;
        int psh = _parent.getSize().height;
        int msx = e.getLocationOnScreen().x;
        int msy = e.getLocationOnScreen().y;

        int x,y;
        if(Settings.get("pinRight").equals("true")) {
            if (msx > psx && msx < (psx + psw) && msy > psy && msy < (psy + psh - 3)) {
                //System.out.println("pinRight -- psx:"+psx+", psy:"+psy+", psw:"+psw+", psh:"+psh+", msx:"+msx+", msy:"+msy);
                return;
            }

        	x = Toolkit.getDefaultToolkit().getScreenSize().width - 3;
            y = _parent.getLocationOnScreen().y;
            
        } else {
            if (msx+2 < (psx+psw) && msy > psy && msy < (psy + psh - 3)) {
            	//System.out.println("pinLeft -- psx:"+psx+", psy:"+psy+", psw:"+psw+", psh:"+psh+", msx:"+msx+", msy:"+msy);
                return;
            }

        	x = (-_parent.getSize().width)+5;
            y = _parent.getLocationOnScreen().y;
            
        }
        
    	_parent.setLocation(x, y);
        
        // keep in mind that the note also saves to disk on a timer
        // but this will ensure edits are always synced too...
        _note.saveContent();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

        if ( !shouldResize(e) ) { return; }

        int resizeRegion = inResizeRegion(e);
        
        // top
        if ( resizeRegion == _top_resizing ) {
            resizeTop(e);
        }
        // left
        else if ( resizeRegion == _left_resizing ) {
            resizeLeft(e);
        }
        // right
        else if ( resizeRegion == _right_resizing ) {
            resizeRight(e);
        }
        // bottom
        else if ( resizeRegion == _bottom_resizing ) {
            resizeBottom(e);
        } 
    }
    
    private void resizeTop(MouseEvent e) {
    }
    
    private void resizeBottom(MouseEvent e) {
    }
    
    /**
     * method that resizes the note based on dragging the left 
     * edge
     * @param e - mouse event to use for determining how resize should occur
     */
    private void resizeLeft(MouseEvent e) {
        _resizing = true;
        Point screenLocation = e.getLocationOnScreen();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        _screen_x = screenLocation.x;
        int width = screenSize.width - screenLocation.x;
        _screen_y = _parent.getLocationOnScreen().y;
        Point newLocation = new Point(_screen_x, _screen_y);
        _parent.setLocation(newLocation);
        _parent.setSize(width, _parent.getHeight());
        _resizing = false;
    }
    
    private void resizeRight(MouseEvent e) {
    	//TBD
    }
    
    /**
     * helper method that checks a few parameters to determine if a resize
     * should be allowed or not
     * @param e - mouse event to use for determining these things
     * @return true if resize should be allowed, false otherwise
     */
    private boolean shouldResize(MouseEvent e) {
        if ( !_resizing && Note.isHidingEnabled() ) {
            return false;
        }
        
        // only respond to drag events if CTRL is pressed too
        if ( !_resizing && (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != InputEvent.CTRL_DOWN_MASK) {
            return false;
        }
        
        return true;
    }
    
    /**
     * helper method to determine if the mouse is in the resize region as defined by this application
     * @param e - mouse event to use for determining location
     * @return an integer value 
     */
    private int inResizeRegion( MouseEvent e ) {
        Point mouseLocation = e.getPoint();

        // top
        if ( mouseLocation.y >= 0 && mouseLocation.y <= _resize_buffer ) {
            //_target.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR)); //removed until this is supported, don't want to confuse
            return(_top_resizing);
        }
        // left
        else if ( mouseLocation.x <= _resize_buffer ) {
            _target.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
            return(_left_resizing);
        }
        // right
        else if( mouseLocation.x >= (_target.getWidth() - _resize_buffer) && mouseLocation.x <= _target.getWidth() ) {
            // _target.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR)); //removed until this is supported, don't want to confuse
            return(_right_resizing);
        }
        // bottom
        else if ( mouseLocation.y > (_target.getHeight() - _resize_buffer) && mouseLocation.y < _target.getHeight()) {
            //_target.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR)); //removed until this is supported, don't want to confuse
            return(_bottom_resizing);
        }
        // nothing matched, not in a resize region
        else {
            _target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            return(_not_resizing);
        }
    }

    public void mouseMoved(MouseEvent e) {
        
        if ( !shouldResize(e) ) { 
            _target.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //just to be sure it gets restored correctly...
            return; 
        }

        inResizeRegion(e);
    }

}
