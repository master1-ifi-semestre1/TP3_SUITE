package creatures;

import java.awt.geom.Point2D;

public interface ICreature extends IActionable, IDrawable {
    public IEnvironment getEnvironment();

    public double getSpeed();

    public Point2D getPosition();

    public double directionFromAPoint(Point2D point2D, double axe);

    public double distanceFromAPoint(Point2D point2D);

    public double getDirection();
}
