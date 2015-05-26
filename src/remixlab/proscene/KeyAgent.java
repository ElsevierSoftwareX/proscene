/**************************************************************************************
 * ProScene (version 3.0.0)
 * Copyright (c) 2010-2014 National University of Colombia, https://github.com/remixlab
 * @author Jean Pierre Charalambos, http://otrolado.info/
 * 
 * All rights reserved. Library that eases the creation of interactive scenes
 * in Processing, released under the terms of the GNU Public License v3.0
 * which is available at http://www.gnu.org/licenses/gpl.html
 **************************************************************************************/

package remixlab.proscene;

import remixlab.bias.event.KeyboardEvent;
import remixlab.dandelion.agent.*;
import remixlab.dandelion.core.*;

public class KeyAgent extends KeyboardAgent {
	protected boolean				press, release, type;
	protected KeyboardEvent	currentEvent;

	public KeyAgent(AbstractScene scn, String n) {
		super(scn, n);
		// registration requires a call to PApplet.registerMethod("keyEvent", keyboardAgent());
		// which is done in Scene.enableKeyboardAgent(), which also register the agent at the inputHandler
		inputHandler().unregisterAgent(this);
	}

	/**
	 * Processing keyEvent method to be registered at the PApplet's instance.
	 */
	public void keyEvent(processing.event.KeyEvent e) {
		press = e.getAction() == processing.event.KeyEvent.PRESS;
		release = e.getAction() == processing.event.KeyEvent.RELEASE;
		type = e.getAction() == processing.event.KeyEvent.TYPE;
		currentEvent = new KeyboardEvent(/*e.getKey(),*/ e.getModifiers(), e.getKeyCode());
		if (press)
			handle(currentEvent);
		if (release) {
			flush(currentEvent);
			updateTrackedGrabber(currentEvent);// needs testing
		}
		
		/*
		// debug
		if (press) {
			if (e.getKey() == '\uFFFF')
				System.out.println("got the unicode replacement char in PRESS: " + e.getKey());
			System.out.println("PRESS: key: " + e.getKey() + " modifiers " + BogusEvent.modifiersText(e.getModifiers())
					+ " keyCode: " + e.getKeyCode()); // updateTrackedGrabber(new KeyboardEvent(e.getModifiers(),
																						// e.getKeyCode()));//
		}
		else if (release) {
			if (e.getKey() == '\uFFFF')
				System.out.println("got the unicode replacement char in RELEASE: " + e.getKey());
			System.out.println("RELEASE: key: " + e.getKey() + " modifiers " + BogusEvent.modifiersText(e.getModifiers())
					+ " keyCode: " + e.getKeyCode());
		}
		else if(type) {
			if (e.getKey() == '\uFFFF')
				System.out.println("got the unicode replacement char in TYPE: " + e.getKey());
			System.out.println("TYPE: key: " + e.getKey() + " modifiers " + BogusEvent.modifiersText(e.getModifiers())
					+ " keyCode: " + e.getKeyCode());
		}
		// */
	}

	/*
	public static int getKeyCode(char key) {
		return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(key);
	}
	*/
	
	@Override
	public int keyCode(char key) {
		return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(key);
	}
}