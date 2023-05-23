package lighting;

import primitives.Color;

/**

 The Light class represents a light source in a scene.

 It provides the intensity (color and brightness) of the light.
 */
public abstract class Light {

    /**

     The intensity (color and brightness) of the light.
     */
    final Color intensity;
    /**

     Constructs a Light object with the specified intensity.
     @param intensity The intensity (color and brightness) of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }
    /**

     Returns the intensity of the light.
     @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
