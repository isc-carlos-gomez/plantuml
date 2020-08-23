package net.sourceforge.plantuml.communicationdiagram.link;

import net.sourceforge.plantuml.communicationdiagram.link.Rectangle.Overlap;
import net.sourceforge.plantuml.svek.Line;

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
    final Overlap overlap = this.data.firstLine().messageBox()
        .overlap(this.data.lastLine().messageBox());
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
