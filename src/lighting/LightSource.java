/**

 The LightSource interface represents a light source in a scene.
 It provides methods to calculate the intensity of the light at a given point
 and the direction from the light source to the given point.
 */
package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

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
    /**

     Returns the distance from the light source to the given point.
     @param point The point for which to calculate the distance.
     @return The distance to the point.
     */
    public double getDistance(Point point);
    /**

     Returns a list of vectors representing the positions on a circle around a given point.
     @param p The center point of the circle.
     @param r The radius of the circle.
     @param amount The number of points to generate on the circle.
     credit to Dina for the code
     @return The list of vectors representing positions on the circle.
     credit to Dina for the code
     */
    public List<Vector> getLCircle(Point p, double r, int amount);
}