/**

 Represents a cylinder in 3D space, which is a type of tube with a specific height.
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    /**
     * The height of the cylinder.
     */
    private double height;

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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}

