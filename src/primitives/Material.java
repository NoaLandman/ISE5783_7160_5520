package primitives;

/**

 The Material class represents the properties of a material in a scene.

 It defines the diffuse reflection coefficient (kD), specular reflection coefficient (kS),

 and the shininess (nshininess) of the material.
 */
public class Material {

    public Double3 kD = new Double3(0, 0, 0);
    public Double3 kS = new Double3(0, 0, 0);
    public int nshininess = 0;

    /**

     Sets the diffuse reflection coefficient (kD) of the material.
     @param kD The diffuse reflection coefficient.
     @return This Material object for method chaining.
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    /**

     Sets the specular reflection coefficient (kS) of the material.
     @param kS The specular reflection coefficient.
     @return This Material object for method chaining.
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**

     Sets the shininess (nshininess) of the material.
     @param nshininess The shininess.
     @return This Material object for method chaining.
     */
    public Material setNshininess(int nshininess) {
        this.nshininess = nshininess;
        return this;
    }
    /**

     Sets the diffuse reflection coefficient (kD) of the material.
     @param kD The diffuse reflection coefficient.
     @return This Material object for method chaining.
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**

     Sets the specular reflection coefficient (kS) of the material.
     @param kS The specular reflection coefficient.
     @return This Material object for method chaining.
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
}
