package net.sourceforge.plantuml.communicationdiagram.line;

import java.util.Objects;

import net.sourceforge.plantuml.communicationdiagram.line.Rectangle.Overlap;

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

  @Override
  public Orientation orientation() {
    final Rectangle centralLineBox = new Rectangle(this.line.getDotPath().getMinMax());
    final Rectangle messageBoxes = this.line.messageBox();
    final Overlap overlap = centralLineBox.overlap(messageBoxes);
    switch (overlap) {
      case HORIZONTAL:
        return Orientation.HORIZONTAL;
      case VERTICAL:
        return Orientation.VERTICAL;
      default:
        throw new IllegalStateException("Unexpected overlap: " + overlap);
    }
  }

  @Override
  public Point focalPoint() {
    return new FocalPointCalculator(this.line, orientation())
        .calculate();
  }

}
