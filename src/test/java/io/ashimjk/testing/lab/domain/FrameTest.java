package io.ashimjk.testing.lab.domain;

import io.ashimjk.testing.lab.ICircle;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FrameTest {

    private final double smallestPositiveDecimal = 1e-28;

    @Test
    void length_ReceivesNegativeValue_throwsException() {
        assertLengthException(-3.5);
    }

    @Test
    void length_ReceivesZero_throwsException() {
        assertLengthException(0);
    }

    private void assertLengthException(double length) {
        Frame frame = new Frame();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> frame.setLength(length));
        assertEquals("Length must be positive", exception.getMessage());
    }

    @Test
    void length_ReceivesSmallestPositiveValue_GetterReturnsSameValue() {
        assertLength(smallestPositiveDecimal);
    }

    @Test
    void length_ReceivesPositiveValue_GetterReturnsSameValue() {
        assertLength(3.7);
    }

    private void assertLength(double length) {
        Frame frame = new Frame();
        frame.setLength(length);

        assertEquals(length, frame.getLength());
    }

    @Test
    void width_ReceivesNegativeValue_throwsException() {
        assertWidthException(-4.5);
    }

    @Test
    void width_ReceivesZero_throwsException() {
        assertWidthException(0);
    }

    private void assertWidthException(double width) {
        Frame frame = new Frame();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> frame.setWidth(width));
        assertEquals("Width must be positive", exception.getMessage());
    }

    @Test
    void width_ReceivesSmallestPositiveValue_GetterReturnsSameValue() {
        assertWidth(smallestPositiveDecimal);
    }

    @Test
    void width_ReceivesPositiveValue_GetterReturnsSameValue() {
        assertWidth(4.7);
    }

    private void assertWidth(double width) {
        Frame frame = new Frame();
        frame.setWidth(width);

        assertEquals(width, frame.getWidth());
    }

    @Test
    void tryAddCircle_CircleEntirelyInsideFrame_ReturnsTrue() {
        assertTryAddCircleResult(1.5, 1.5, true);
    }

    @Test
    void tryAddCircle_CircleTouchesLeftEdge_ReturnsTrue() {
        assertTryAddCircleResult(1.0, 1.5, true);
    }

    @Test
    void tryAddCircle_CircleTouchesTopEdge_ReturnsTrue() {
        assertTryAddCircleResult(1.5, 1.0, true);
    }

    @Test
    void tryAddCircle_CircleTouchesRightEdge_ReturnsTrue() {
        assertTryAddCircleResult(2.0, 1.5, true);
    }

    @Test
    void tryAddCircle_CircleTouchesBottomEdge_ReturnsTrue() {
        assertTryAddCircleResult(1.5, 2.0, true);
    }

    @Test
    void tryAddCircle_CircleCrossesLeftEdge_ReturnsFalse() {
        assertTryAddCircleResult(0.7, 1.5, false);
    }

    @Test
    void tryAddCircle_CircleCrossesTopEdge_ReturnsFalse() {
        assertTryAddCircleResult(1.5, 0.7, false);
    }

    @Test
    void tryAddCircle_CircleCrossesRightEdge_ReturnsFalse() {
        assertTryAddCircleResult(2.3, 1.5, false);
    }

    @Test
    void tryAddCircle_CircleCrossesBottomEdge_ReturnsFalse() {
        assertTryAddCircleResult(1.5, 2.3, false);
    }

    @Test
    void tryAddCircle_CircleCompletelyOutsideTheFrame_ReturnsFalse() {
        assertTryAddCircleResult(5.4, 6.1, false);
    }

    private void assertTryAddCircleResult(double x, double y, boolean expectedResult) {
        Frame frame = createFrame();
        ICircle circle = mockCircle(x, y, false);
        boolean result = frame.tryAddCircle(circle);
        assertEquals(expectedResult, result);
    }

    @Test
    void circlesCount_CircleInsideTheFrame_ReturnsOne() {
        assertCirclesCount(1, 1.5, 1.5);
    }

    @Test
    void circlesCount_CircleInsideTheFrame_ReturnsZero() {
        assertCirclesCount(0, 0.7, 1.3);
    }

    @Test
    void tryAddCircle_CircleEqualToExistingCircle_ReturnsFalse() {
        assertTryAddCircleTwoCircles(true, false);
    }

    @Test
    void tryAddCircle_CircleEqualToExistingCircle_ReturnsTrue() {
        assertTryAddCircleTwoCircles(false, true);
    }

    private void assertTryAddCircleTwoCircles(boolean equalsResult, boolean expectedResult) {
        Frame frame = createFrame();
        ICircle circle1 = mockCircle(1.5, 1.5, equalsResult);
        ICircle circle2 = mockCircle(1.5, 1.6, equalsResult);

        frame.tryAddCircle(circle1);
        boolean result = frame.tryAddCircle(circle2);
        assertEquals(expectedResult, result);
    }

    private void assertCirclesCount(int expectedCount, double v, double v2) {
        Frame frame = createFrameAndTryAddMockCircle(v, v2);
        assertEquals(expectedCount, frame.getCirclesCount());
    }

    private Frame createFrameAndTryAddMockCircle(double x, double y) {
        Frame frame = createFrame();
        ICircle circle = mockCircle(x, y, false);
        frame.tryAddCircle(circle);
        return frame;
    }

    private ICircle mockCircle(double x, double y, boolean equalsResult) {
        ICircle circle = Mockito.mock(ICircle.class);
        when(circle.getX()).thenReturn(x);
        when(circle.getY()).thenReturn(y);
        when(circle.getRadius()).thenReturn(1.0);
        when(circle.isEqualTo(any())).thenReturn(equalsResult);
        return circle;
    }

    private Frame createFrame() {
        Frame frame = new Frame();
        frame.setLength(3.0);
        frame.setWidth(3.0);
        return frame;
    }

}
