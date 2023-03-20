/**

 The Triangle class represents a polygon with three vertices.
 It is a subclass of the Polygon class.
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Point;

public class Triangle extends Polygon{

    /**
     * Constructs a Triangle object with the specified points as vertices.
     *
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
}