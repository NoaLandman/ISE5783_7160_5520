package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Cylinder class to represent cylinder defined by tube (ray and radius) and also with height
 */

public class Cylinder extends Tube {

    private final double _height;


    /**
     * Constructor toto initialize cylinder
     * @param axisRay the ray
     * @param radius the radius of cylinder
     * @param height the height of cylinder
     */
    public Cylinder(double radius,Ray axisRay, double height) {
        super(radius,axisRay);
        _height = height;
    }

    /**
     * Get the height of the cylinder
     *
     * @return The height of the cylinder (double)
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + axisRay +
                ", _radius=" + radius +
                '}';
    }

    /**
     * Return the normal vector of the cylinder
     *
     * @param p The point to measure the normal (Point)
     * @return The normal of cylinder (Vector)
     */
    @Override
    public Vector getNormal(Point p) {
        Point o = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();
    }


    /**
     * find intersection points between ray and 3D cylinder
     * @param ray ray towards the sphere
     * @return immutable list containing 0/1/2 intersection points as {@link GeoPoint}s objects
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray ,double maxDistance) {
        // origin point of cylinder (on bottom base)
        Point basePoint=axisRay.getP0();
        // point across base point on top base
        Point topPoint =axisRay.getPoint(_height);
        // direction vector of cylinder (orthogonal to base point)
        Vector vC=axisRay.getDir();

        // find intersection points of ray with bottom base of cylinder
        List<GeoPoint> result= new LinkedList<>();
        // crate plane that contains base point in it
        Plane basePlane= new Plane(basePoint,vC);
        // find intersection between ray and plane
        List<GeoPoint> intersectionsBase=basePlane.findGeoIntersections(ray,maxDistance);

        // if intersections were found, check that point are actually on the base of the cylinder
        //if distance from base point to intersection point holds the equation ->  distance² < from radius²
        if(intersectionsBase!=null){
            for (GeoPoint p:intersectionsBase) {
                Point pt = p.point;
                // intersection point is the base point itself
                if(pt.equals(basePoint))
                    result.add(new GeoPoint(this,basePoint));
                    // intersection point is different to base point but is on the bottom base
                else if(pt.subtract(basePoint).dotProduct(pt.subtract(basePoint))<radius*radius)
                    result.add(new GeoPoint(this,pt));
            }
        }

        // find intersection points of ray with bottom base of cylinder
        // crate plane that contains top point in it
        Plane topPlane= new Plane(topPoint,vC);
        // find intersection between ray and plane
        List<GeoPoint> intersectionsTop=topPlane.findGeoIntersections(ray,maxDistance);
        // if intersections were found, check that point are actually on the base of the cylinder
        //if distance from top point to intersection point holds the equation ->  distance² < from radius²
        if(intersectionsTop!=null){
            for (var p:intersectionsTop) {
                Point pt = p.point;
                // intersection point is the top point itself
                if(pt.equals(topPoint))
                    result.add(new GeoPoint(this,topPoint));
                    // intersection point is different to base point but is on the bottom base
                else if(pt.subtract(topPoint).dotProduct(pt.subtract(topPoint))<radius*radius)
                    result.add(new GeoPoint(this,pt));
            }
        }

        // if rsy intersects both bases , no other intersections possible - return the result list
        if (result.size()==2)
            return result;

        // use tube parent class function to find intersections with the cylinder represented
        // as an infinite tube
        List<GeoPoint> intersectionsTube=super.findGeoIntersectionsHelper(ray ,maxDistance);

        // if intersection points were found check that they are within the finite cylinder's boundary
        // by checking if  scalar product fo direction vector with a vector from intersection point
        // to bottom base point is positive, and scalar product of direction vector with a
        // vector from intersection point to top base point is negative
        if(intersectionsTube!=null){
            for (var p:intersectionsTube){
                Point pt = p.point;
                if(vC.dotProduct(pt.subtract(basePoint))>0 && vC.dotProduct(pt.subtract(topPoint))<0)
                    result.add(new GeoPoint(this,pt));
            }
        }

        // return an immutable list
        int len = result.size();
        if(len>0)
            if (len ==1)
                return List.of(result.get(0));
            else
                return List.of(result.get(0), result.get(1));

        // no intersections
        return null;
    }
}