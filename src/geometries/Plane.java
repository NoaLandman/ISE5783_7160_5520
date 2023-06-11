/**
 * A plane geometry defined by a point and a normal vector or three points
 *
 * @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {

    /* the reference point of the plane*/
    private final Point q0;

    /* the normal vector of the plane*/
    private final Vector normal;

    /**
     * Constructs a plane using a reference point and a normal vector.
     * @param q0 the reference point of the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        if (!(isZero(normal.length() - 1d))) {
            this.normal = normal.normalize();
        } else {
            this.normal = normal;
        }

    }

    /**
     * Constructs a plane using three points.
     * @param p1 first point in the plane
     * @param p2 second point in the plane
     * @param p3 third point in the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("two of the points are identical");


        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        if (U.normalize().equals(V.normalize()))
            throw new IllegalArgumentException("there is a linear dependents between the vectors");
        //right hand rule
        Vector N = U.crossProduct(V);
        normal = N.normalize();

    }

    /**
     * Returns the reference point of the plane.
     * @return the reference point of the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Returns the normal vector of the plane.
     * @return the normal vector of the plane
     */

    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the normal vector to the plane at the given point.
     *
     * @param point the point on the plane
     * @return the normal vector to the plane at the given point
     */

    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    /**
     * Finds the intersection points between the given ray and the plane.
     *
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if there are no intersections
     */


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = normal;

        //ray cannot start from the plane
        if (q0.equals(p0))
            return null;

        double nv = alignZero(n.dotProduct(v));
        // ray is lying in the plane axis

        if (isZero(nv))
            return null;

        Vector P0_Q0 = q0.subtract(p0); // Q - P0
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

        if (isZero(nP0Q0))
            return null;

        double t = alignZero(nP0Q0 / nv);
        // t should be bigger than 0
        if (t <= 0)
            return null;
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}


