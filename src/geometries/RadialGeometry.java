/**

 The abstract RadialGeometry class represents a geometry with a radial component, such as a circle or a sphere.
 It implements the Geometry interface.
 @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;
public abstract class RadialGeometry implements Geometry {

    /**
     * The radius of the RadialGeometry object.
     */
    protected final double radius;

    /**
     * Constructs a new RadialGeometry object with the specified radius.
     * @param radius The radius of the new RadialGeometry object.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}