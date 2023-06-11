/**
 * The Triangle class represents a polygon with three vertices.
 * It is a subclass of the Polygon class.
 *
 * @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * @author Avigail and Noa
 */

public class Triangle extends Polygon {

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

    /**
     * Finds the intersection points between the triangle and a given ray.
     *
     * @param ray the ray to find the intersection points with
     * @return a list of intersection points, or null if there are no intersections
     */

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> planePoints = plane.findGeoIntersections(ray);

        if (planePoints == null) {
            return null;
        }


        List<Point> l = vertices;
        Point p0 = ray.getP0();

        Vector v1 = l.get(0).subtract(p0);
        Vector v2 = l.get(1).subtract(p0);
        Vector v3 = l.get(2).subtract(p0);

        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();

        double num1 = alignZero(n1.dotProduct(ray.getDir()));
        double num2 = alignZero(n2.dotProduct(ray.getDir()));
        double num3 = alignZero(n3.dotProduct(ray.getDir()));

        // if there is an intersection point inside the triangle
        if ((num1 > 0 && num2 > 0 && num3 > 0) || (num1 < 0 && num2 < 0 && num3 < 0)) {
            return List.of(new GeoPoint(this, planePoints.get(0).point));
        }

        return null; // there isn't an intersection point inside the triangle    }
    }
}
