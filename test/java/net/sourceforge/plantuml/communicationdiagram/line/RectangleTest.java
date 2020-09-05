package net.sourceforge.plantuml.communicationdiagram.line;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.sourceforge.plantuml.communicationdiagram.line.Rectangle.Overlap;


/**
 * Unit tests {@link Rectangle}.
 *
 * @author Carlos Gomez
 */
class RectangleTest {

  @Test
  void findsHorizontalOverlapWhenRectangle1HorizontalEdgeCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(1, 6, 9, 10);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.HORIZONTAL);
  }

  @Test
  void findsHorizontalOverlapWhenRectangle2HorizontalEdgeCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(1, 6, 9, 10);
    final Rectangle rectangle2 = new Rectangle(0, 0, 10, 5);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.HORIZONTAL);
  }

  @Test
  void findsHorizontalOverlapWhenRectangle1HorizontalEdgePartiallyCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(5, 6, 15, 10);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.HORIZONTAL);
  }

  @Test
  void findsHorizontalOverlapWhenRectangle2HorizontalEdgePartiallyCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(5, 6, 15, 10);
    final Rectangle rectangle2 = new Rectangle(0, 0, 10, 5);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.HORIZONTAL);
  }

  @Test
  void findsVerticalOverlapWhenRectangle1VerticalEdgeCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 5, 10);
    final Rectangle rectangle2 = new Rectangle(6, 1, 10, 9);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsVerticalOverlapWhenRectangle2VerticalEdgeCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(6, 1, 10, 9);
    final Rectangle rectangle2 = new Rectangle(0, 0, 5, 10);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsVerticalOverlapWhenRectangle1VerticalEdgePartiallyCoversRectangle2() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 5, 10);
    final Rectangle rectangle2 = new Rectangle(6, 5, 10, 15);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsVerticalOverlapWhenRectangle2VerticalEdgePartiallyCoversRectangle1() {
    final Rectangle rectangle1 = new Rectangle(6, 5, 10, 15);
    final Rectangle rectangle2 = new Rectangle(0, 0, 5, 10);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsHorizontalOverlapWhenBothEdgesOverlapButHorizontalOverlapIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 5, 10);
    final Rectangle rectangle2 = new Rectangle(1, 9, 6, 19);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.HORIZONTAL);
  }

  @Test
  void findsVerticalOverlapWhenBothEdgesOverlapButVerticalOverlapIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(9, 1, 11, 6);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsHorizontalOverlapWhenBothEdgesFullyOverlapButHorizontalEdgeIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 20, 20);
    final Rectangle rectangle2 = new Rectangle(1, 2, 18, 4);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.HORIZONTAL);
  }

  @Test
  void findsVerticalOverlapWhenBothEdgesFullyOverlapButVerticalEdgeIsLonger() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 20, 10);
    final Rectangle rectangle2 = new Rectangle(1, 1, 4, 9);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsVerticalOverlapInCircumscribedSquares() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 10);
    final Rectangle rectangle2 = new Rectangle(1, 1, 9, 9);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.VERTICAL);
  }

  @Test
  void findsNoOverlap() {
    final Rectangle rectangle1 = new Rectangle(0, 0, 10, 5);
    final Rectangle rectangle2 = new Rectangle(11, 6, 21, 11);
    assertThat(rectangle1.overlap(rectangle2))
        .isEqualTo(Overlap.NONE);
  }

  @Test
  void test() {
    System.out.println(Math.toDegrees(Math.atan2(70, 0)));
    // System.out.println(Math.toDegrees(Math.atan2(5, 0)));
    // System.out.println(Math.toDegrees(Math.atan2(0, -5)));
    // System.out.println(Math.toDegrees(Math.atan2(-5, 0)) + 360);

    // assertThat(Math.atan2(0, 5))
    // .isGreaterThan(-Math.PI / 4)
    // .isLessThan(Math.PI / 4);
    //
    // assertThat(Math.atan2(5, 0))
    // .isGreaterThan(Math.PI / 4)
    // .isLessThan(3 * Math.PI / 4);
    //
    // assertThat(Math.atan2(0, -5))
    // .isGreaterThan(3 * Math.PI / 4)
    // .isLessThan(-3 * Math.PI / 4);
  }

}
