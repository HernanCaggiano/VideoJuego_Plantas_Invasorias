package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Auto {
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;
	private Image img;
	private Image autoDerecha;
	private Image autoIzquierda;
	private Image autoArriba;
	private Image autoAbajo;
	
	public Auto(int x, int y, int ancho, int alto, int velocidad, String rutaImagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = velocidad;
		this.img = Herramientas.cargarImagen(rutaImagen);
		this.autoDerecha = Herramientas.cargarImagen("imagenes/autoHorizontalDerecha.png");
		this.autoIzquierda = Herramientas.cargarImagen("imagenes/autoHorizontalIzquierda.png");
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0);
	}
	
	public void moverHorizontalDerecha() {
		this.x = this.x + this.velocidad;
	}
	
	public void moverHorizontalIzquierda() {
		this.x = this.x - this.velocidad;
	}
	
	public void moverVerticar() {
		this.y = this.y + this.velocidad;
	}
	
	public void cambiarSentido() {
		this.velocidad = -1 * this.velocidad;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getAncho() {
		return this.ancho;
	}
	
	public int getAlto() {
		return this.alto;
	}
	
	public int getVelocidad() {
		return this.velocidad;
	}

}
