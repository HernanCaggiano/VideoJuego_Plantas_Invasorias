package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fuego {
	
	private int x;
	private int y;
	private int diametro;
	private int velocidad;
	private Image img;
	
	public Fuego(int x, int y, int diametro, int velocidad, String rutaImagen) {
		this.x = x;
		this.y = y;
		this.diametro = diametro;
		this.velocidad = velocidad;
		this.img = Herramientas.cargarImagen(rutaImagen);
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0);
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
    
    public int getDiametro() {
    	return this.diametro;
    }
    
    //colision con autos asdjasdasd
    public boolean checkCollisionCarsRight (Auto auto) {
		return (x < auto.getX() + auto.getAncho()/2 && x + diametro/2 > auto.getX()  - (auto.getAncho()+5)/2 && y < auto.getY() + (auto.getAlto()/2) && y + diametro/2 > auto.getY() - auto.getAlto()/2);
	}
	
	public boolean checkCollisionCarsLeft (Auto auto) {
		return x > auto.getX() - auto.getAncho()/2 && x - diametro/2 < auto.getX()  + (auto.getAncho()+5)/2 && y < auto.getY() + (auto.getAlto()/2) && y + diametro/2 > auto.getY() - auto.getAlto()/2;
	}
	
	public boolean checkCollisionCarsUp (Auto auto) {
		return x + diametro/2 > auto.getX() - auto.getAncho()/2 && x < auto.getX() + auto.getAncho()/2 && y + diametro > auto.getY()  && y < auto.getY() + (auto.getAlto() + 40)/2;
	}
	
	public boolean checkCollisionCarsDown (Auto auto) {
		return x + diametro/2 > auto.getX() - auto.getAncho()/2 && x < auto.getX() + auto.getAncho()/2 && y - diametro < auto.getY()  && y > auto.getY() - (auto.getAlto() + 45)/2;
	}
  

}
