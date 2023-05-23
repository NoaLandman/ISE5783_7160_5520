package lighting;

import primitives.Color;
import primitives.Double3;

/**

 The AmbientLight class represents an ambient light source.

 Ambient light is a uniform light that illuminates all objects in the scene equally,

 regardless of their position or orientation.

 It contributes to the overall illumination of the scene and provides a base level of brightness.
 */
public class AmbientLight extends Light {

    /**

     A constant representing no ambient light (black color with zero intensity).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
    /**

     Constructs an AmbientLight object with the specified ambient color and attenuation factor.
     @param Ia The ambient color of the light.
     @param Ka The attenuation factor of the light.
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
    /**

     Constructs an AmbientLight object with the specified ambient color and attenuation factor.
     The attenuation factor is applied uniformly to all color channels.
     @param Ia The ambient color of the light.
     @param Ka The attenuation factor of the light.
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
}