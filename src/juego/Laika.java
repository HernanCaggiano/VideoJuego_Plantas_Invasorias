package juego;

import entorno.Entorno;

import entorno.Herramientas;

//import java.awt.Color;
import java.awt.Image;

public class Laika {
	
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Image img;
	private Image caminandoDerecha;
	private Image caminandoIzquierda;
	private Image caminandoArriba;
	private Image caminandoAbajo;
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	public Laika(int x, int y, int ancho, int alto, String rutaImagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.img = Herramientas.cargarImagen(rutaImagen);
		this.caminandoDerecha = Herramientas.cargarImagen("imagenes/perroMoviendoDerecha.png");
		this.caminandoIzquierda = Herramientas.cargarImagen("imagenes/perroMoviendoIzquierda.png");
		this.caminandoArriba = Herramientas.cargarImagen("imagenes/perroMoviendoArriba.png");
		this.caminandoAbajo = Herramientas.cargarImagen("imagenes/perroMoviendoAbajo.png");
	}
	
	//COLISION CON CUADRAS
	public boolean checkCollisionRight(Cuadra cuadra) {
		return x < cuadra.getX() + cuadra.getAncho()/2 && x + ancho/2 > cuadra.getX()  - (cuadra.getAncho()+5)/2 && y < cuadra.getY() + (cuadra.getAlto()/2) && y + alto/2 > cuadra.getY() - cuadra.getAlto()/2;
    }
	
	public boolean checkCollisionLeft(Cuadra cuadra) {
		return x > cuadra.getX() - cuadra.getAncho()/2 && x - ancho/2 < cuadra.getX()  + (cuadra.getAncho()+5)/2 && y < cuadra.getY() + (cuadra.getAlto()/2) && y + alto/2 > cuadra.getY() - cuadra.getAlto()/2;
    }
	
	public boolean checkCollisionUp (Cuadra cuadra) {
		return x + ancho/2 > cuadra.getX() - cuadra.getAncho()/2 && x < cuadra.getX() + cuadra.getAncho()/2 && y + alto > cuadra.getY()  && y < cuadra.getY() + (cuadra.getAlto() + 40)/2;
	}
	
	public boolean checkCollisionDown (Cuadra cuadra) {
		return x + ancho/2 > cuadra.getX() - cuadra.getAncho()/2 && x < cuadra.getX() + cuadra.getAncho()/2 && y - alto < cuadra.getY()  && y > cuadra.getY() - (cuadra.getAlto() + 45)/2;
	}
	
	
	public void draw(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0);;
	}
	
	public void moveLeft() {
		this.x = this.x - 2;
		this.left = true;
		this.right = false;
		this.up = false;
		this.down = false;
	}
	
	public void moveRight() {
		this.x = this.x + 2;
		this.left = false;
		this.right = true;
		this.up = false;
		this.down = false;
	}
	
	public void moveUp() {
		this.y = this.y - 2;
		this.left = false;
		this.right = false;
		this.up = true;
		this.down = false;
	}
	
	public void moveDown() {
		this.y = this.y + 2;
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = true;
	}
	
	//Lanzar rayo
	public Laser lanzarRayo() {
		return new Laser(3, this.x + this.ancho / 2, this.y - this.alto / 2, 20, 15, "imagenes/laserDerecha.png");
	}
	
	public void cambiarImagen(String s) {
		if(s.equals("derecha") && !this.img.equals(caminandoDerecha)) {
			img = caminandoDerecha;
		} else if(s.equals("izquierda") && !this.img.equals(caminandoIzquierda)) {
			img = caminandoIzquierda;
		} else if(s.equals("arriba") && !this.img.equals(caminandoArriba)) {
			img = caminandoArriba;
		} else if(s.equals("abajo") && !this.img.equals(caminandoAbajo)) {
			img = caminandoAbajo;
		}
	}
	
	public int getXLaika() {
		return this.x;
	}
	
	public int getYLaika() {
		return this.y;
	}
	
	public int getAncho() {
		return this.ancho;
	}
	
	public int getAlto() {
		return this.alto;
	}
	
	public boolean right () {
		return this.right;
	}
	
	public boolean left () {
		return this.left;
	}
	
	public boolean up () {
		return this.up;
	}
	
	public boolean down () {
		return this.down;
	}
	
	public Image getImg() {
		return this.img;
	}
	
	public Image getCaminandoDerecha() {
		return this.caminandoDerecha;
	}
	
	public Image getCaminandoIzquierda() {
		return this.caminandoIzquierda;
	}
	
	public Image getCaminandoArriba() {
		return this.caminandoArriba;
	}
	
	public Image getCaminandoAbajo() {
		return this.caminandoAbajo;
	}

}
