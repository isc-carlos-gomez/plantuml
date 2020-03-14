package net.sourceforge.plantuml.svek;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import net.sourceforge.plantuml.posimo.DotPath;
import net.sourceforge.plantuml.ugraphic.MinMax;

/**
 * Entity representing a group of lines that are analyzed together to determine some of their
 * features and behaviors so that when they are rendered together they draw a single communication
 * diagram link with its messages flowing around.
 * 
 * @author Carlos Gomez
 *
 */
public class LineGroup {

  private final UUID id;
  private final Set<Line> lines;

  /**
   * Creates a new instance.
   * 
   * @param id
   *        the unique identifier of this line group
   */
  public LineGroup(final UUID id) {
    this.id = id;
    this.lines = new HashSet<>();
  }

  /**
   * Adds a new line to this group.
   * 
   * @param line
   *        the line to add
   * @return true if this group didn't contain the given line and was added, false otherwise
   */
  public boolean addLine(final Line line) {
    return this.lines.add(line);
  }

  /**
   * 
   * @param line
   *        the line to analyze
   * @return whether the given line is visible in this group or not
   */
  public boolean isVisible(final Line line) {
    if (this.lines.size() == 1) {
      return true;
    }
    return line == centralLine();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final LineGroup other = (LineGroup) obj;
    return Objects.equals(this.id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  private Line centralLine() {
    if (this.lines.size() != 3) {
      throw new IllegalStateException(
          "Unsupported line group size, expected 3 but found: " + this.lines.size());
    }
    final Comparator<LineCenter> lineCenterComparator;
    if (isHorizontallyOriented()) {
      lineCenterComparator = (line1, line2) -> (int) (line1.center.getY() - line2.center.getY());
    } else {
      lineCenterComparator = (line1, line2) -> (int) (line1.center.getX() - line2.center.getX());
    }
    return this.lines.stream()
        .map(LineCenter::new)
        .sorted(lineCenterComparator)
        .collect(Collectors.toList())
        .get(1).line;
  }

  private boolean isHorizontallyOriented() {
    final Dimension2D groupDimension = this.lines.stream()
        .map(Line::getDotPath)
        .map(DotPath::getMinMax)
        .reduce(MinMax.getEmpty(true), MinMax::addMinMax)
        .getDimension();
    return groupDimension.getWidth() >= groupDimension.getHeight();
  }

  private static class LineCenter {

    private final Line line;
    private final Point2D center;

    private LineCenter(final Line line) {
      this.line = line;
      this.center = line.getDotPath().getMiddle().getPt();
    }

  }

}
