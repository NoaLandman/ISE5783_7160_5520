/**

 A plane geometry defined by a point and a normal vector or three points
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    /* the reference point of the plane*/
    private final Point p0;

    /* the normal vector of the plane*/
    private final Vector normal;

    /**
     * Constructs a plane using a reference point and a normal vector.
     * @param p0 the reference point of the plane
     * @param normal the normal vector of the plane
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a plane using three points.
     * @param p1 first point in the plane
     * @param p2 second point in the plane
     * @param p3 third point in the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.p0 = p1;
        this.normal = null; // not implemented yet
    }

    /**
     * Returns the reference point of the plane.
     * @return the reference point of the plane
     */
    public Point getP0() {
        return p0;
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
        return normal;
    }
}


