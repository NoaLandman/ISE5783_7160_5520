/**

 *Cylinder class Represents a cylinder in 3D space, which is a type of tube with a specific height.
* @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
            Point firstBaseCenter=axisRay.getP0();
            boolean isOnFirstBaseCenter=false;
            Vector vecFirstBase=new Vector (1,1,1);
            if(point.equals(firstBaseCenter))
                isOnFirstBaseCenter=true;
            else
                vecFirstBase=point.subtract(firstBaseCenter);

            Point secondBaseCenter=axisRay.getP0().add(axisRay.getDir().scale(height));
            boolean isOnSecondBaseCenter=false;
            Vector vecSecondBase=new Vector (1,1,1);
            if(point.equals(secondBaseCenter))
                isOnSecondBaseCenter=true;
            else
                vecSecondBase=point.subtract(secondBaseCenter);


            //if the point is on the first base (and as decided - not on the border with the cylinder's side):
            if(isOnFirstBaseCenter||(isZero(vecFirstBase.dotProduct(axisRay.getDir()))&&vecFirstBase.length()<this.radius))
                return axisRay.getDir().normalize();
                //if the point is on the second base (and as decided - not on the border with the cylinder's side):
            else if(isOnSecondBaseCenter||(isZero(vecSecondBase.dotProduct(axisRay.getDir()))&&vecSecondBase.length()<this.radius))
                return axisRay.getDir().normalize();
                //if the point is on the cylinder's side:
            else
                return super.getNormal(point);
    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}

