/**

*Sphere class Represents a sphere in 3D space with a given radius and center point.
 *@author: Avigail Tenenbaum and Noa Landman
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
     * Finds the intersection points between the given ray and the sphere.
     *
     * @param ray the ray to find intersections with
     * @return a list of intersection points, or null if there are no intersections
     */

    @Override
    public List<Point> findIntsersections(Ray ray) {
        if (ray.getP0().equals(center))
            return List.of(ray.getPoint(radius));
        Vector v= center.subtract(ray.getP0());
        double d1=alignZero (ray.getDir().dotProduct(v));
        double d2= alignZero(Math.sqrt(v.lengthSquared()-d1*d1));
        if(d2>=radius||isZero(d2-radius))
            return null;
        if (d2 >= radius || isZero(d2 - radius))
            return null;
        double d =alignZero( Math.sqrt(radius * radius - d2 * d2));
        if (d1 - d > 0 && d1 + d > 0)
            return List.of(ray.getP0().add(ray.getDir().scale(d1 - d))
                    , ray.getP0().add(ray.getDir().scale(d1 + d)));
        if (d1 - d > 0 && !(d1 + d > 0))
            return List.of(ray.getP0().add(ray.getDir().scale(d1 - d)));
        if (!(d1 - d > 0) && d1 + d > 0)
            return List.of(ray.getP0().add(ray.getDir().scale(d1 + d)));
        return null;    }
}