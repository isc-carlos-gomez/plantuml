package net.sourceforge.plantuml.communicationdiagram.line;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sourceforge.plantuml.communicationdiagram.line.Rectangle.Overlap;

/**
 * Unit tests {@link Rectangle}.
 *
 * @author Carlos Gomez
 */
public class RectangleTest {

  @Test
  public void findsHorizontalOverlapWhenRectangle1HorizontalEdgeCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(1, 6, 9, 10);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.HORIZONTAL);
  }

  @Test
  public void findsHorizontalOverlapWhenRectangle2HorizontalEdgeCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(1, 6, 9, 10);
    final Rectangle rectangle2 = new Rectangle(0, 0, 10, 5);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.HORIZONTAL);
  }

  @Test
  public void findsHorizontalOverlapWhenRectangle1HorizontalEdgePartiallyCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(5, 6, 15, 10);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.HORIZONTAL);
  }

  @Test
  public void findsHorizontalOverlapWhenRectangle2HorizontalEdgePartiallyCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(5, 6, 15, 10);
    final Rectangle rectangle2 = new Rectangle(0, 0, 10, 5);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.HORIZONTAL);
  }

  @Test
  public void findsVerticalOverlapWhenRectangle1VerticalEdgeCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 5, 10);
    final Rectangle rectangle2 = new Rectangle(6, 1, 10, 9);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsVerticalOverlapWhenRectangle2VerticalEdgeCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(6, 1, 10, 9);
    final Rectangle rectangle2 = new Rectangle(0, 0, 5, 10);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsVerticalOverlapWhenRectangle1VerticalEdgePartiallyCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 5, 10);
    final Rectangle rectangle2 = new Rectangle(6, 5, 10, 15);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsVerticalOverlapWhenRectangle2VerticalEdgePartiallyCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(6, 5, 10, 15);
    final Rectangle rectangle2 = new Rectangle(0, 0, 5, 10);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsHorizontalOverlapWhenBothEdgesOverlapButHorizontalOverlapIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 5, 10);
    final Rectangle rectangle2 = new Rectangle(1, 9, 6, 19);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.HORIZONTAL);
  }

  @Test
  public void findsVerticalOverlapWhenBothEdgesOverlapButVerticalOverlapIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(9, 1, 11, 6);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsHorizontalOverlapWhenBothEdgesFullyOverlapButHorizontalEdgeIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 20, 20);
    final Rectangle rectangle2 = new Rectangle(1, 2, 18, 4);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.HORIZONTAL);
  }

  @Test
  public void findsVerticalOverlapWhenBothEdgesFullyOverlapButVerticalEdgeIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 20, 10);
    final Rectangle rectangle2 = new Rectangle(1, 1, 4, 9);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsVerticalOverlapInCircumscribedSquares() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 10);
    final Rectangle rectangle2 = new Rectangle(1, 1, 9, 9);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.VERTICAL);
  }

  @Test
  public void findsNoOverlap() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(11, 6, 21, 11);
    assertEquals(rectangle1.overlap(rectangle2), Overlap.NONE);
  }

}
