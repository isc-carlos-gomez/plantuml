package net.sourceforge.plantuml.communicationdiagram.link;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests {@link MessageSequence}.
 *
 * @author Carlos Gomez
 */
class MessageSequenceTest {

  /**
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
   */
  @Test
  void generatesNextSequence() {
    MessageSequence sequence = new MessageSequence();
    assertThat(sequence).hasToString("0: ");

    sequence = sequence.next(0);
    assertThat(sequence).hasToString("1: ");

    sequence = sequence.next(1);
    assertThat(sequence).hasToString("1.1: ");

    sequence = sequence.next(1);
    assertThat(sequence).hasToString("1.2: ");

    sequence = sequence.next(2);
    assertThat(sequence).hasToString("1.2.1: ");

    sequence = sequence.next(0);
    assertThat(sequence).hasToString("2: ");

    sequence = sequence.next(3);
    assertThat(sequence).hasToString("2.0.0.1: ");
  }

}
