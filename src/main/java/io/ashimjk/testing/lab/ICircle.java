package io.ashimjk.testing.lab;

public interface ICircle {

    double getX();

    double setX(double x);

    double getY();

    double setY(double y);

    double getRadius();

    double setRadius(double radius);

    boolean isEqualTo(ICircle circle);
}
