/**
 * The Geometry interface represents a geometric shape in three-dimensional space.
 * It provides a method for getting the normal vector of the shape at a given point.
 *
 * @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * @author Avigail and Noa
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    Material material = new Material();

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Returns the normal vector of the shape at the specified point.
     *
     * @param point The point at which to get the normal vector.
     * @return The normal vector of the shape at the specified point.
     */
    public abstract Vector getNormal(Point point);
}

