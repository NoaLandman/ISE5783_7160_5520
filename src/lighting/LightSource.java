package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**

 The LightSource interface represents a light source in a scene.

 It provides methods to calculate the intensity of the light at a given point,

 and the direction from the light source to the given point.
 */
public interface LightSource {

    /**

     Returns the intensity of the light at the given point.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    public Color getIntensity(Point p);
    /**

     Returns the direction from the light source to the given point.
     @param p The point for which to calculate the direction.
     @return The direction vector.
     */
    public Vector getL(Point p);
}