package net.sourceforge.plantuml.svek.communication;

import net.sourceforge.plantuml.svek.Line;

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
  default LineGroup addLine(final Line line) {
    throw new UnsupportedOperationException();
  }

  /**
   * @param line
   *        the line to analyze
   * @return whether a line is visible in this group or not
   */
  default boolean isLineVisible(final Line line) {
    throw new UnsupportedOperationException();
  }

  /**
   * @param line
   *        the line carrying the label to analyze
   * @return whether the label of a line is visible in this group or not
   */
  default boolean isLabelVisible(final Line line) {
    throw new UnsupportedOperationException();
  }

  /**
   * @return the {@link Orientation} of this line group considering the position of its lines and
   *         labels in the Communication Diagram
   */
  default Orientation orientation() {
    throw new UnsupportedOperationException();
  }

  /**
   * @return the focal point where the messages of this group of lines converge
   */
  default Point focalPoint() {
    throw new UnsupportedOperationException();
  }

}
