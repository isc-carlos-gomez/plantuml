package net.sourceforge.plantuml.communicationdiagram.link;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests {@link MessageSequence}.
 *
 * @author Carlos Gomez
 */
public class MessageSequenceTest {

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
  public void generatesNextSequence() {
    MessageSequence sequence = new MessageSequence();
    assertEquals(sequence.toString(), "0: ");

    sequence = sequence.next(0);
    assertEquals(sequence.toString(), "1: ");

    sequence = sequence.next(1);
    assertEquals(sequence.toString(), "1.1: ");

    sequence = sequence.next(1);
    assertEquals(sequence.toString(), "1.2: ");

    sequence = sequence.next(2);
    assertEquals(sequence.toString(), "1.2.1: ");

    sequence = sequence.next(0);
    assertEquals(sequence.toString(), "2: ");

    sequence = sequence.next(3);
    assertEquals(sequence.toString(), "2.0.0.1: ");
  }

}
