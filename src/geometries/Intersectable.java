/**
 * An interface representing an intersectable geometry in 3D space
 */
package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**

 The Intersectable class represents an abstract class for objects that can be intersected by rays.
 */
public abstract class Intersectable {

    /**

     Represents a point of intersection between a ray and a geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**

         Constructs a GeoPoint object with the given geometry and point.
         @param geometry The intersected geometry.
         @param point The point of intersection.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        /**

         Checks if this GeoPoint is equal to another object.
         @param o The object to compare.
         @return {@code true} if the objects are equal, {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
        /**

         Computes the hash code of this GeoPoint.
         @return The hash code value.
         */
        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }
        /**

         Returns a string representation of this GeoPoint.
         @return The string representation.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
    /**

     Finds a list of intersections between the geometry and a given ray.
     @param ray The ray to intersect with.
     @return A list of points representing the intersections with the ray.
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    /**

     Finds a list of GeoPoints representing the intersections between the geometry and a given ray.
     This method is implemented in subclasses.
     @param ray The ray to intersect with.
     @return A list of GeoPoint objects representing the intersections with the ray.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }
    /**

     Finds a list of GeoPoints representing the intersections between the geometry and a given ray.
     This method should be implemented in subclasses.
     @param ray The ray to intersect with.
     @return A list of GeoPoint objects representing the intersections with the ray.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}