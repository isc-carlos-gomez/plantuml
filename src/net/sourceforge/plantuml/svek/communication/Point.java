package net.sourceforge.plantuml.svek.communication;

import java.awt.geom.Point2D;

/**
 * Wrapper to simplify use of {@link Point2D.Double}.
 *
 * @author Carlos Gomez
 */
public class Point extends Point2D.Double {

  private static final long serialVersionUID = -855719837691776621L;

  public Point(final double x, final double y) {
    super(x, y);
  }

}
