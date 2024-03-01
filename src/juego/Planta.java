package juego;


import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Planta {
	
	private int x;
	private int y;
	private int diametro;
	private int velocidad;
	private Image img;
	private Image mirandoDerecha;
	private Image mirandoIzquierda;
	private Image mirandoArriba;
	private Image mirandoAbajo;
	
	public Planta(int x, int y, int diametro, int velocidad, String rutaImagen) {
		this.x = x;
		this.y = y;
		this.diametro = diametro;
		this.velocidad = velocidad;
		this.img = Herramientas.cargarImagen(rutaImagen);
		this.mirandoAbajo = Herramientas.cargarImagen("imagenes/plantaAbajo.png");
		this.mirandoArriba = Herramientas.cargarImagen("imagenes/plantaArriba.png");
		this.mirandoDerecha = Herramientas.cargarImagen("imagenes/plantaDerecha.png");
		this.mirandoIzquierda = Herramientas.cargarImagen("imagenes/plantaIzquierda.png");
	}
	
	
	public Image tenerimagen(String rutaimagen) {
		return img = Herramientas.cargarImagen(rutaimagen);
	}
	
	public void cambiarImagen() {
		if(this.img.equals(mirandoAbajo)) {
			img = mirandoArriba;
		} else if(this.img.equals(mirandoArriba)) {
			img = mirandoAbajo;
		} else if(this.img.equals(mirandoDerecha)) {
			img = mirandoIzquierda;
		} else if(this.img.equals(mirandoIzquierda)) {
			img = mirandoDerecha;
		}
	}
	

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0);
	}
	
	public Fuego lanzarFuego() {
		return new Fuego(this.x, this.y, this.diametro, 2, "imagenes/bolaFuego.png");
	}
	
	public void moverHorizontal() {
		this.x = (this.x + this.velocidad);
	}
	
	public void moverVertical() {
		this.y = (this.y + this.velocidad);
	}
	
	public void cambiarSentido() {
		this.velocidad = -1 * this.velocidad;
	}
	
	public int getXPlanta() {
		return this.x;
	}
	
	public int getYPlanta() {
		return this.y;
	}
	
	public int getDiametroPlanta() {
		return this.diametro;
	}

}
