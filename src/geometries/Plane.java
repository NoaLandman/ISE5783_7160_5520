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
     * find intersection between ray and plane
     * @param ray ray towards the plane
     * @return  immutable list of one intersection point as  {@link GeoPoint} object
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        // ray cannot start at plane's origin point
        if(q0.equals(P0))
            return null;

        // ray points -> P = p0 + t*v_ (v_ = direction vector)
        // points on plane  if normal vector dot product with vector from
        // origin point to proposed point == 0
        // glossary:  (n,v) = dot product between vectors n,v
        // isolating t ,( scaling factor for ray's direction vector ) ->
        // t = (normal vector, vector from origin to point)/ (normal vector, ray vector)
        // if t is positive ray intersects plane
        double nv = n.dotProduct(v);

        // ray direction cannot be parallel to plane orientation
        if (isZero(nv)){
            return null;
        }

        // vector from origin to point
        Vector Q_P0 = q0.subtract(P0);

        double nQMinusP0 = alignZero(n.dotProduct(Q_P0));

        //t should not be equal to 0
        if( isZero(nQMinusP0)){
            return null;
        }
        // scaling factor for ray , if value is positive
        // ray intersects plane
        double t = alignZero(nQMinusP0 / nv);
        if (t > 0 && alignZero(t-maxDistance) <= 0){
            //return immutable List
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        // no intersection point  - ray and plane in opposite  direction
        return null;
    }
}


