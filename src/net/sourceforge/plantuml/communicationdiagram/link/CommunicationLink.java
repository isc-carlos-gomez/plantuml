package net.sourceforge.plantuml.communicationdiagram.link;

import java.util.Objects;
import java.util.UUID;

import net.sourceforge.plantuml.svek.Line;

/**
 * Entity that provides access to the information required to render a group of lines as a single
 * Communication Diagram Link with its messages flowing around.
 * <p>
 * First the link must be created and get added all the lines that are rendered together to draw it.
 * Then every line in the group can use this link to enquire for features and behavior required
 * during the drawing process.
 *
 * @author Carlos Gomez
 */
public class CommunicationLink {

  private final UUID id;
  private LineGroup group;

  /**
   * Creates a new instance.
   *
   * @param id
   *        the unique identifier of this link
   */
  public CommunicationLink(final UUID id) {
    this.id = id;
    this.group = new EmptyLineGroup();
  }

  /**
   * Adds a new line to this link.
   *
   * @param line
   *        the line to add
   */
  public void addLine(final Line line) {
    this.group = this.group.addLine(line);
  }

  /**
   * @param line
   *        the line to analyze
   * @return whether a line is visible in this link or not
   */
  public boolean isLineVisible(final Line line) {
    return this.group.isLineVisible(line);
  }

  /**
   * @param line
   *        the line the message arrow is created for
   * @return a new {@link MessageArrow} which position corresponds with the position of a line label
   */
  public MessageArrow buildMessageArrow(final Line line) {
    return new MessageArrowBuilder(line, this.group.orientation(), this.group.focalPoint())
        .build();
  }

  /**
   * @param line
   *        the line containing the message to position
   * @return the point corresponding to the message's top left corner that is fully aligned with its
   *         message arrow
   */
  public Point calculateMessagePosition(final Line line) {
    return new MessagePositionCalculator(line, this.group.orientation(), this.group.focalPoint())
        .calculate();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final CommunicationLink other = (CommunicationLink) obj;
    return Objects.equals(this.id, other.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

}
