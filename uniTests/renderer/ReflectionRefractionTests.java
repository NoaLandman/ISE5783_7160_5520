/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

import javax.swing.*;
import java.util.List;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene         scene      = new Scene.SceneBuilder("Test scene").build();

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(50d,new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
                           new Sphere(25d,new Point(0, 0, -50)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracerBase(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      Scene alternateScene = new Scene.SceneBuilder("Alternate Test scene")
              .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1))

              .setGeometries(new Geometries( //
                           new Sphere(400d,new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setkT(new Double3(0.5, 0, 0))),
                           new Sphere(200d,new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setkR(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4)))))
           .setLights(List.of(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005))).build();

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracerBase(new RayTracerBasic(alternateScene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      Scene alternateScene = new Scene.SceneBuilder("Alternate Test scene")
              .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15))

              .setGeometries(new Geometries( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(30d,new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)))).

      setLights(List.of(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7))).build();

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracerBase(new RayTracerBasic(alternateScene)) //
         .renderImage() //
         .writeToImage();
   }

   @Test
   public void myTest() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1),
              new Vector(0, 1, 0)).setVPSize(200, 200).setVPDistance(1000);

      Scene alternateScene = new Scene.SceneBuilder("Alternate Test scene")
              .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15))

              .setGeometries(new Geometries(
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                      new Point(75, 75, -150)).setMaterial(
                      new Material().setKd(0.5).setKs(0.5).setShininess(60).setkR(0.09)),
              new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140),
                      new Point(75, 75, -150)).setMaterial(
                      new Material().setKd(0.5).setKs(0.5).setShininess(60).setkR(0.09)),
              new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(RED)).setMaterial(
                      new Material().setKd(0.2).setKs(0.2).setShininess(30).setkR(0.5)),
              new Sphere(40d, new Point(-40, -30, -80)).setEmission(new Color(GREEN)).setMaterial(
                      new Material().setKd(0.4).setKs(0.4).setShininess(50).setkT(0.8)),
              new Sphere(20d, new Point(-40, -30, -80)).setEmission(new Color(YELLOW)).setMaterial(
                      new Material().setKd(0.2).setKs(0.2).setShininess(10).setkR(0.8))
      ))

              .setLights(List.of(new SpotLight(new Color(400, 400, 400), new Point(-40, -30, 0),
              new Vector(0, 0, -1)).setKl(0.0005).setKq(0.0005),
      new SpotLight(new Color(800, 800, 800), new Point(60, 50, 0),
              new Vector(0, 0, -1)).setKl(0.0005).setKq(0.0005),
     new DirectionalLight(new Color(100, 50, 100), new Vector(-1, -1, -1)))).build();

      ImageWriter imageWriter = new ImageWriter("myTestTrianglesAndSpheres", 600, 600);
      camera.setImageWriter(imageWriter)
              .setRayTracerBase(new RayTracerBasic(alternateScene))
              .renderImage()
              .writeToImage();
   }
}
