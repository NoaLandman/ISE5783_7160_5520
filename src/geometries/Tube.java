/**

 The Tube class represents a tube-shaped 3D geometrical object.
 It extends the RadialGeometry class and includes an axis ray for the tube.
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{

    /**
     * The axis ray of the tube.
     */
    protected Ray axisRay;

    /**
     * Constructs a Tube object with the specified radius and axis ray.
     * @param radius the radius of the tube
     * @param axisRay the axis ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axis ray of the tube.
     * @return the axis ray of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Computes and returns the normal vector to the given point on the tube's surface.
     * @param point a point on the surface of the tube
     * @return the normal vector to the given point on the tube's surface
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}