package net.sourceforge.plantuml.communicationdiagram.link;

import java.awt.geom.Point2D;

import net.sourceforge.plantuml.Direction;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import net.sourceforge.plantuml.graphic.HtmlColorUtils;
import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.UChangeColor;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.ULine;
import net.sourceforge.plantuml.ugraphic.UTranslate;

/**
 * A drawable message arrow flowing around a Communication Diagram Link.
 *
 * @author Carlos Gomez
 */
public class MessageArrow implements UDrawable {

  private final Point startPoint;
  private final Point endPoint;
  private final Direction direction;

  /**
   * Creates a new instance.
   *
   * @param startPoint
   *        the starting point
   * @param endPoint
   *        the end point
   * @param direction
   *        the arrow direction
   */
  MessageArrow(final Point startPoint, final Point endPoint, final Direction direction) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.direction = direction;
  }

  @Override
  public void drawU(final UGraphic ug) {
    ug.apply(new UTranslate(this.startPoint.getX(), this.startPoint.getY()))
        .apply(new UChangeColor(HtmlColorUtils.BLACK))
        .draw(new ULine(this.startPoint, this.endPoint));

    final Point2D arrowHeadPosition;
    if (this.direction == Direction.RIGHT || this.direction == Direction.DOWN) {
      arrowHeadPosition = this.endPoint;
    } else {
      arrowHeadPosition = this.startPoint;
    }

    LinkDecor.ARROW.getExtremityFactory(HtmlColorUtils.BLACK)
        .createUDrawable(arrowHeadPosition, arrowHeadAngle(), null)
        .drawU(ug.apply(new UChangeColor(HtmlColorUtils.BLACK)));
  }

  private double arrowHeadAngle() {
    switch (this.direction) {
      case DOWN:
        return Math.PI / 2;
      case UP:
        return 3 * Math.PI / 2;
      case RIGHT:
        return 0;
      case LEFT:
        return Math.PI;
      default:
        throw new IllegalStateException("Unsupported direction: " + this.direction);
    }
  }

}
