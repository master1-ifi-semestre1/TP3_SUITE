package creatures;

import commons.Utils;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.toDegrees;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public abstract class AbstractCreature {

    public static final int DEFAULT_SIZE = 40;
    public static final int DEFAULT_VISION_DISTANCE = 50;

    /**
     * The field of view (FOV) is the extent of the observable world that is
     * seen at any given moment by a creature in radians.
     */
    protected double fieldOfView = (PI / 4);

    /**
     * The distance indicating how far a creature see in front of itself in
     * pixels.
     */
    protected double visionDistance = DEFAULT_VISION_DISTANCE;

    /** Position */
    //protected double x, y;
    protected Point2D position;

    /** Speed in pixels */
    protected double speed;

    /** Direction in radians (0,2*pi) */
    protected double direction;

    /** Color of the creature */
    protected Color color;

    /** Reference to the environment */
    protected final Environment environment;

    /** Size of the creature in pixels */
    protected final int size = DEFAULT_SIZE;

    public AbstractCreature(Environment environment, Point2D position) {
        this.environment = environment;

        setPosition(position);
    }

    /**
     * The core method of a creature. It is suppose to modify its internal state
     * (position, etc.) in a response to an environment. It can use methods
     * defined in the {@link #environment}.
     */
    public abstract void act();

    // ----------------------------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------------------------

    public double getFieldOfView() {
        return fieldOfView;
    }

    public double getVisionDistance() {
        return visionDistance;
    }

    /*public double getX() {
        return x;
    }*/

    /*public void setX(double newX) {
        if (newX > environment.getWidth() / 2) {
            newX = -environment.getWidth() / 2;
        } else if (newX < -environment.getWidth() / 2) {
            newX = environment.getWidth() / 2;
        }

        this.x = newX;
    }*/

    /*public double getY() {
        return y;
    }*/

    /*public void setY(double newY) {
        if (newY > environment.getHeight() / 2) {
            newY = -environment.getHeight() / 2;
        } else if (newY < -environment.getHeight() / 2) {
            newY = environment.getHeight() / 2;
        }

        this.y = newY;
    }*/

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction % (PI * 2);
        if (this.direction < 0) // % is keeping the number below 0
            this.direction += PI * 2;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    /**
     * Position of the creature.
     *
     * @return position of the creature as a {@link Point}
     */
    public Point2D getPosition() {
        return new Point2D.Double(position.getX(), position.getY());
    }

    public void setPosition(Point2D newPosition) {
        setPosition(position.getX(), position.getY());
    }

    public void setPosition(double x, double y) {
        Dimension dim = environment.getSize();

        if (x > dim.getWidth() / 2) {
            x = -dim.getWidth() / 2;
        } else if (x < -dim.getWidth() / 2) {
            x = dim.getWidth() / 2;
        }

        if (y > dim.getHeight() / 2) {
            y = -dim.getHeight() / 2;
        } else if (y < -dim.getHeight() / 2) {
            y = dim.getHeight() / 2;
        }

        this.position = new Point2D.Double(x, y);
    }

    // ----------------------------------------------------------------------------
    // Positioning methods
    // ----------------------------------------------------------------------------

    protected void move(double incX, double incY) {
        //setX(x + incX);
        //setY(y + incY);
        setPosition(position.getX() + incX, position.getY() + incY);
    }

    protected void rotate(double angle) {
        this.direction += angle;
    }

    // ----------------------------------------------------------------------------
    // Methods for calculating the direction
    // ----------------------------------------------------------------------------

    /**
     * Computes the direction between the given point {@code (x1, y1)} and the
     * current position in respect to a given {@code axis}.
     *
     * @return direction in radians between given point and current position in
     *         respect to a given {@code axis}.
     */
    public double directionFromAPoint(Point2D p, double axis) {
        double b = 0d;

        // use a inverse trigonometry to get the angle in an orthogonal triangle
        // formed by the points (x,y) and (x1,y1)
        if (position.getX() != p.getX()) {
            // if we are not in the same horizontal axis
            b = atan((position.getY() - p.getY()) / (position.getX() - p.getX()));
        } else if (position.getY() < p.getY()) {
            // below -pi/2
            b = -PI / 2;
        } else {
            // above +pi/2
            b = PI / 2;
        }

        // make a distinction between the case when the (x1, y1)
        // is right from the (x,y) or left
        if (position.getX() < p.getX()) {
            b += PI;
        }

        // align with the axis of the origin (x1,y1)
        b = b - axis;

        // make sure we always take the smaller angle
        // keeping the range between (-pi, pi)
        if (b >= PI)
            b = b - PI * 2;
        else if (b < -PI)
            b = b + PI * 2;

        return b % (PI * 2);
    }

    /**
     * Distance between the current position and a given point {@code(x1, y1)}.
     *
     * @return distance between the current position and a given point.
     */
    public double distanceFromAPoint(Point2D p) {
        return getPosition().distance(p);
    }

    // ----------------------------------------------------------------------------
    // Painting
    // ----------------------------------------------------------------------------

    /**
     * Draws creature to a given canvas.
     *
     * @param g2
     *            canvas where to draw the creature.
     */
    public void paint(Graphics2D g2) {
        // center the point
        g2.translate(position.getX(), position.getY());
        // center the surrounding rectangle
        g2.translate(-size / 2, -size / 2);
        // center the arc
        // rotate towards the direction of our vector
        g2.rotate(-direction, size / 2, size / 2);

        // set the color
        g2.setColor(color);
        // we need to do PI - FOV since we want to mirror the arc
        g2.fillArc(0, 0, size, size, (int) toDegrees(-fieldOfView / 2),
                (int) toDegrees(fieldOfView));

    }

    public String toString() {
        Class<?> cl = getClass();

        StringBuilder sb = new StringBuilder();
        sb.append(getFullName(cl));
        sb.append("\n---\n");
        sb.append(Utils.mkString(getProperties(cl), "\n"));

        return sb.toString();
    }

    private List<String> getProperties(Class<?> clazz) {
        List<String> properties = new ArrayList<String>();

        Collection<Field> fields = Arrays.asList(clazz.getDeclaredFields());

        for (Field f : fields) {
            String name = f.getName();
            Object value = null;

            try {
                value = f.get(this);
            } catch (IllegalArgumentException e) {
                value = "unable to get value: " + e;
            } catch (IllegalAccessException e) {
                value = "unable to get value: " + e;
            } finally {
                properties.add(name + ": " + value);
            }
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            properties.addAll(getProperties(superclass));
        }

        return properties;
    }

    private String getFullName(Class<?> clazz) {
        String name = clazz.getSimpleName();
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            return getFullName(superclass) + " > " + name;
        } else {
            return name;
        }
    }

    /**
     * Concatenates elements using a {@code sep} as a separator
     * @param c
     * @param sep
     * @return
     */
    public static String mkString(Collection<String> c, final String sep) {
        if (c.isEmpty()) {
            return "";
        }

        String head = c.iterator().next();
        Collection<String> tail = new ArrayList<String>(c);
        tail.remove(head);

        return head + sep + mkString(tail, sep);
    }


}
