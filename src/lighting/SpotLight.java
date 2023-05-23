package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**

 The SpotLight class represents a spotlight light source.

 A spotlight emits light in a specific direction from a position in space,

 creating a cone of light.

 The intensity of the light decreases with distance from the light source,

 and also depends on the angle between the light direction and the direction to the point.
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**

     Constructs a SpotLight object with the specified intensity, position, and direction.
     @param intensity The intensity (color and brightness) of the light.
     @param position The position of the light source.
     @param direction The direction in which the light is emitted.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    /**

     Returns the intensity of the spotlight at the given point.
     The intensity is calculated based on the direction of the spotlight,
     the direction from the light source to the point,
     and the intensity of the point light at the given point.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point p) {
        double proj = direction.dotProduct(getL(p)); //direction*(psition-p) , projection of light on point
        //if the light source doesn't hit the point return color black
        if (isZero(proj))
            return Color.BLACK;

        double factor = Math.max(0, proj);
        Color i0 = super.getIntensity(p);

        return i0.scale(factor);
    }
    /**

     Returns the direction from the light source to the given point.
     @param p The point for which to calculate the direction.
     @return The direction vector.
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}