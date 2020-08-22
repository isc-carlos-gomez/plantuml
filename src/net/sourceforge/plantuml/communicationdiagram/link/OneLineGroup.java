package net.sourceforge.plantuml.communicationdiagram.link;

import java.util.Objects;

import net.sourceforge.plantuml.communicationdiagram.link.Rectangle.Overlap;
import net.sourceforge.plantuml.svek.Line;

/**
 * A {@link LineGroup} with only one line.
 *
 * @author Carlos Gomez
 */
public class OneLineGroup implements LineGroup {

  private final Line line;
  private Orientation orientation;
  private Point focalPoint;

  OneLineGroup(final Line line) {
    this.line = Objects.requireNonNull(line);
  }

  @Override
  public LineGroup addLine(final Line line2) {
    return new TwoLineGroup(this.line, line2);
  }

  @Override
  public boolean isLineVisible(final Line line) {
    return true;
  }

  @Override
  public Orientation orientation() {
    if (this.orientation == null) {
      this.orientation = calculateOrientation();
    }
    return this.orientation;
  }

  @Override
  public Point focalPoint() {
    if (this.focalPoint == null) {
      this.focalPoint = new FocalPointCalculator(this.line, orientation())
          .calculate();
    }
    return this.focalPoint;
  }

  private Orientation calculateOrientation() {
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

}
