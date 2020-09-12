package net.sourceforge.plantuml.communicationdiagram.line;

import java.util.Objects;

/**
 * A {@link LineGroup} with only one line.
 *
 * @author Carlos Gomez
 */
public class OneLineGroup implements LineGroup {

  private final CommunicationLine line;

  OneLineGroup(final CommunicationLine line) {
    this.line = Objects.requireNonNull(line);
  }

  @Override
  public LineGroup addLine(final CommunicationLine line2) {
    return new TwoLineGroup(this.line, line2);
  }

  @Override
  public boolean isLineVisible(final CommunicationLine line) {
    return true;
  }

}
