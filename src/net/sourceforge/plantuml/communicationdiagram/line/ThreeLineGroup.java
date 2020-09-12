package net.sourceforge.plantuml.communicationdiagram.line;

/**
 * A {@link LineGroup} with three lines.
 *
 * @author Carlos Gomez
 */
class ThreeLineGroup implements LineGroup {

  private final ThreeLineGroupLazyData data;

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

}
