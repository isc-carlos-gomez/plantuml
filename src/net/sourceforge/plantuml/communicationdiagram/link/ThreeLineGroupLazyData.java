package net.sourceforge.plantuml.communicationdiagram.link;

import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import net.sourceforge.plantuml.posimo.DotPath;
import net.sourceforge.plantuml.ugraphic.MinMax;

/**
 * Data used by a {@link ThreeLineGroup}. Its lazy initialized because the lines are not ready for
 * analysis at creation time and we must wait until lines are being drawn for the analysis to work
 * correctly.
 *
 * @author Carlos Gomez
 */
class ThreeLineGroupLazyData {

  private final List<CommunicationLine> lines;
  private boolean linesSorted;

  ThreeLineGroupLazyData(final CommunicationLine line1, final CommunicationLine line2, final CommunicationLine line3) {
    this.lines = new ArrayList<>();
    this.lines.add(Objects.requireNonNull(line1));
    this.lines.add(Objects.requireNonNull(line2));
    this.lines.add(Objects.requireNonNull(line3));
  }

  CommunicationLine firstLine() {
    return line(0);
  }

  CommunicationLine centralLine() {
    return line(1);
  }

  CommunicationLine lastLine() {
    return line(2);
  }

  private CommunicationLine line(final int index) {
    if (!this.linesSorted) {
      sortLines();
      this.linesSorted = true;
    }
    return this.lines.get(index);
  }

  private void sortLines() {
    if (horizontallyOrientedLines()) {
      Collections.sort(this.lines, ThreeLineGroupLazyData::sortByCentralY);
    } else {
      Collections.sort(this.lines, ThreeLineGroupLazyData::sortByCentralX);
    }
  }

  private boolean horizontallyOrientedLines() {
    final Dimension2D groupDimension = this.lines.stream()
        .map(CommunicationLine::getDotPath)
        .map(DotPath::getMinMax)
        .reduce(MinMax.getEmpty(true), MinMax::addMinMax)
        .getDimension();
    return groupDimension.getWidth() >= groupDimension.getHeight();
  }

  private static int sortByCentralY(final CommunicationLine line1, final CommunicationLine line2) {
    final double diff = line1.messageBox().center().getY() - line2.messageBox().center().getY();
    return (int) diff;
  }

  private static int sortByCentralX(final CommunicationLine line1, final CommunicationLine line2) {
    final double diff = line1.messageBox().center().getX() - line2.messageBox().center().getX();
    return (int) diff;
  }

}
