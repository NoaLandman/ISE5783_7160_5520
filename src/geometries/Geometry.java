/**

 The Geometry interface represents a geometric shape in three-dimensional space.
 It provides a method for getting the normal vector of the shape at a given point.
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;
import primitives.Vector;
/** @author Avigail and Noa */
public interface Geometry extends Intersectable{


    /**
     * Returns the normal vector of the shape at the specified point.
     *
     * @param point The point at which to get the normal vector.
     * @return The normal vector of the shape at the specified point.
     */
    public abstract Vector getNormal(Point point);
}

