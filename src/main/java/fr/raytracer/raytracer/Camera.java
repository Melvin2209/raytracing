
package fr.raytracer.raytracer;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

public class Camera {
    private Point lookFrom;
    private Point lookAt;
    private Vector up;
    private double fov;
    private Orthonormal orthonormal;

    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
        this.orthonormal = new Orthonormal(lookFrom, lookAt, up);
    }

    public Point getLookFrom() { return lookFrom; }
    public Point getLookAt() { return lookAt; }
    public Vector getUp() { return up; }
    public double getFov() { return fov; }
    public Orthonormal getOrthonormal() { return orthonormal; }
}