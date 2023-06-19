/**
 * Sphere class Represents a sphere in 3D space with a given radius and center point.
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

public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    private final Point center;

    /**
     * Constructs a new sphere object with the given radius and center point.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal vector to the surface of the sphere at the given point.
     *
     * @param point the point on the sphere for which to return the normal vector
     * @return the normal vector to the surface of the sphere at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        /** if(center.distance(point)!=radius)
         throw new IllegalArgumentException("the point has to be on the sphere");**/
        return point.subtract(center).normalize();
    }

    /**
     * find intersection points between ray and sphere
     * @param ray ray towards the object
     * @return immutable list containing 0/1/2 intersection points as {@link GeoPoint}s objects
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        // ray starts at center point of sphere
        // return point on surface in direction of ray
        if (P0.equals(center)) {
            return List.of( new GeoPoint(this,ray.getPoint(radius)));
        }

        // vector from ray origin to center point
        Vector U = center.subtract(P0);

        // tm = U's projection on ray's vector
        double tm = alignZero(v.dotProduct(U));
        // d between u and ray (at center point)
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        //distance from center to ray is larger than the radius
        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        // th = distance from ray intersection point to point
        // reached by scaling ray vector by tm
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        // t1 = size of distance from first intersection point to
        //  point reached by ray scaled by tm
        double t1 = alignZero(tm - th);
        // t2 = size of distance from point reached by ray scaled by tm
        // to second intersection point
        double t2 = alignZero(tm + th);

        // check that distance from ray origin to intersection points
        // is smaller than max distance parameter set by user
        boolean distanceT1 = alignZero(t1-maxDistance) <= 0;
        boolean distanceT2 = alignZero(t2-maxDistance) <= 0;

        // ray constructed outside sphere
        // two intersection points
        if (t1 > 0 && t2 > 0 && distanceT1 && distanceT2) {
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this,P1), new GeoPoint (this,P2));
        }
        // ray constructed inside sphere and intersect in back direction
        if (t1 > 0 && distanceT1) {
            Point P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(this,P1));
        }
        // ray constructed inside sphere and intersect in forward direction
        if (t2 > 0 && distanceT2) {
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint (this,P2));
        }
        // no intersection points found - assurance return
        // code should not be reaching this point
        return null;

    }
}