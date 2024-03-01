package juego;



import java.awt.Image;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import entorno.Entorno;
import entorno.Herramientas;

public class Laser {
	private int velocidad;
	private int x;
	private int y;
	private int ancho;
	private int alto;
    private Image imagen;
    private Image rayoDerecha;
    private Image rayoIzquierda;
    private Image rayoArriba;
    private Image rayoAbajo;
	

	public Laser(int velocidad, int x, int y, int ancho, int alto, String rutaImagen) {
		this.velocidad = velocidad;
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
        this.imagen = Herramientas.cargarImagen(rutaImagen);
        this.rayoDerecha = Herramientas.cargarImagen("imagenes/laserDerecha.png");
        this.rayoIzquierda = Herramientas.cargarImagen("imagenes/laserIzquierda.png");
        this.rayoAbajo = Herramientas.cargarImagen("imagenes/laserAbajo.png");
        this.rayoArriba = Herramientas.cargarImagen("imagenes/laserArriba.png");
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}
	
	public void cambiarImagen(String s, Laika laika) {
		if(s.equals("derecha") && laika.getImg().equals(laika.getCaminandoDerecha())) {
			imagen = rayoDerecha;
		} else if(s.equals("izquierda") && laika.getImg().equals(laika.getCaminandoIzquierda())) {
			imagen = rayoIzquierda;
		} else if(s.equals("arriba") && laika.getImg().equals(laika.getCaminandoArriba())) {
			imagen = rayoArriba;
		} else if(s.equals("abajo") && laika.getImg().equals(laika.getCaminandoAbajo())) {
			imagen = rayoAbajo;
		}
	}
	
	public void moverRight() {
        this.x += velocidad;
    }
	
	public void moverUp() {
        this.y -= velocidad;
    }
	
	public void moverLeft() {
        this.x -= velocidad;
    }
	
	public void moverDown() {
        this.y += velocidad;
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
	
	//COLISION CON AUTOS
	
	public boolean checkCollisionCarsRight (Auto auto) {
		return (x < auto.getX() + auto.getAncho()/2 && x + ancho/2 > auto.getX()  - (auto.getAncho()+5)/2 && y < auto.getY() + (auto.getAlto()/2) && y + alto/2 > auto.getY() - auto.getAlto()/2);
	}
	
	public boolean checkCollisionCarsLeft (Auto auto) {
		return x > auto.getX() - auto.getAncho()/2 && x - ancho/2 < auto.getX()  + (auto.getAncho()+5)/2 && y < auto.getY() + (auto.getAlto()/2) && y + alto/2 > auto.getY() - auto.getAlto()/2;
	}
	
	public boolean checkCollisionCarsUp (Auto auto) {
		return x + ancho/2 > auto.getX() - auto.getAncho()/2 && x < auto.getX() + auto.getAncho()/2 && y + alto > auto.getY()  && y < auto.getY() + (auto.getAlto() + 40)/2;
	}
	
	public boolean checkCollisionCarsDown (Auto auto) {
		return x + ancho/2 > auto.getX() - auto.getAncho()/2 && x < auto.getX() + auto.getAncho()/2 && y - alto < auto.getY()  && y > auto.getY() - (auto.getAlto() + 45)/2;
	}
	
	//COLISION CON PLANTAS
	
	public boolean checkCollisionFloorsRight (Planta planta) {
		return (x < planta.getXPlanta() + planta.getDiametroPlanta()/2 && x + ancho/2 > planta.getXPlanta()  - (planta.getDiametroPlanta()+5)/2 && y < planta.getYPlanta() + (planta.getDiametroPlanta()/2) && y + alto/2 > planta.getYPlanta() - planta.getDiametroPlanta()/2);
	}
	
	public boolean checkCollisionFloorsLeft (Planta planta) {
		return x > planta.getXPlanta() - planta.getDiametroPlanta()/2 && x - ancho/2 < planta.getXPlanta()  + (planta.getDiametroPlanta()+5)/2 && y < planta.getYPlanta() + (planta.getDiametroPlanta()/2) && y + alto/2 > planta.getYPlanta() - planta.getDiametroPlanta()/2;
	}
	
	
	public boolean checkCollisionFloorsUp (Planta planta) {
		return x + ancho/2 > planta.getXPlanta() - planta.getDiametroPlanta()/2 && x < planta.getXPlanta() + planta.getDiametroPlanta()/2 && y + alto > planta.getYPlanta()  && y < planta.getYPlanta() + (planta.getDiametroPlanta() + 40)/2;
	}
	
	public boolean checkCollisionFloorsDown (Planta planta) {
		return x + ancho/2 > planta.getXPlanta() - planta.getDiametroPlanta()/2 && x < planta.getXPlanta() + planta.getDiametroPlanta()/2 && y - alto < planta.getYPlanta()  && y > planta.getYPlanta() - (planta.getDiametroPlanta() + 45)/2;
	}
	
}
