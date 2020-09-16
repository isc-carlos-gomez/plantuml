package net.sourceforge.plantuml.communicationdiagram.line;

/**
 * Service to calculate the position of a line message. Message positions needs to be calculated so
 * that they can be aligned with message arrows.
 *
 * @author Carlos Gomez
 */
class MessagePositionCalculator {

  private static final int GAP_VERTICAL_LINK = 17;
  private static final int GAP_HORIZONTAL_LINK = 15;

  private final Rectangle lineMessageBox;
  private final Orientation linkOrientation;
  private final Point linkFocalPoint;

  /**
   * Creates a new calculator.
   *
   * @param lineMessageBox
   *        the rectangular area occupied by the original line message (before being positioned)
   * @param linkOrientation
   *        orientation of the communication link calculating the message position
   * @param linkFocalPoint
   *        the focal point where the communication link and its message(s) converge
   */
  MessagePositionCalculator(final Rectangle lineMessageBox, final Orientation linkOrientation,
      final Point linkFocalPoint) {
    this.lineMessageBox = lineMessageBox;
    this.linkOrientation = linkOrientation;
    this.linkFocalPoint = linkFocalPoint;
  }

  /**
   * @return the point corresponding to the message's top left corner
   */
  Point calculate() {
    if (this.linkOrientation == Orientation.HORIZONTAL) {
      return calculateForHorizontalLink();
    }
    return calculateForVerticalLink();
  }

  private Point calculateForHorizontalLink() {
    final double y;
    if (this.lineMessageBox.topLeft().getY() < this.linkFocalPoint.getY()) {
      y = this.linkFocalPoint.getY() - this.lineMessageBox.height() - GAP_HORIZONTAL_LINK;
    } else {
      y = this.linkFocalPoint.getY() + GAP_HORIZONTAL_LINK;
    }
    return new Point(this.lineMessageBox.topLeft().getX(), y);
  }

  private Point calculateForVerticalLink() {
    final double x;
    if (this.lineMessageBox.topLeft().getX() < this.linkFocalPoint.getX()) {
      x = this.linkFocalPoint.getX() - this.lineMessageBox.width() - GAP_VERTICAL_LINK;
    } else {
      x = this.linkFocalPoint.getX() + GAP_VERTICAL_LINK;
    }
    return new Point(x, this.lineMessageBox.topLeft().getY());
  }

}
