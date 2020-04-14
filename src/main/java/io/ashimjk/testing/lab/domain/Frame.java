package io.ashimjk.testing.lab.domain;

import io.ashimjk.testing.lab.ICircle;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private double length;
    private double width;
    private List<ICircle> circles = new ArrayList<>();


    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        if (length <= 0)
            throw new IllegalArgumentException("Length must be positive");
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width <= 0)
            throw new IllegalArgumentException("Width must be positive");
        this.width = width;
    }

    public boolean tryAddCircle(ICircle circle) {
        boolean isMatchFound = this.circles.stream().anyMatch(c -> c.isEqualTo(circle));

        if (isMatchFound) {
            return false;
        }

        // isInsideOfCircle
        if (isCircleInXAxisBorder(circle)
                || isCircleInFrameLengthBorder(circle)
                || isCircleInYAxisBorder(circle)
                || isCircleInFrameWidthBorder(circle)
        ) {
            return false;
        }

        this.circles.add(circle);
        return true;
    }

    public int getCirclesCount() {
        return circles.size();
    }

    private boolean isCircleInFrameWidthBorder(ICircle circle) {
        return circle.getY() > this.width - circle.getRadius();
    }

    private boolean isCircleInFrameLengthBorder(ICircle circle) {
        return circle.getX() > this.length - circle.getRadius();
    }

    private boolean isCircleInXAxisBorder(ICircle circle) {
        return circle.getX() < circle.getRadius();
    }

    private boolean isCircleInYAxisBorder(ICircle circle) {
        return circle.getY() < circle.getRadius();
    }
}
