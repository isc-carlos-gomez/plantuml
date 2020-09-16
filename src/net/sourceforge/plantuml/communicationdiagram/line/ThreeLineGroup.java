package net.sourceforge.plantuml.communicationdiagram.line;

import net.sourceforge.plantuml.communicationdiagram.line.Rectangle.Overlap;

/**
 * A {@link LineGroup} with three lines.
 *
 * @author Carlos Gomez
 */
class ThreeLineGroup implements LineGroup {

  private final ThreeLineGroupLazyData data;
  private Orientation orientation;

  ThreeLineGroup(final CommunicationLine line1, final CommunicationLine line2, final CommunicationLine line3) {
    this.data = new ThreeLineGroupLazyData(line1, line2, line3);
  }

  @Override
  public LineGroup addLine(final CommunicationLine line) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isLineVisible(final CommunicationLine line) {
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
    return new FocalPointCalculator(this.data.centralLine(), orientation())
        .calculate();
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
