/**

 Class represents a directional light source.
 It extends the Light class and implements the LightSource interface.
 */
package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**

 The DirectionalLight class represents a directional light source in a scene.

 It extends the Light class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource {
    // Field represents the direction of the light
    private Vector direction;

    /**

     Constructor for creating a DirectionalLight object.
     @param intensity The intensity of the light.
     @param direction The direction of the light source.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }
    /**

     Calculates and returns the intensity of the light at the given point.
     The intensity of a directional light is the same at every point.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }
    /**

     Calculates and returns the direction from the light source to the given point.
     For a directional light, the direction is constant and does not depend on the point.
     @param p The point for which to calculate the direction.
     @return The direction vector.
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }
    /**

     Returns the distance from the light source to the given point.
     Since a directional light has an infinite distance, it returns Double.POSITIVE_INFINITY.
     @param point The point for which to calculate the distance.
     @return The distance to the point.
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
    /**

     Returns a list of vectors representing the direction from the light source to points on a circle.
     Since a directional light has a fixed direction, the list contains only one vector representing the light direction.
     @param p The center point of the circle (not used in this implementation).
     @param r The radius of the circle (not used in this implementation).
     @param amount The number of vectors to generate on the circle (not used in this implementation).
     @return The list of vectors representing the light direction.
     */
    @Override
    public List<Vector> getLCircle(Point p, double r, int amount) {
        return List.of(getL(p));
    }
}