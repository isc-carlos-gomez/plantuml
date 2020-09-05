package net.sourceforge.plantuml.communicationdiagram.link;

import java.awt.geom.Point2D;

import net.sourceforge.plantuml.Direction;

/**
 * Service to build a {@link MessageArrow} that corresponds with the position of a line message.
 *
 * @author Carlos Gomez
 */
class MessageArrowBuilder {

  private static final double ARROW_LENGTH = 20;
  private static final int LINK_CENTER_GAP = 10;

  private final CommunicationLine line;
  private final Orientation linkOrientation;
  private final Point linkFocalPoint;

  /**
   * Creates a new builder.
   *
   * @param line
   *        the line the {@link MessageArrow} will be created for
   * @param linkOrientation
   *        orientation of the communication link building the {@link MessageArrow}
   * @param linkFocalPoint
   *        the focal point where the communication link and its message(s) converge
   */
  MessageArrowBuilder(final CommunicationLine line, final Orientation linkOrientation, final Point linkFocalPoint) {
    this.line = line;
    this.linkOrientation = linkOrientation;
    this.linkFocalPoint = linkFocalPoint;
  }

  /**
   * @return a new {@link MessageArrow}
   */
  MessageArrow build() {
    final Direction direction = calculateDirection();
    final Point startPoint = calculateStartPoint();
    final Point endPoint = calculateEndPoint(startPoint);
    return new MessageArrow(startPoint, endPoint, direction);
  }

  private Direction calculateDirection() {
    final Direction direction = naturalDirection(this.line);
    if (this.line.isInverted()) {
      return direction.getInv();
    }
    return direction;
  }

  private Direction naturalDirection(final CommunicationLine line) {
    final Point2D start = line.getDotPath().getStartPoint();
    final Point2D end = line.getDotPath().getEndPoint();
    double angle = Math.toDegrees(Math.atan2(-end.getY() + start.getY(), end.getX() - start.getX()));
    if (angle < 0) {
      angle += 360;
    }
    if (angle <= 180) {
      return this.linkOrientation == Orientation.HORIZONTAL ? Direction.RIGHT : Direction.UP;
    }
    return this.linkOrientation == Orientation.HORIZONTAL ? Direction.LEFT : Direction.DOWN;
  }

  private Point calculateStartPoint() {
    if (this.linkOrientation == Orientation.HORIZONTAL) {
      return calculateHorizontalStartPoint();
    }
    return calculateVerticalStartPoint();
  }

  private Point calculateHorizontalStartPoint() {
    final double labelCenter = this.line.messageBox().center().getX();
    final double x = labelCenter - ARROW_LENGTH / 2;
    double y = this.linkFocalPoint.getY();
    if (this.line.messageBox().center().getY() < y) {
      y -= LINK_CENTER_GAP;
    } else {
      y += LINK_CENTER_GAP;
    }
    return new Point(x, y);
  }

  private Point calculateVerticalStartPoint() {
    final double labelCenter = this.line.messageBox().center().getY();
    final double y = labelCenter - ARROW_LENGTH / 2;
    double x = this.linkFocalPoint.getX();
    if (this.line.messageBox().center().getX() < x) {
      x -= LINK_CENTER_GAP;
    } else {
      x += LINK_CENTER_GAP;
    }
    return new Point(x, y);
  }

  private Point calculateEndPoint(final Point startPoint) {
    if (this.linkOrientation == Orientation.HORIZONTAL) {
      return new Point(startPoint.getX() + ARROW_LENGTH, startPoint.getY());
    }
    return new Point(startPoint.getX(), startPoint.getY() + ARROW_LENGTH);
  }

}
