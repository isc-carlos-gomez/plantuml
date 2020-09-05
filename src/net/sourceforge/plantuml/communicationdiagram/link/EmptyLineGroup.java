package net.sourceforge.plantuml.communicationdiagram.link;

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

}
