package net.sourceforge.plantuml.communicationdiagram.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Immutable object representing a zero-based and multi-level numeric sequence.
 *
 * @author Carlos Gomez
 */
class MessageSequence {

  private final List<Integer> levels;

  /**
   * Creates a new sequence with a single level, starting at zero.
   */
  MessageSequence() {
    this.levels = new ArrayList<>();
    this.levels.add(0);
  }

  private MessageSequence(final List<Integer> levels) {
    this.levels = levels;
  }

  /**
   * Produces the next sequence for the given level by incrementing the current sequence at the
   * given level. If the given level is beyond the levels present in the current sequence all the
   * missing levels will be filled out with zeros.
   * <p>
   * <b>Examples:</b>
   *
   * <pre>
   * | Current | Level | Next     |
   * |---------|-------|----------|
   * | 0:      | 0     | 1:       |
   * | 1:      | 1     | 1.1:     |
   * | 1.1:    | 1     | 1.2:     |
   * | 1.2:    | 2     | 1.2.1:   |
   * | 1.2.1:  | 0     | 2:       |
   * | 2:      | 3     | 2.0.0.1: |
   * </pre>
   *
   * @param level
   *        the maximum level of the next sequence
   * @return the next sequence produced by incrementing the current sequence at the given level
   */
  MessageSequence next(final int level) {
    checkLevelIsPositive(level);
    final List<Integer> nextLevels = IntStream.range(0, level)
        .mapToObj(this::currentValue)
        .collect(Collectors.toList());
    nextLevels.add(nextValue(level));
    return new MessageSequence(nextLevels);
  }

  /**
   * Prints this sequence separating levels by periods and suffixing with colon and space, e.g.:
   * {@code 1.2.3: }
   */
  @Override
  public String toString() {
    return this.levels.stream()
        .map(Object::toString)
        .collect(Collectors.joining(".", "", ": "));
  }

  private void checkLevelIsPositive(final int level) {
    if (level < 0) {
      throw new IllegalArgumentException("Unsupported level: " + level);
    }
  }

  private int currentValue(final int level) {
    if (level >= this.levels.size()) {
      return 0;
    }
    return this.levels.get(level);
  }

  private int nextValue(final int level) {
    return currentValue(level) + 1;
  }

}
