package net.sourceforge.plantuml.communicationdiagram.sequence;

import net.sourceforge.plantuml.command.regex.RegexResult;

/**
 * Service to decorate a command arguments ({@link RegexResult}s) with sequence numbers.
 *
 * @author Carlos Gomez
 */
public class CommandArgumentSequenceDecorator {

  private static final String LINK_LABEL_KEY = "LABEL_LINK";
  private static final int INDENTATION_SIZE = 2;
  private MessageSequence sequence;

  /**
   * Creates a new instance with a single-level sequence starting at zero.
   */
  public CommandArgumentSequenceDecorator() {
    this.sequence = new MessageSequence();
  }

  /**
   * Decorates the given command argument by prefixing its link label (if present) with a sequence
   * number.
   *
   * @param commandArgument
   *        the command argument to decorate
   * @param commandString
   *        the command string where the argument was created from
   * @return the given command argument decorated if its link label is present, or the original
   *         command argument otherwise
   */
  public RegexResult decorate(final RegexResult commandArgument, final String commandString) {
    if (hasLinkLabel(commandArgument)) {
      return decorateWithSequence(commandArgument, commandString);
    }
    return commandArgument;
  }

  private boolean hasLinkLabel(final RegexResult originalResult) {
    return originalResult.get(LINK_LABEL_KEY, 0) != null;
  }

  private RegexResult decorateWithSequence(final RegexResult originalResult, final String commandString) {
    final String newLinkLabel = nextSequence(commandString) + originalResult.get(LINK_LABEL_KEY, 0);
    return new CustomLinkLabelRegexResult(originalResult, newLinkLabel);
  }

  private MessageSequence nextSequence(final String commandString) {
    final int leadingWhiteSpaces = (int) commandString.chars()
        .takeWhile(Character::isWhitespace)
        .count();
    final int level = leadingWhiteSpaces / INDENTATION_SIZE;
    this.sequence = this.sequence.next(level);
    return this.sequence;
  }

}
