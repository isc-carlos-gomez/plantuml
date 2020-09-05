package net.sourceforge.plantuml.communicationdiagram.link;

import net.sourceforge.plantuml.Direction;
import net.sourceforge.plantuml.command.regex.RegexResult;

/**
 * Encapsulates some of the properties required to create communication links.
 *
 * @author Carlos Gomez
 */
class CommunicationLinkProperties {

  private final RegexResult commandArgument;
  private final Direction direction;

  /**
   * Creates a new instance.
   *
   * @param commandArgument
   *        the arguments (properties) that were used to execute the command that is requesting the
   *        creation of a communication link
   * @param direction
   *        the direction (up, down, right, left) of the communication link
   */
  CommunicationLinkProperties(final RegexResult commandArgument, final Direction direction) {
    this.commandArgument = commandArgument;
    this.direction = direction;
  }

  /**
   * @return whether the link is dashed
   */
  boolean isDashed() {
    return this.commandArgument.get("ARROW_BODY1", 0).contains("--")
        || this.commandArgument.get("ARROW_BODY2", 0).contains("--");
  }

  /**
   * @return whether the link is vertical
   */
  boolean isVertical() {
    return this.direction == Direction.UP || this.direction == Direction.DOWN;
  }

  /**
   * @return the arrow style of the link
   */
  String arrowStyle() {
    return this.commandArgument.getLazzy("ARROW_STYLE", 0);
  }

}
