/**

 The Vector class represents a mathematical vector in 3D space.
 It extends the Point class.
 @author: Avigail Tenenbaum and Noa Landman
 @version 1.0
 */
package primitives;
/** @author Avigail and Noa */
public class Vector extends Point {

    /**
     * Constructs a Vector object with the specified x, y, and z coordinates.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     * @throws IllegalArgumentException if the vector is equal to ZERO
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector can not be ZERO");
    }

    /**
     * Constructs a Vector object with the specified Double3 object.
     *
     * @param double3 the Double3 object containing the x, y, and z coordinates of the vector
     */
     Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Checks if this Vector object is equal to another object.
     *
     * @param o the object to compare this vector to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    /**
     * Returns the length of this Vector object.
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the squared length of this Vector object.
     *
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Returns a new normalized Vector object with the same direction as this Vector object.
     *
     * @return a new normalized vector
     */
    public Vector normalize() {
        double length=length();
        return new Vector(xyz.reduce(length));
    }

    /**
     * Computes the dot product of this Vector object and the specified Vector object.
     *
     * @param u the vector to compute the dot product with
     * @return the dot product of the two vectors
     */
    public double dotProduct(Vector u) {
        return this.xyz.d1*u.xyz.d1+
                this.xyz.d2*u.xyz.d2+
                this.xyz.d3*u.xyz.d3;
    }

    /**
     * Computes the cross product of this Vector object and the specified Vector object.
     *
     * @param u the vector to compute the cross product with
     * @return a new Vector object that is the cross product of the two vectors
     */
    public Vector crossProduct(Vector u) {
        double x=this.xyz.d2*u.xyz.d3-this.xyz.d3*u.xyz.d2;
        double y=(-1)*(this.xyz.d1*u.xyz.d3-this.xyz.d3*u.xyz.d1);
        double z=this.xyz.d1*u.xyz.d2-this.xyz.d2*u.xyz.d1;
        return new Vector(x,y,z);
    }

    /**
     * Computes the sum of this Vector object and the specified Vector object.
     *
     * @param vector the vector to add to this vector
     * @return a new Vector object that is the sum of the two vectors
     */
    public Vector add(Vector vector) {
        return new Vector((super.add(vector)).xyz);
    }

    /**
     * 
     * @param  scalar in which the vector is multiplied
     * @return a new vector multiplied by scalar
     */
    public Vector scale(double scalar){
        return new Vector((super.xyz.scale(scalar)));
    }
}


