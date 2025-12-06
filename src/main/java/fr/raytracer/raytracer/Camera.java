
package fr.raytracer.raytracer;

import fr.raytracer.geometry.Point;
import fr.raytracer.geometry.Vector;

/**
 * Représente une caméra pour le raytracing.
 */
public class Camera {
    /** Position de la caméra. */
    private Point lookFrom;
    /** Point visé par la caméra. */
    private Point lookAt;
    /** Vecteur "haut" de la caméra. */
    private Vector up;
    /** Champ de vision en degrés. */
    private double fov;
    /** Base orthonormée de la caméra. */
    private Orthonormal orthonormal;

    /**
     * Crée une caméra.
     * @param lookFrom position de la caméra
     * @param lookAt point visé
     * @param up vecteur "haut"
     * @param fov champ de vision en degrés
     */
    public Camera(Point lookFrom, Point lookAt, Vector up, double fov) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
        this.orthonormal = new Orthonormal(lookFrom, lookAt, up);
    }

    /**
     * Retourne la position de la caméra.
     * @return la position
     */
    public Point getLookFrom() { return lookFrom; }

    /**
     * Retourne le point visé.
     * @return le point visé
     */
    public Point getLookAt() { return lookAt; }

    /**
     * Retourne le vecteur "haut".
     * @return le vecteur up
     */
    public Vector getUp() { return up; }

    /**
     * Retourne le champ de vision.
     * @return le fov en degrés
     */
    public double getFov() { return fov; }

    /**
     * Retourne la base orthonormée de la caméra.
     * @return l'orthonormal
     */
    public Orthonormal getOrthonormal() { return orthonormal; }
}