package net.sourceforge.plantuml.communicationdiagram.link;

import net.sourceforge.plantuml.svek.Line;

/**
 * An empty {@link LineGroup}.
 *
 * @author Carlos Gomez
 */
class EmptyLineGroup implements LineGroup {

  @Override
  public LineGroup addLine(final Line line) {
    return new OneLineGroup(line);
  }

}
