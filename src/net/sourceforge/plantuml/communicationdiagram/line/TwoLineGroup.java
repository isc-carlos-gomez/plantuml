package net.sourceforge.plantuml.communicationdiagram.line;

import java.util.Objects;

/**
 * A {@link LineGroup} of two lines, useful only for transitions between 1 and 3 line groups.
 *
 * @author Carlos Gomez
 */
class TwoLineGroup implements LineGroup {

  private final CommunicationLine line1;
  private final CommunicationLine line2;

  TwoLineGroup(final CommunicationLine line1, final CommunicationLine line2) {
    this.line1 = Objects.requireNonNull(line1);
    this.line2 = Objects.requireNonNull(line2);
  }

  @Override
  public LineGroup addLine(final CommunicationLine line3) {
    return new ThreeLineGroup(this.line1, this.line2, line3);
  }

}
