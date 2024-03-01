package juego;

import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.ImageIcon;

import entorno.Entorno;
import entorno.InterfaceJuego;
import entorno.Herramientas;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	

	// Variables y métodos propios de cada grupo
	// ...
	
	private Cuadra cuadra1, cuadra2, cuadra3, cuadra4, cuadra5, cuadra6;
	private Laika laika;
	private Laser laser;
	private Planta[] plantas = new Planta[5];
	private Auto[] autos = new Auto[4];
	private boolean juegoFinalizado = false;
	private Image perder;
	private boolean creadoRight = false;
	private boolean creadoLeft = false;
	private boolean creadoUp = false;
	private boolean creadoDown = false;

	Juego() {
		Random rand = new Random();
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, " Plantas Invasoras - Grupo ... - v1", 800, 600);
		
		
		// Inicializar lo que haga falta para el juego
		// ...
		//Inicializa las cuadras
		this.cuadra1 = new Cuadra((this.entorno.ancho() / 2)/3 + 35, (this.entorno.alto()/2) / 2 + 19, ((this.entorno.alto()/100) * 60)/3, ((this.entorno.alto()/100) * 60)/2, "src/juego/manzana1.PNG");
		this.cuadra2 = new Cuadra(this.entorno.ancho() / 2, (this.entorno.alto()/2) / 2 + 19, ((this.entorno.alto()/100) * 60)/3, ((this.entorno.alto()/100) * 60)/2, "src/juego/manzana2.PNG");
		this.cuadra3 = new Cuadra((this.entorno.ancho() - (this.entorno.ancho() / 2)/3) - 35, (this.entorno.alto()/2)/2 + 19, ((this.entorno.alto()/100) * 60)/3, ((this.entorno.alto()/100) * 60)/2, "src/juego/manzana1.PNG");
		
		this.cuadra4 = new Cuadra((this.entorno.ancho() / 2)/3 + 35, (this.entorno.alto() - (this.entorno.alto()/2) / 2) - 19, ((this.entorno.alto()/100) * 60)/3, ((this.entorno.alto()/100) * 60)/2, "src/juego/manzana1.PNG");
		this.cuadra5 = new Cuadra(this.entorno.ancho() / 2, (this.entorno.alto() - (this.entorno.alto()/2) / 2) - 19, ((this.entorno.alto()/100) * 60)/3, ((this.entorno.alto()/100) * 60)/2, "src/juego/manzana2.PNG");
		this.cuadra6 = new Cuadra((this.entorno.ancho() - (this.entorno.ancho() / 2)/3) - 35, (this.entorno.alto() - (this.entorno.alto()/2)/2) - 19, ((this.entorno.alto()/100) * 60)/3, ((this.entorno.alto()/100) * 60)/2, "src/juego/manzana1.PNG");

		this.laika = new Laika (this.entorno.ancho()/2 , this.entorno.alto() - (this.entorno.alto()/4)/5 , 30, 30);
		
		
		
		//Inicializa las plantas
		this.plantas[0] = new Planta(20, 20, 40, 1);
	    this.plantas[1] = new Planta(this.entorno.ancho() - 20, this.entorno.alto() / 2 + 17, 40, 1);
	    this.plantas[2] = new Planta(20, this.entorno.alto() - 20, 40, 1);
	    this.plantas[3] = new Planta(this.entorno.ancho() / 3 - 20, this.entorno.alto() - 20, 40, 1);
	    this.plantas[4] = new Planta(this.entorno.ancho() / 3 + this.entorno.ancho() / 3 + 20, 20, 40, 1);
		
		//Inicializa los autos
		this.autos[0] = new Auto(this.entorno.ancho() - 20, 67, 40, 20, 1);
		this.autos[1] = new Auto(20, this.entorno.alto() / 2 - 17, 40, 20, 1);
		this.autos[2] = new Auto(this.entorno.ancho() / 3 + 60, 20, 20, 40, 1);
		this.autos[3] = new Auto(this.entorno.ancho() - 20, this.entorno.alto() - 60, 40, 20, 1);
		
		
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		// ...
		this.cuadra1.draw(this.entorno);
		this.cuadra2.draw(this.entorno);
		this.cuadra3.draw(this.entorno);
		this.cuadra4.draw(this.entorno);
		this.cuadra5.draw(this.entorno);
		this.cuadra6.draw(this.entorno);
		
		
		for(int i = 0; i < this.plantas.length; i++) {
			this.plantas[i].dibujar(this.entorno);
		}
		
		for(int i = 0; i < this.autos.length; i++) {
			this.autos[i].dibujar(this.entorno);
		}
		
		this.laika.draw(this.entorno);
		
		//movimiento de laika y colisiones
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA) && this.laika.getXLaika() + this.laika.getAncho()/2 < this.entorno.ancho()) {
			if(!this.laika.checkCollisionRight(cuadra1) && !this.laika.checkCollisionRight(cuadra2) && !this.laika.checkCollisionRight(cuadra3) && !this.laika.checkCollisionRight(cuadra4) && !this.laika.checkCollisionRight(cuadra5) && !this.laika.checkCollisionRight(cuadra6)) {
				this.laika.moveRight();
			}
		}else if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA) && this.laika.getXLaika() - this.laika.getAncho()/2 > 0) {
			if(!this.laika.checkCollisionLeft(cuadra1) && !this.laika.checkCollisionLeft(cuadra2) && !this.laika.checkCollisionLeft(cuadra3) && !this.laika.checkCollisionLeft(cuadra4) && !this.laika.checkCollisionLeft(cuadra5) && !this.laika.checkCollisionLeft(cuadra6)) {
				this.laika.moveLeft();
			}
		}else if(this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA) && this.laika.getYLaika() - this.laika.getAlto()/2 > 0) {
			if(!this.laika.checkCollisionUp(cuadra1) && !this.laika.checkCollisionUp(cuadra2) && !this.laika.checkCollisionUp(cuadra3) && !this.laika.checkCollisionUp(cuadra4) && !this.laika.checkCollisionUp(cuadra5) && !this.laika.checkCollisionUp(cuadra6)) {
				this.laika.moveUp();
			}
		}else if(this.entorno.estaPresionada(this.entorno.TECLA_ABAJO) && this.laika.getYLaika() + this.laika.getAlto()/2 < this.entorno.alto()) {
			if(!this.laika.checkCollisionDown(cuadra1) && !this.laika.checkCollisionDown(cuadra2) && !this.laika.checkCollisionDown(cuadra3) && !this.laika.checkCollisionDown(cuadra4) && !this.laika.checkCollisionDown(cuadra5) && !this.laika.checkCollisionDown(cuadra6)) {
				this.laika.moveDown();
			}
		}
		
		
		if(!creadoRight && !creadoLeft  && !creadoUp && !creadoDown ) {
			//disparar laser
			if(this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)) {
				this.laser = new Laser(3, this.laika.getXLaika(), this.laika.getYLaika(), 15, 10, "src/juego/LaserDerecha.png");
				
				creadoRight = this.laika.right();
				creadoLeft = this.laika.left(); 
				creadoUp = this.laika.up(); 
				creadoDown = this.laika.down();
			}
		}
		
		if(creadoRight) {
			this.laser.draw(this.entorno);
			this.laser.moverRight();
			if(this.laser.getX() >= 800 
				||this.laser.checkCollisionRight(cuadra1) || this.laser.checkCollisionRight(cuadra2) || this.laser.checkCollisionRight(cuadra3) || this.laser.checkCollisionRight(cuadra4) || this.laser.checkCollisionRight(cuadra5) || this.laser.checkCollisionRight(cuadra6)
				|| this.laser.checkCollisionCarsRight(this.autos[0]) || this.laser.checkCollisionCarsRight(this.autos[1]) || this.laser.checkCollisionCarsRight(this.autos[2]) || this.laser.checkCollisionCarsRight(this.autos[3])
				|| this.laser.checkCollisionFloorsRight(this.plantas[0]) || this.laser.checkCollisionFloorsRight(this.plantas[1]) || this.laser.checkCollisionFloorsRight(this.plantas[2]) || this.laser.checkCollisionFloorsRight(this.plantas[3]) || this.laser.checkCollisionFloorsRight(this.plantas[4])) {				
				creadoRight = false;
			} 
		}
		
		if(creadoLeft) {
			this.laser.draw(this.entorno);
			this.laser.moverLeft();
			if(this.laser.getX() <= 0 
				|| this.laser.checkCollisionLeft(cuadra1) || this.laser.checkCollisionLeft(cuadra2) || this.laser.checkCollisionLeft(cuadra3) || this.laser.checkCollisionLeft(cuadra4) || this.laser.checkCollisionLeft(cuadra5) || this.laser.checkCollisionLeft(cuadra6)
				|| this.laser.checkCollisionCarsLeft(this.autos[0]) || this.laser.checkCollisionCarsLeft(this.autos[1]) || this.laser.checkCollisionCarsLeft(this.autos[2]) || this.laser.checkCollisionCarsLeft(this.autos[3])
				|| this.laser.checkCollisionFloorsLeft(this.plantas[0]) || this.laser.checkCollisionFloorsLeft(this.plantas[1]) || this.laser.checkCollisionFloorsLeft(this.plantas[2]) || this.laser.checkCollisionFloorsLeft(this.plantas[3]) || this.laser.checkCollisionFloorsLeft(this.plantas[4])) {	
				creadoLeft = false;		
			} 
		}
		
		if(creadoUp) {
			this.laser.draw(this.entorno);
			this.laser.moverUp();
			if(this.laser.getY() <= 1 
				|| this.laser.checkCollisionUp(cuadra1) || this.laser.checkCollisionUp(cuadra2) || this.laser.checkCollisionUp(cuadra3) || this.laser.checkCollisionUp(cuadra4) || this.laser.checkCollisionUp(cuadra5) || this.laser.checkCollisionUp(cuadra6)
				|| this.laser.checkCollisionCarsUp(this.autos[0]) || this.laser.checkCollisionCarsUp(this.autos[1]) || this.laser.checkCollisionCarsUp(this.autos[2]) || this.laser.checkCollisionCarsUp(this.autos[3])
				|| this.laser.checkCollisionFloorsUp(this.plantas[0]) || this.laser.checkCollisionFloorsUp(this.plantas[1]) || this.laser.checkCollisionFloorsUp(this.plantas[2]) || this.laser.checkCollisionFloorsUp(this.plantas[3]) || this.laser.checkCollisionFloorsUp(this.plantas[4])) {					
				creadoUp = false;			
			} 
		}
		
		if(creadoDown) {
			this.laser.draw(this.entorno);
			this.laser.moverDown();
			if(this.laser.getY() >= 600 
				|| this.laser.checkCollisionDown(cuadra1) || this.laser.checkCollisionDown(cuadra2) || this.laser.checkCollisionDown(cuadra3) || this.laser.checkCollisionDown(cuadra4) || this.laser.checkCollisionDown(cuadra5) || this.laser.checkCollisionDown(cuadra6)
				|| this.laser.checkCollisionCarsDown(this.autos[0]) || this.laser.checkCollisionCarsDown(this.autos[1]) || this.laser.checkCollisionCarsDown(this.autos[2]) || this.laser.checkCollisionCarsDown(this.autos[3])
				|| this.laser.checkCollisionFloorsDown(this.plantas[0]) || this.laser.checkCollisionFloorsDown(this.plantas[1]) || this.laser.checkCollisionFloorsDown(this.plantas[2]) || this.laser.checkCollisionFloorsDown(this.plantas[3]) || this.laser.checkCollisionFloorsDown(this.plantas[4])) {				
				creadoDown = false;
			} 
		}
		
		
		
		//Movimiento de plantas y colisiones
		this.plantas[0].moverHorizontal();
		
		
		if(!this.hayColisionBordeDerecho(this.plantas[0])) {
			this.plantas[0].cambiarSentido();
		}
		
		if(this.hayColisionBordeIzquierdo(this.plantas[0])) {
			this.plantas[0].cambiarSentido();
		}
		
		this.plantas[1].moverHorizontal();
		
		if(this.hayColisionBordeDerecho(this.plantas[1])) {
			this.plantas[1].cambiarSentido();
		}
		
		if(!this.hayColisionBordeIzquierdo(this.plantas[1])) {
			this.plantas[1].cambiarSentido();
		}
		
		this.plantas[2].moverHorizontal();
		
		if(!this.hayColisionBordeDerecho(this.plantas[2])) {
			this.plantas[2].cambiarSentido();
		}
		
		if(this.hayColisionBordeIzquierdo(this.plantas[2])) {
			this.plantas[2].cambiarSentido();
		}
		
		this.plantas[3].moverVertical();
		
		if(this.hayColisionBordeSuperior(plantas[3])) {
			this.plantas[3].cambiarSentido();
		}
		
		if(!this.hayColisionBordeInferior(plantas[3])) {
			this.plantas[3].cambiarSentido();
		}
		
		this.plantas[4].moverVertical();
		
		if(!this.hayColisionBordeSuperior(plantas[4])) {
			this.plantas[4].cambiarSentido();
		}
		
		if(this.hayColisionBordeInferior(plantas[4])) {
			this.plantas[4].cambiarSentido();
		}
		
		//movimiento de autos
		this.autos[0].moverHorizontal();
		
		if(this.hayColisionBordeDerecho(this.autos[0])) {
			this.autos[0].cambiarSentido();
		}
		
		if(!this.hayColisionBordeIzquierdo(this.autos[0])) {
			this.autos[0].cambiarSentido();
		}
		
		this.autos[1].moverHorizontal();
		
		if(!this.hayColisionBordeDerecho(this.autos[1])) {
			this.autos[1].cambiarSentido();
		}
		
		if(this.hayColisionBordeIzquierdo(this.autos[1])) {
			this.autos[1].cambiarSentido();
		}
		
		this.autos[2].moverVerticar();
		
		if(!this.hayColisionBordeSuperior(autos[2])) {
			this.autos[2].cambiarSentido();
		}
		
		if(this.hayColisionBordeInferior(autos[2])) {
			this.autos[2].cambiarSentido();
		}
		
		this.autos[3].moverHorizontal();
		
		if(this.hayColisionBordeDerecho(this.autos[3])) {
			this.autos[3].cambiarSentido();
		}
		
		if(!this.hayColisionBordeIzquierdo(this.autos[3])) {
			this.autos[3].cambiarSentido();
		}
		
		if(this.hayColisionLaikaYAuto() || this.hayColisionLaikaYPlantas()) {
			this.juegoFinalizado = true;
		}
		
		if(this.juegoFinalizado) {
			
			this.perder = new ImageIcon("src/juego/juegoFinalizado.PNG").getImage();
			this.entorno.dibujarImagen(this.perder, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0, 1.6);
		}
		
		
	}
	//Metodos para auto
	public boolean hayColisionBordeDerecho(Auto a) {
		return (a.getX() + (a.getAncho() / 2)) <= this.entorno.ancho();
	}
	
	public boolean hayColisionBordeIzquierdo(Auto a) {
		return (a.getX() - (a.getAncho() / 2)) <= 0;
	}
	
	public boolean hayColisionBordeSuperior(Auto a) {
		return (a.getY() - (a.getAlto() / 2)) <= 0;
	}
	
	public boolean hayColisionBordeInferior(Auto a) {
		return (a.getY() + (a.getAlto() / 2)) <= this.entorno.alto();
	}
	
	
	//metodos para planta
	public boolean hayColisionBordeDerecho(Planta p) {
		return (p.getXPlanta() + (p.getDiametroPlanta() / 2)) <= this.entorno.ancho();
	}
	
	public boolean hayColisionBordeIzquierdo(Planta p) {
		return (p.getXPlanta() - (p.getDiametroPlanta() / 2)) <= 0;
	}
	
	public boolean hayColisionBordeSuperior(Planta p) {
		return (p.getYPlanta() - (p.getDiametroPlanta() / 2)) <= 0;
	}
	
	public boolean hayColisionBordeInferior(Planta p) {
		return (p.getYPlanta() + (p.getDiametroPlanta() / 2)) <= this.entorno.alto();
	}
	
	
	//colision laika y auto
	public boolean hayColisionLaikaYAuto() {
		
		for(int i = 0; i < this.autos.length; i++) {
			boolean colision = this.laika.getXLaika() + this.laika.getAncho() / 2 >= this.autos[i].getX() - this.autos[i].getAncho() / 2
									&& this.laika.getXLaika() - this.laika.getAncho() / 2 <= this.autos[i].getX() + this.autos[i].getAncho() / 2
									&& this.laika.getYLaika() + this.laika.getAlto() / 2 >= this.autos[i].getY() - this.autos[i].getAlto() / 2
									&& this.laika.getYLaika() - this.laika.getAlto() / 2 <= this.autos[i].getY() + this.autos[i].getAlto() / 2;
				
			if(colision) {
				return true;
			}
		}
		return false;
	}
	
	//colision laika y plantas
	public boolean hayColisionLaikaYPlantas() {
		for(int i = 0; i < this.plantas.length; i++) {
			boolean colision = this.laika.getXLaika() + this.laika.getAncho() / 2 >= this.plantas[i].getXPlanta() - this.plantas[i].getDiametroPlanta() / 2
					&& this.laika.getXLaika() - this.laika.getAncho() / 2 <= this.plantas[i].getXPlanta() + this.plantas[i].getDiametroPlanta() / 2
					&& this.laika.getYLaika() + this.laika.getAlto() / 2 >= this.plantas[i].getYPlanta() - this.plantas[i].getDiametroPlanta()/ 2
					&& this.laika.getYLaika() - this.laika.getAlto() / 2 <= this.plantas[i].getYPlanta() + this.plantas[i].getDiametroPlanta() / 2;
			
			if(colision) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
