package fr.raytracer.parsing;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Sphere;
import fr.raytracer.geometry.Triangle;
import fr.raytracer.imaging.Color;
import fr.raytracer.lighting.DirectionalLight;
import fr.raytracer.lighting.PointLight;
import fr.raytracer.raytracer.Camera;
import fr.raytracer.raytracer.Scene;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse un fichier de scène et construit un objet Scene.
 */
public class SceneFileParser {
    /** Liste des vertices lus. */
    private List<Point> vertices = new ArrayList<>();
    /** Couleur diffuse courante. */
    private Color currentDiffuse = new Color(0, 0, 0);
    /** Couleur spéculaire courante. */
    private Color currentSpecular = new Color(0, 0, 0);
    /** Brillance courante. */
    private double currentShininess = 1.0;

    /**
     * Parse un fichier de scène.
     * @param filename le chemin du fichier
     * @return la scène construite
     * @throws IOException en cas d'erreur de lecture
     */
    public Scene parse(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        
        Scene scene = null;
        
        for (String line : lines) {
            line = line.trim();
            
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            
            String[] parts = line.split("\\s+");
            String command = parts[0];
            
            switch (command) {
                case "size":
                    int width = Integer.parseInt(parts[1]);
                    int height = Integer.parseInt(parts[2]);
                    scene = new Scene(width, height, null);
                    break;
                    
                case "output":
                    if (scene != null) {
                        scene.setOutput(parts[1]);
                    }
                    break;
                    
                case "camera":
                    Camera camera = parseCamera(parts);
                    if (scene != null) {
                        scene = new Scene(scene.getWidth(), scene.getHeight(), camera);
                    }
                    break;
                    
                case "ambient":
                    Color ambient = new Color(
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3])
                    );
                    if (scene != null) {
                        scene.setAmbient(ambient);
                    }
                    break;
                    
                case "diffuse":
                    currentDiffuse = new Color(
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3])
                    );
                    break;
                    
                case "specular":
                    currentSpecular = new Color(
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3])
                    );
                    break;
                    
                case "shininess":
                    currentShininess = Double.parseDouble(parts[1]);
                    break;
                    
                case "directional":
                    DirectionalLight dirLight = new DirectionalLight(
                        new fr.raytracer.geometry.Vector(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                        ),
                        new Color(
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]),
                            Double.parseDouble(parts[6])
                        )
                    );
                    if (scene != null) {
                        scene.addLight(dirLight);
                    }
                    break;
                    
                case "point":
                    PointLight pointLight = new PointLight(
                        new Point(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                        ),
                        new Color(
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]),
                            Double.parseDouble(parts[6])
                        )
                    );
                    if (scene != null) {
                        scene.addLight(pointLight);
                    }
                    break;
                    
                case "maxverts":
                    // Initialiser la liste des vertices
                    break;
                    
                case "vertex":
                    vertices.add(new Point(
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3])
                    ));
                    break;
                    
                case "sphere":
                    Sphere sphere = new Sphere(
                        new Point(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                        ),
                        Double.parseDouble(parts[4]),
                        currentDiffuse,
                        currentSpecular,
                        currentShininess
                    );
                    if (scene != null) {
                        scene.addShape(sphere);
                    }
                    break;
                    
                case "tri":
                    // Créer un triangle à partir des indices de vertices
                    int idx0 = Integer.parseInt(parts[1]);
                    int idx1 = Integer.parseInt(parts[2]);
                    int idx2 = Integer.parseInt(parts[3]);
                    
                    if (idx0 < vertices.size() && idx1 < vertices.size() && idx2 < vertices.size()) {
                        Triangle triangle = new Triangle(
                            vertices.get(idx0),
                            vertices.get(idx1),
                            vertices.get(idx2),
                            currentDiffuse,
                            currentSpecular,
                            currentShininess
                        );
                        if (scene != null) {
                            scene.addShape(triangle);
                        }
                    }
                    break;
                    
                case "plane":
                    // Plane implementation (bonus)
                    break;
                    
                case "maxdepth":
                    // Handled in main
                    break;
            }
        }
        
        return scene;
    }
    
    /**
     * Parse les paramètres de la caméra.
     * @param parts les parties de la ligne
     * @return la caméra configurée
     */
    private Camera parseCamera(String[] parts) {
        Point lookFrom = new Point(
            Double.parseDouble(parts[1]),
            Double.parseDouble(parts[2]),
            Double.parseDouble(parts[3])
        );
        
        Point lookAt = new Point(
            Double.parseDouble(parts[4]),
            Double.parseDouble(parts[5]),
            Double.parseDouble(parts[6])
        );
        
        fr.raytracer.geometry.Vector up = new fr.raytracer.geometry.Vector(
            Double.parseDouble(parts[7]),
            Double.parseDouble(parts[8]),
            Double.parseDouble(parts[9])
        );
        
        double fov = Double.parseDouble(parts[10]);
        
        return new Camera(lookFrom, lookAt, up, fov);
    }
}