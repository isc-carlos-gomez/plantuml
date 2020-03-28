package net.sourceforge.plantuml.svek.communication;

import net.sourceforge.plantuml.svek.Line;
import net.sourceforge.plantuml.svek.communication.Rectangle.Overlap;

/**
 * A {@link LineGroup} with three lines.
 *
 * @author Carlos Gomez
 */
class ThreeLineGroup implements LineGroup {

  private final ThreeLineGroupLazyData data;
  private Orientation orientation;
  private Point focalPoint;

  ThreeLineGroup(final Line line1, final Line line2, final Line line3) {
    this.data = new ThreeLineGroupLazyData(line1, line2, line3);
  }

  @Override
  public LineGroup addLine(final Line line) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isLineVisible(final Line line) {
    return line == this.data.centralLine();
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
      this.focalPoint = new FocalPointCalculator(this.data.centralLine(), orientation())
          .calculate();
    }
    return this.focalPoint;
  }

  private Orientation calculateOrientation() {
    final Rectangle centralLineBox = new Rectangle(this.data.centralLine().getDotPath().getMinMax());
    final Rectangle messageBoxes = this.data.firstLine().messageBox()
        .join(this.data.lastLine().messageBox());
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
