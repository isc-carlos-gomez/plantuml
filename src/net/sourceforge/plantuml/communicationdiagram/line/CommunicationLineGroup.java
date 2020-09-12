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
   * @param line
   *        the line the message arrow is created for
   * @return a new {@link MessageArrow} whose position corresponds with the position of the line
   *         message
   */
  MessageArrow buildMessageArrow(final CommunicationLine line) {
    return new MessageArrowBuilder(line, this.group.orientation(), this.group.focalPoint())
        .build();
  }

  /**
   * @param line
   *        the line containing the message to position
   * @return the point corresponding to the top left corner of the line message
   */
  Point calculateMessagePosition(final CommunicationLine line) {
    return new MessagePositionCalculator(line, this.group.orientation(), this.group.focalPoint())
        .calculate();
  }
  
  /**
   * @return the group
   */
  public LineGroup operations() {
    return group;
  }

}
