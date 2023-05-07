/**

 *Cylinder class Represents a cylinder in 3D space, which is a type of tube with a specific height.
* @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/** @author Avigail and Noa */
public class Cylinder extends Tube{

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

    @Override
    public Vector getNormal(Point point) {
        Point o = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(point.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return point.subtract(o).normalize();

    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}

