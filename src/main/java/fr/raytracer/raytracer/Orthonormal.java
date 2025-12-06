
package fr.raytracer.raytracer;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

/**
 * Représente une base orthonormée pour la caméra.
 */
public class Orthonormal {
    /** Vecteur u (droite). */
    private Vector u;
    /** Vecteur v (haut). */
    private Vector v;
    /** Vecteur w (arrière). */
    private Vector w;

    /**
     * Crée une base orthonormée à partir de la position de la caméra.
     * @param lookFrom position de la caméra
     * @param lookAt point visé
     * @param up vecteur "haut"
     */
    public Orthonormal(Point lookFrom, Point lookAt, Vector up) {
        this.w = lookFrom.subtract(lookAt).normalize();
        this.u = up.cross(w).normalize();
        this.v = w.cross(u).normalize();
    }

    /**
     * Retourne le vecteur u (droite).
     * @return u
     */
    public Vector getU() { return u; }

    /**
     * Retourne le vecteur v (haut).
     * @return v
     */
    public Vector getV() { return v; }

    /**
     * Retourne le vecteur w (arrière).
     * @return w
     */
    public Vector getW() { return w; }
}
