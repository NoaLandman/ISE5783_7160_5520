//package renderer;
//
//import geometries.Cylinder;
//import geometries.Geometries;
//import geometries.Plane;
//import geometries.Sphere;
//import lighting.AmbientLight;
//import lighting.SpotLight;
//import org.junit.jupiter.api.Test;
//import primitives.*;
//import scene.Scene;
//
//import java.util.List;
//
//import static java.awt.Color.PINK;
//
//public class SoftShadowTests {
//    @Test
//    void soft() {
//        Scene scene = new Scene.SceneBuilder("Test scene").
//                setBackground(new Color(java.awt.Color.BLACK)).
//                setAmbientLight(new AmbientLight(Color.BLACK, new Double3(0)))
//                .setGeometries(new Geometries(
//                        new Cylinder(4,new Ray(new Point(10, -40, 0), new Vector(0, 0, 1)), 20)
//                                .setEmission(new Color(PINK).reduce(2)) //
//                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
//                        new Plane(new Point(0, 0, 0), new Vector(0,0,1))
//                                .setEmission(new Color(java.awt.Color.BLUE))
//                                .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3).setkR(0))))
//
//                .setLights(List.of(new SpotLight(new Color(1000, 600, 0), new Point(-30, 60, 200), new Vector(1, -1, -2))
//                        .setkC(1).setKl(0.0004).setKq(0.0000006))).build();
//        Camera camera = new Camera(new Point(-100, -100, 20), new Vector(1, 1, 0), new Vector(0, 0, 1)).
//                setVPSize(200, 200).setVPDistance(100) //
//                .setRayTracerBase(new RayTracerBasic(scene));
//
//        camera.setImageWriter(new ImageWriter("withsoftShadow", 500, 500)) //
//                //.setSoftShadow(true)
//                .renderImage() //
//                .writeToImage();Scene scen = new Scene.SceneBuilder("Test scene").
//                setBackground(new Color(java.awt.Color.BLACK)).
//                setAmbientLight(new AmbientLight(Color.BLACK, new Double3(0))).build();
//        Camera camera1 = new Camera(new Point(-100, -100, 20), new Vector(1, 1, 0), new Vector(0, 0, 1)).
//                setVPSize(200, 200).setVPDistance(100) //
//                .setRayTracerBase(new RayTracerBasic(scen));
//        scen.geometries.add(
//                new Sphere(30,new Point(0, 0, 30))
//                        .setEmission(new Color(java.awt.Color.MAGENTA))
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3).setkR(0)),
//                new Plane(new Point(0, 0, 0), new Vector(0,0,1))
//                        .setEmission(new Color(java.awt.Color.BLUE))
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3).setkR(0)));
//
//        scen.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-60, 60, 200), new Vector(1, -1, -2))
//                .setkC(1).setKl(0.0004).setKq(0.0000006));
//
//        camera1.setImageWriter(new ImageWriter("softShadow", 500, 500)) //
//                .renderImage() //
//                .writeToImage();
//    }
//
//}
