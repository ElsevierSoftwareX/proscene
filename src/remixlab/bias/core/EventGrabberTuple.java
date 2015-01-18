/*********************************************************************************
 * bias_tree
 * Copyright (c) 2014 National University of Colombia, https://github.com/remixlab
 * @author Jean Pierre Charalambos, http://otrolado.info/
 *
 * All rights reserved. Library that eases the creation of interactive
 * scenes, released under the terms of the GNU Public License v3.0
 * which is available at http://www.gnu.org/licenses/gpl.html
 *********************************************************************************/

package remixlab.bias.core;

/**
 * [{@link remixlab.bias.core.BogusEvent},{@link remixlab.bias.core.Grabber}] tuples which encapsulate message passing
 * from {@link remixlab.bias.core.BogusEvent} to {@link remixlab.bias.core.Grabber} to perform actions.
 */
public class EventGrabberTuple {
	protected BogusEvent	event;
	protected Grabber			grabber;
	protected Action<?>		action;

	/**
	 * Constructs <{@link remixlab.bias.core.BogusEvent},{@link remixlab.bias.core.Grabber}> tuple.
	 * 
	 * @param e
	 *          {@link remixlab.bias.core.BogusEvent}
	 * @param g
	 *          {@link remixlab.bias.core.Grabber}
	 */
	public EventGrabberTuple(BogusEvent e, Grabber g) {
		event = e;
		grabber = g;
	}

	// /*
	public <E extends Enum<E>> EventGrabberTuple(BogusEvent e, ActionGrabber<E> g, Action<E> a) {
		event = e;
		grabber = g;
		action = a;
	}

	// */

	/*
	 * public EventGrabberTuple(BogusEvent e, ActionGrabber<?> g, Action<?> a) { event = e; grabber = g; action = a; } //
	 */

	/**
	 * Calls {@link remixlab.bias.core.Grabber#performInteraction(BogusEvent)}.
	 * 
	 * @return true if succeeded and false otherwise.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean perform() {
		if (grabber != null) {
			if (grabber instanceof ActionGrabber) {
				((ActionGrabber) grabber).setAction(action);
			}
			grabber.performInteraction(event);
			return true;
		}
		return false;
	}

	/**
	 * Returns the event from the tuple.
	 */
	public BogusEvent event() {
		return event;
	}

	/**
	 * Returns the object Grabber in the tuple.
	 */
	public Grabber grabber() {
		return grabber;
	}
}
