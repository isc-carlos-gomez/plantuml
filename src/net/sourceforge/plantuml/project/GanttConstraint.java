/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.project;

import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import net.sourceforge.plantuml.cucadiagram.LinkType;
import net.sourceforge.plantuml.cucadiagram.WithLinkType;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.project.core.TaskInstant;
import net.sourceforge.plantuml.project.time.Wink;
import net.sourceforge.plantuml.project.timescale.TimeScale;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class GanttConstraint extends WithLinkType {

	private final TaskInstant source;
	private final TaskInstant dest;

	public GanttConstraint(TaskInstant source, TaskInstant dest, HColor forcedColor) {
		this.source = source;
		this.dest = dest;
		this.type = new LinkType(LinkDecor.NONE, LinkDecor.NONE);
		this.setSpecificColor(forcedColor);
	}

	public GanttConstraint(TaskInstant source, TaskInstant dest) {
		this(source, dest, null);
	}

	@Override
	public String toString() {
		return source.toString() + " --> " + dest.toString();
	}

	public UDrawable getUDrawable(TimeScale timeScale, HColor color, ToTaskDraw toTaskDraw) {
		if (getSpecificColor() == null) {
			return new GanttArrow(timeScale, source, dest, color, getType(), toTaskDraw);
		}
		return new GanttArrow(timeScale, source, dest, getSpecificColor(), getType(), toTaskDraw);
	}

	public boolean isHidden(Wink min, Wink max) {
		if (isHidden(source.getInstantPrecise(), min, max)) {
			return true;
		}
		if (isHidden(dest.getInstantPrecise(), min, max)) {
			return true;
		}
		return false;
	}

	private boolean isHidden(Wink now, Wink min, Wink max) {
		if (now.compareTo(min) < 0) {
			return true;
		}
		if (now.compareTo(max) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void goNorank() {
	}
}
