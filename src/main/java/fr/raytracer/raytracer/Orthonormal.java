
package fr.raytracer.raytracer;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

public class Orthonormal {
    private Vector u, v, w;

    public Orthonormal(Point lookFrom, Point lookAt, Vector up) {
        this.w = lookFrom.subtract(lookAt).normalize();
        this.u = up.cross(w).normalize();
        this.v = w.cross(u).normalize();
    }

    public Vector getU() { return u; }
    public Vector getV() { return v; }
    public Vector getW() { return w; }
}
