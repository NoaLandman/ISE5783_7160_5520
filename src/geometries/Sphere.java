/**

*Sphere class Represents a sphere in 3D space with a given radius and center point.
 *@author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Vector;

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
        return null;
    }
}