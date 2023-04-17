/**

 A plane geometry defined by a point and a normal vector or three points
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

public class Plane implements Geometry {

    /* the reference point of the plane*/
    private final Point q0;

    /* the normal vector of the plane*/
    private final Vector normal;

    /**
     * Constructs a plane using a reference point and a normal vector.
     * @param q0 the reference point of the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        if(!(isZero(normal.length()-1d))){
            this.normal = normal.normalize();
        }
        else {this.normal = normal;
        }
    }

    /**
     * Constructs a plane using three points.
     * @param p1 first point in the plane
     * @param p2 second point in the plane
     * @param p3 third point in the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        if(p1.equals(p2)||p1.equals(p3)||p2.equals(p3))
            throw new IllegalArgumentException("two of the points are identical");


        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        if(U.normalize().equals(V.normalize()))
            throw new IllegalArgumentException("there is a linear dependents between the vectors");
        //right hand rule
        Vector N = U.crossProduct(V);
        normal = N.normalize();

    }

    /**
     * Returns the reference point of the plane.
     * @return the reference point of the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Returns the normal vector of the plane.
     * @return the normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }
}


