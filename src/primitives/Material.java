package primitives;

/**
 * The Material class represents the properties of a material in a scene.
 * <p>
 * It defines the diffuse reflection coefficient (kD), specular reflection coefficient (kS),
 * <p>
 * and the shininess (nshininess) of the material.
 */
public class Material {

    public Double3 kD = new Double3(0, 0, 0);
    public Double3 kS = new Double3(0, 0, 0);
    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;
    public int nshininess = 0;

    /**
     * Sets the diffuse reflection coefficient (kD) of the material.
     *
     * @param kD The diffuse reflection coefficient.
     * @return This Material object for method chaining.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material.
     *
     * @param kD The diffuse reflection coefficient.
     * @return This Material object for method chaining.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material.
     *
     * @param kS The specular reflection coefficient.
     * @return This Material object for method chaining.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the shininess (nshininess) of the material.
     *
     * @param nshininess The shininess.
     * @return This Material object for method chaining.
     */
    public Material setShininess(int nshininess) {
        this.nshininess = nshininess;
        return this;
    }

    /**
     * /**
     * <p>
     * Sets the specular reflection coefficient (kS) of the material.
     *
     * @param kS The specular reflection coefficient.
     * @return This Material object for method chaining.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}
