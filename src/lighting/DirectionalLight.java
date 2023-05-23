package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**

 The DirectionalLight class represents a directional light source.

 A directional light emits light uniformly in a specific direction,

 as if it is infinitely far away and the light rays are parallel.

 It provides consistent illumination and is commonly used to simulate sunlight.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**

     Constructs a DirectionalLight object with the specified intensity and direction.
     @param intensity The intensity (color and brightness) of the light.
     @param direction The direction in which the light is emitted.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }
    /**

     Returns the intensity of the directional light at the given point.
     Since a directional light emits light uniformly in a specific direction,
     the intensity is the same at all points.
     @param p The point at which to calculate the intensity.
     @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }
    /**

     Returns the direction from the light source to the given point.
     Since a directional light emits light uniformly in a specific direction,
     the direction is the same for all points.
     @param p The point for which to calculate the direction.
     @return The direction vector.
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
