/**
 * An interface representing an intersectable geometry in 3D space
 */
package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {

    /**
     * Finds a list of intersections between the geometry and a given ray
     *
     * @param  ray the ray to intersect with
     * @return     a list of points representing the intersections with the ray
     */
    List<Point> findIntsersections(Ray ray);

}
