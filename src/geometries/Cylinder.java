/**
 * Cylinder class Represents a cylinder in 3D space, which is a type of tube with a specific height.
 *
 * @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/** @author Avigail and Noa */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    private final double height;

    /**
     * Constructs a new cylinder object with the specified radius, axis ray, and height.
     *
     * @param radius   The radius of the cylinder.
     * @param axisRay  The axis ray of the cylinder.
     * @param height   The height of the cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the normal vector to the surface of the cylinder at the given point.
     *
     * @param point the point on the cylinder for which to return the normal vector
     * @return the normal vector to the surface of the cylinder at the given point
     */

    /**
     * Return the normal vector of the cylinder
     *
     * @param point The point to measure the normal (Point)
     * @return The normal of cylinder (Vector)
     */
    @Override
    public Vector getNormal(Point point) {

        //Define the center of cylinder's sides.
        Vector cylinderCenterVector = axisRay.getDir();

        Point centerOfOneSide = axisRay.getP0();
        Point centerOfSecondSide = axisRay.getP0().add(axisRay.getDir().scale(height));

        //The normal at a base will be simply equal to central ray's
        //direction vector v or opposite to it (-v) so we check it
        if (point.equals(centerOfOneSide)) {
            return cylinderCenterVector.scale(-1);
        } else if (point.equals(centerOfSecondSide)) {
            return cylinderCenterVector;
        }

        //If the point on one of the cylinder's bases, but it's not the center point
        double projection = cylinderCenterVector.dotProduct(point.subtract(centerOfOneSide));
        if (isZero(projection)) {
            Vector v1 = point.subtract(centerOfOneSide);
            return v1.normalize();
        }

        //If the point on the side of the cylinder.
        Point center = centerOfOneSide.add(cylinderCenterVector.scale(projection));
        Vector v = point.subtract(center);

        return v.normalize();
    }

    /**
     * Find the intersection point of the ray and the cylinder
     *
     * @param ray The ray
     * @return List of intersection GeoPoint between the cylinder and the ray
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = new LinkedList<>();
        Vector va = axisRay.getDir();
        Point p1 = axisRay.getP0();
        Point p2 = p1.add(axisRay.getDir().scale(height));

        Plane plane1 = new Plane(p1, axisRay.getDir()); //get plane of bottom base
        List<GeoPoint> result2 = plane1.findGeoIntersections(ray); //intersections with bottom's plane
        if (result2 != null){
            //Add all intersections of bottom's plane that are in the base's bounders
            for (GeoPoint point : result2) {
                if (point.point.equals(p1)){ //to avoid vector ZERO
                    result.add(new GeoPoint(this, point.point));
                }
                //Formula that checks that point is inside the base
                else if ((point.point.subtract(p1).dotProduct(point.point.subtract(p1)) < radius * radius)){
                    result.add(new GeoPoint(this, point.point));
                }
            }
        }

        List<GeoPoint> result1 = super.findGeoIntersectionsHelper(ray); //get intersections for tube

        if (result1 != null){
            //Add all intersections of tube that are in the cylinder's bounders
            for (GeoPoint point:result1) {
                if (va.dotProduct(point.point.subtract(p1)) > 0 && va.dotProduct(point.point.subtract(p2)) < 0){
                    result.add(new GeoPoint(this, point.point));
                }
            }
        }


        Plane plane2 = new Plane(p2, this.axisRay.getDir()); //get plane of top base
        List<GeoPoint> result3 = plane2.findGeoIntersections(ray); //intersections with top's plane

        if (result3 != null){
            for (GeoPoint point : result3) {
                if (point.point.equals(p2)){ //to avoid vector ZERO
                    result.add(new GeoPoint(this, point.point));
                }
                //Formula that checks that point is inside the base
                else if ((point.point.subtract(p2).dotProduct(point.point.subtract(p2)) <radius * radius)){
                    result.add(new GeoPoint(this, point.point));
                }
            }
        }

        if (result.size() > 0)
            return result;

        return null;
    }

}

