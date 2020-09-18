package net.sourceforge.plantuml.communicationdiagram.line;

/**
 * An empty {@link LineGroup}.
 *
 * @author Carlos Gomez
 */
class EmptyLineGroup implements LineGroup {

  @Override
  public LineGroup addLine(final CommunicationLine line) {
    return new OneLineGroup(line);
  }

  @Override
  public boolean isLineVisible(final CommunicationLine line) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Orientation orientation() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Point focalPoint() {
    throw new UnsupportedOperationException();
  }

}
