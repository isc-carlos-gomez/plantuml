package net.sourceforge.plantuml.communicationdiagram.line;

/**
 * Value object representing a group of lines that are analyzed together to determine some of their
 * features and behaviors so that when rendered together they draw a single Communication Diagram
 * Link with its messages flowing around.
 *
 * @author Carlos Gomez
 */
interface LineGroup {

  /**
   * Adds a line to this group.
   *
   * @param line
   *        the line to add
   * @return a new group that contains this group's lines and the added line
   */
  LineGroup addLine(final CommunicationLine line);

  /**
   * @param line
   *        the line to analyze
   * @return whether a line is visible in this group or not
   */
  boolean isLineVisible(final CommunicationLine line);

  /**
   * @return the {@link Orientation} of this line group considering the position of its lines and
   *         labels in the Communication Diagram
   */
  Orientation orientation();

  /**
   * @return the focal point where the messages of this group of lines converge
   */
  Point focalPoint();

}
