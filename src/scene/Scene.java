package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public final Color background;
    public final AmbientLight ambientLight;
    public final Geometries geometries;
    public final List<LightSource> lights;
    private final String name;

    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        background = builder.background;
        geometries = builder.geometries;
        ambientLight = builder.ambientLight;
        lights = builder.lights;
    }

    public static class SceneBuilder {
        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = AmbientLight.NONE;
        private Geometries geometries = new Geometries();
        private List<LightSource> lights = new LinkedList<>();


        public SceneBuilder(String name) {
            this.name = name;
        }

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public Scene build() {
            return new Scene(this);
        }
    }
}
