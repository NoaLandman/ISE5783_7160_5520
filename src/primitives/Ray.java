package primitives;

import java.util.List;

/**
 * The Ray class represents a Ray in 3D space.
 *
 * ...
 *
 * @author Avigail Tenenbaum and Noa Landman
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    /**
     * Constructor for Ray.
     *
     * @param p0  The starting point of the Ray
     * @param dir The direction of the Ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Getter for p0.
     *
     * @return The starting point of the Ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for dir.
     *
     * @return The direction of the Ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Overrides the toString method of Object class.
     *
     * @return A String representation of the Ray object
     */
    @Override
    public String toString() {
        return "Ray: " +
                "p0=" + p0 +
                ", dir=" + dir;
    }

    /**
     * Overrides the equals method of Object class.
     *
     * @param obj The object to compare for equality
     * @return true if this Ray and obj are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }

    /**
     * Computes a point along the Ray at a given distance.
     *
     * @param t The distance from the starting point of the Ray
     * @return The point along the Ray at the given distance
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }
    /**
     * search from list of points what is the closest point to the ray and return is
     * back
     *
     * @param intersections - list of points we want to scan
     * @return the closest point to the ray
     */
    public Point findClosestPoint(List<Point> intersections) {
        if (intersections == null)
            return null;
        Point minPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point item : intersections) {
            double d = item.distance(p0);
            if (d < minDistance) {
                minPoint = item;
                minDistance = d;
            }
        }
        return minPoint;
    }
}