package net.sourceforge.plantuml.communicationdiagram.line;

/**
 * Facade that provides access to the information required to render a group of lines as a single
 * link in a Communication Diagram.
 * <p>
 * First the group must be created and get added all the lines that should be rendered together.
 * Then every line in the group can use this group to enquire for features and behavior required
 * during the drawing process.
 *
 * @author Carlos Gomez
 */
public class CommunicationLineGroup {

  private LineGroup group;

  public CommunicationLineGroup() {
    this.group = new EmptyLineGroup();
  }

  /**
   * Adds a new line to this group.
   *
   * @param line
   *        the line to add
   */
  public void addLine(final CommunicationLine line) {
    this.group = this.group.addLine(line);
  }

  /**
   * @param line
   *        the line to analyze
   * @return whether a line is visible in this group or not
   */
  boolean isLineVisible(final CommunicationLine line) {
    return this.group.isLineVisible(line);
  }

  /**
   * @return the {@link Orientation} of this line group considering the position of its lines and
   *         labels in the Communication Diagram
   */
  Orientation orientation() {
    return this.group.orientation();
  }

  /**
   * @return the focal point where the messages of this group of lines converge
   */
  Point focalPoint() {
    return this.group.focalPoint();
  }

}
