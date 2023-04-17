/**

 *The Tube class represents a tube-shaped 3D geometrical object.
 *It extends the RadialGeometry class and includes an axis ray for the tube.
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Tube extends RadialGeometry{

    /**
     * The axis ray of the tube..
     */
    protected final Ray axisRay;

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
       /** Vector dir = axisRay.getDir();
        Point p0 = axisRay.getP0();

        var t = dir.dotProduct(point.subtract(p0));
        if (Util.isZero(t))
            return point.subtract(p0).normalize();
        var o = p0.add(dir.scale(t));
        return point.subtract(o).normalize();**/
        Vector dir = axisRay.getDir();
        Point p0 = axisRay.getP0();

        var t = dir.dotProduct(point.subtract(p0));
        if (Util.isZero(t))
            return point.subtract(p0).normalize();
        var o = p0.add(dir.scale(t));
        return point.subtract(o).normalize();
    }
}