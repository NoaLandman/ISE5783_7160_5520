/**

 The Geometry interface represents a geometric shape in three-dimensional space.
 It provides a method for getting the normal vector of the shape at a given point.
 @author Avigail Tenenbaum and Noa Landman
 */
package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**

 The Geometry class is an abstract class that serves as the base for all geometric shapes in three-dimensional space.

 It extends the Intersectable class.

 @author Avigail and Noa
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    protected Material material = new Material();

    /**

     Returns the emission color of the geometry.
     @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }
    /**

     Sets the emission color of the geometry.
     @param emission The new emission color to be set.
     @return The geometry itself.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**

     Returns the material of the geometry.
     @return The material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }
    /**

     Sets the material of the geometry.
     @param material The new material to be set.
     @return The geometry itself.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
    /**

     Returns the normal vector of the shape at the specified point.
     @param point The point at which to get the normal vector.
     @return The normal vector of the shape at the specified point.
     */
    public abstract Vector getNormal(Point point);
}