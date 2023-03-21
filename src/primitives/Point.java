/**

 Point represents a point in three-dimensional space.
 It is represented by a Double3 object which holds the x, y, and z coordinates.
 @author: Avigail Tenenbaum and Noa Landman
 */
package primitives;
import java.util.Objects;
public class Point {

     protected final Double3 xyz;
    /**
     * Constructs a new Point object with the specified x, y, and z coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point object with the same coordinates as the specified Double3 object.
     *
     * @param double3 the Double3 object representing the point's coordinates
     */
     Point(Double3 double3){
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Returns whether this Point object is equal to the specified object.
     *
     * @param o the object to compare to this Point
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    /**
     * Returns the hash code of this Point object.
     *
     * @return the hash code of this Point object
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * Returns a string representation of this Point object.
     *
     * @return a string representation of this Point object
     */
    @Override
    public String toString() {
        return "Point :" + xyz ;
    }

    /**
     * Returns the Euclidean distance between this Point and the specified Point.
     *
     * @param other the other Point to calculate the distance to
     * @return the Euclidean distance between this Point and the specified Point
     */
    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Returns the square of the Euclidean distance between this Point and the specified Point.
     *
     * @param other the other Point to calculate the squared distance to
     * @return the square of the Euclidean distance between this Point and the specified Point
     */
    public double distanceSquared(Point other){
        return (xyz.d1 - other.xyz.d1)*(xyz.d1 - other.xyz.d1)+
                (xyz.d2 - other.xyz.d2)*(xyz.d2 - other.xyz.d2)+
                (xyz.d3 - other.xyz.d3)*(xyz.d1 - other.xyz.d3);
    }

    /**
     * Returns a new Point object that is the result of adding the specified Vector to this Point.
     *
     * @param vector the Vector to add to this Point
     * @return a new Point object that is the result of adding the specified Vector to this Point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Returns the Vector that represents the difference between this Point and the specified Point.
     *
     * @param point the Point to subtract from this Point
     * @return the Vector that represents the difference between this Point and the specified Point
     */
    public Vector subtract(Point point) {
        return  new Vector(this.xyz.subtract(point.xyz));
    }

}











