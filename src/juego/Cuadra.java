package juego;

//import java.awt.Color;


import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

public class Cuadra {
	private int x;
	private int y;
	private int ancho;
	private int alto;
    private Image imagen;
	
	public Cuadra(int x, int y, int ancho, int alto,  String rutaImagen) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
        this.imagen = new ImageIcon(rutaImagen).getImage();
        changeSizeImage(ancho, alto);
	}
	
	
	public void draw(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}
	
	private void changeSizeImage(int ancho, int alto) {
        imagen = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }
	
	

}
