package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
	private Fuego[] fuego = new Fuego[] {null, null, null, null, null, null, null};
	private Planta[] plantas = new Planta[] {null, null, null, null, null, null, null};
	private Auto[] autos = new Auto[4];
	
	private Image perder;
	
	private int puntos = 0;
	private int cantPlantasEliminadas;
	
	private boolean colision;
	private boolean creadoRight = false;
	private boolean creadoLeft = false;
	private boolean creadoUp = false;
	private boolean creadoDown = false;
	private boolean jugar = false;
	private boolean juegoFinalizado = false;
	private boolean creadafuego0 = false;
	private boolean derfuego = true;
	private boolean izqfuego = true;
	private boolean arribafuego = true;
	private boolean abajofuego = true;
	
	private long ultimoLanzamiento = 0;
    private long tiempoActual = System.currentTimeMillis();
    private long tiempoEspera = 5000;
    private long tiempoUltimaCreacion;
	
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

		this.laika = new Laika(this.entorno.ancho()/2 , this.entorno.alto() - (this.entorno.alto()/4)/5 , 25, 25, "imagenes/perroQuieto.png");
		
		
		
		//Inicializa las plantas
		this.plantas[0] = new Planta(26, 26, 37, 1, "imagenes/plantaDerecha.png");
	    this.plantas[1] = new Planta(this.entorno.ancho() - 26, this.entorno.alto() / 2 + 17, 37, 1, "imagenes/plantaDerecha.png");
	    this.plantas[2] = new Planta(26, this.entorno.alto() - 26, 37, 1, "imagenes/plantaDerecha.png");
	    this.plantas[3] = new Planta(this.entorno.ancho() / 3 - 26, this.entorno.alto() - 26, 37, 1, "imagenes/plantaAbajo.png");
	    this.plantas[4] = new Planta(this.entorno.ancho() / 3 + this.entorno.ancho() / 3 + 26, 26, 37, 1, "imagenes/plantaAbajo.png");
		
		//Inicializa los autos
		this.autos[0] = new Auto(this.entorno.ancho() - 20, 67, 40, 20, 1, "imagenes/autoHorizontalIzquierda.png");
		this.autos[1] = new Auto(20, this.entorno.alto() / 2 - 17, 40, 20, 1, "imagenes/autoHorizontalDerecha.png");
		this.autos[2] = new Auto(this.entorno.ancho() / 3 + 60, 20, 20, 40, 1, "imagenes/autoVerticalAbajo.png");
		this.autos[3] = new Auto(this.entorno.ancho() - 20, this.entorno.alto() - 67, 40, 20, 1, "imagenes/autoHorizontalIzquierda.png");
		
		//Inicializa las bolas de fuego
		this.fuego[0] = this.plantas[0].lanzarFuego();
		this.fuego[1] = this.plantas[1].lanzarFuego();
		this.fuego[2] = this.plantas[2].lanzarFuego();
		this.fuego[3] = this.plantas[3].lanzarFuego();
		this.fuego[4] = this.plantas[4].lanzarFuego();

		
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
		//portada de inicio
		if(!this.jugar) {
			this.entorno.dibujarImagen(Herramientas.cargarImagen("imagenes/menu.png"), this.entorno.ancho() / 2, this.entorno.alto() / 2, 0);
			if(this.entorno.estaPresionada(this.entorno.TECLA_ENTER)) {
				this.jugar = true;
			}
		}
		//comienza el juego
		else if(this.jugar) {
			this.entorno.dibujarImagen(Herramientas.cargarImagen("imagenes/fondo.png"), this.entorno.ancho() / 2, this.entorno.alto() / 2, 0);
			this.cuadra1.draw(this.entorno);
			this.cuadra2.draw(this.entorno);
			this.cuadra3.draw(this.entorno);
			this.cuadra4.draw(this.entorno);
			this.cuadra5.draw(this.entorno);
			this.cuadra6.draw(this.entorno);
			
			//Dibuja las plantas
			if(plantas[0] != null) {
				this.plantas[0].dibujar(this.entorno);
				crearboladefuegoD(0);
			}
			
			if(plantas[1] != null) {
				this.plantas[1].dibujar(this.entorno);
				crearboladefuegoI(1);
			}
			
			if(plantas[2] != null) {
				this.plantas[2].dibujar(this.entorno);
				crearboladefuegoD(2);
			}
			
			if(plantas[3] != null) {
				this.plantas[3].dibujar(this.entorno);
				crearboladefuegoU(3);
			}
			
			if(plantas[4] != null) {
				this.plantas[4].dibujar(this.entorno);
				crearboladefuegoDown(4);
			}
			
			
			//Dibuja los autos
			for(int i = 0; i < this.autos.length; i++) {
				if(this.autos[i] != null) {
					this.autos[i].dibujar(this.entorno);
				}
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
			
			//cambio de imagen laika
			if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
				this.laika.cambiarImagen("derecha");
			} else if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
				this.laika.cambiarImagen("izquierda");
			} else if(this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA)) {
				this.laika.cambiarImagen("arriba");
			} else if(this.entorno.estaPresionada(this.entorno.TECLA_ABAJO)) {
				this.laika.cambiarImagen("abajo");
			}
			
			
			
			//disparar laser
			if(this.laser == null && this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)) {
					this.laser = this.laika.lanzarRayo();
					
					creadoRight = this.laika.right();
					creadoLeft = this.laika.left(); 
					creadoUp = this.laika.up(); 
					creadoDown = this.laika.down();			
			}
			//movimiento del laser
			if(this.laser != null) {
				if(creadoRight) {
					this.laser.dibujar(this.entorno);
					this.laser.moverRight();
					this.laser.cambiarImagen("derecha", this.laika);
				} else if(creadoLeft) {
					this.laser.dibujar(this.entorno);
					this.laser.moverLeft();
					this.laser.cambiarImagen("izquierda", this.laika);
				} else if(creadoUp) {
					this.laser.dibujar(this.entorno);
					this.laser.moverUp();
					this.laser.cambiarImagen("arriba", this.laika);
				} else if(creadoDown) {
					this.laser.dibujar(this.entorno);
					this.laser.cambiarImagen("abajo", this.laika);
					this.laser.moverDown();
				}
			}
			
			
			
			//si hay colision laser y planta elimina planta y eliminia laser
			if(this.laser != null) {
				if(this.hayColisionLaserYPlantas()) {
					for(int i = 0; i < this.plantas.length; i++) {
						if(this.plantas[i] != null) {
							this.colision = this.laser.getX() + this.laser.getAncho() / 2 >= this.plantas[i].getXPlanta() - this.plantas[i].getDiametroPlanta() / 2
									&& this.laser.getX() - this.laser.getAncho() / 2 <= this.plantas[i].getXPlanta() + this.plantas[i].getDiametroPlanta() / 2
									&& this.laser.getY() + this.laser.getAlto() / 2 >= this.plantas[i].getYPlanta() - this.plantas[i].getDiametroPlanta()/ 2
									&& this.laser.getY() - this.laser.getAlto() / 2 <= this.plantas[i].getYPlanta() + this.plantas[i].getDiametroPlanta() / 2;
						}
						
						if(this.colision) {
							this.plantas[i] = null;
							
							break;	
						}	
					}
					
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
					this.puntos += 5;
					this.cantPlantasEliminadas += 1;
				}
				
				
				//Si hay colision laser con cuadra o auto elimina laser
				else if(this.laser != null && (this.laser.checkCollisionDown(cuadra1) || this.laser.checkCollisionDown(cuadra2) || this.laser.checkCollisionDown(cuadra3) || this.laser.checkCollisionDown(cuadra4) || this.laser.checkCollisionRight(cuadra5) || this.laser.checkCollisionRight(cuadra6))) {
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
				}
				
				else if(this.laser != null && (this.laser.checkCollisionRight(cuadra1) || this.laser.checkCollisionRight(cuadra2) || this.laser.checkCollisionRight(cuadra3) || this.laser.checkCollisionRight(cuadra4) || this.laser.checkCollisionRight(cuadra5) || this.laser.checkCollisionRight(cuadra6))) {
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
				}
				
				else if(this.laser != null && (this.laser.checkCollisionLeft(cuadra1) || this.laser.checkCollisionLeft(cuadra2) || this.laser.checkCollisionLeft(cuadra3) || this.laser.checkCollisionLeft(cuadra4) || this.laser.checkCollisionLeft(cuadra5) || this.laser.checkCollisionLeft(cuadra6))) {
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
				}
				
				else if(this.laser != null && (this.laser.checkCollisionUp(cuadra1) || this.laser.checkCollisionUp(cuadra2) || this.laser.checkCollisionUp(cuadra3) || this.laser.checkCollisionUp(cuadra4) || this.laser.checkCollisionUp(cuadra5) || this.laser.checkCollisionUp(cuadra6))) {
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
				}
				
				else if(this.laser != null && (this.hayColisionLaserYAuto())) {
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
				}
				
				else if(this.laser.getX() >= 800 || this.laser.getX() <= 0 || this.laser.getY() <= 0 || this.laser.getY() >= 600) {
					this.laser = null;
					creadoRight = false;
					creadoLeft =  false;
					creadoUp =  false;
					creadoDown = false;
				}
				
				
			}
			
			//colision laser con bola de fuego
			if(this.laser != null && this.hayColisionLaserYFuego()) {
				this.laser = null;
				creadoRight = false;
				creadoLeft =  false;
				creadoUp =  false;
				creadoDown = false;
			}
			
			this.hayColisionAutoYFuego();
			
			//crea nuevas plantas
			if(System.currentTimeMillis() - tiempoUltimaCreacion >= tiempoEspera && this.hayMenosDeCuatroPlantas()) {
				for(int i = 0; i < this.plantas.length; i++) {
					if(this.plantas[i] == null) {
						if( i == 0) {
							plantas[i] = new Planta(26, 26, 37, 1, "imagenes/plantaDerecha.png");
						} else if(i == 1) {
							plantas[i] = new Planta(this.entorno.ancho() - 26, this.entorno.alto() / 2 + 17, 37, 1, "imagenes/plantaDerecha.png");
						} else if(i == 2) {
						    plantas[i] = new Planta(26, this.entorno.alto() - 26, 37, 1, "imagenes/plantaDerecha.png");
						} else if(i == 3) {
						    plantas[i] = new Planta(this.entorno.ancho() / 3 - 26, this.entorno.alto() - 26, 37, 1, "imagenes/plantaAbajo.png");
						} else if(i == 4) {
						    this.plantas[4] = new Planta(this.entorno.ancho() / 3 + this.entorno.ancho() / 3 + 26, 26, 37, 1, "imagenes/plantaAbajo.png");
						}
						tiempoUltimaCreacion = System.currentTimeMillis();
						break;
					}
				}
			}
			
			
			
			
			//Movimiento de plantas y colisiones
			if(this.plantas[0] != null) {
				this.plantas[0].moverHorizontal();
				
				
				if(!this.hayColisionBordeDerecho(this.plantas[0])) {
					this.plantas[0].cambiarSentido();
					this.plantas[0].cambiarImagen();
					this.derfuego = false;
				}
				
				if(this.hayColisionBordeIzquierdo(this.plantas[0])) {
					this.plantas[0].cambiarSentido();
					this.plantas[0].cambiarImagen();
					this.derfuego = true;
				}
			}
			
			if(this.plantas[1] != null) {
				this.plantas[1].moverHorizontal();
				
				if(!this.hayColisionBordeDerecho(this.plantas[1])) {
					this.plantas[1].cambiarSentido();
					this.plantas[1].cambiarImagen();
					this.izqfuego = true;
				}
				
				if(this.hayColisionBordeIzquierdo(this.plantas[1])) {
					this.plantas[1].cambiarSentido();
					this.plantas[1].cambiarImagen();
					this.izqfuego = false;
				}
			}
			
			if(this.plantas[2] != null) {
				this.plantas[2].moverHorizontal();
				
				if(!this.hayColisionBordeDerecho(this.plantas[2])) {
					this.plantas[2].cambiarSentido();
					this.plantas[2].cambiarImagen();
					this.derfuego = false;
				}
				
				if(this.hayColisionBordeIzquierdo(this.plantas[2])) {
					this.plantas[2].cambiarSentido();
					this.plantas[2].cambiarImagen();
					this.derfuego = true;
				}
			}
			
			if(this.plantas[3] != null) {
				this.plantas[3].moverVertical();
				
				if(this.hayColisionBordeSuperior(plantas[3])) {
					this.plantas[3].cambiarSentido();
					this.plantas[3].cambiarImagen();
					this.arribafuego = false;
				}
				
				if(!this.hayColisionBordeInferior(plantas[3])) {
					this.plantas[3].cambiarSentido();
					this.plantas[3].cambiarImagen();
					this.arribafuego = true;
				}
			}
			
			
			if(this.plantas[4] != null) {
				this.plantas[4].moverVertical();
				
				if(this.hayColisionBordeSuperior(plantas[4])) {
					this.plantas[4].cambiarSentido();
					this.plantas[4].cambiarImagen();
					this.abajofuego = true;
				}
				
				if(!this.hayColisionBordeInferior(plantas[4])) {
					this.plantas[4].cambiarSentido();
					this.plantas[4].cambiarImagen();
					this.abajofuego = false;
				}
			}
			
			if(this.plantas[5] != null) {
				this.plantas[5].moverVertical();
				
				if(!this.hayColisionBordeSuperior(plantas[5])) {
					this.plantas[5].cambiarSentido();
					this.plantas[5].cambiarImagen();
				}
				
				if(this.hayColisionBordeInferior(plantas[5])) {
					this.plantas[5].cambiarSentido();
					this.plantas[5].cambiarImagen();
				}
			}
			
			
			//movimiento de autos
			if(this.autos[0] != null) {
				this.autos[0].moverHorizontalIzquierda();
				
				if(this.hayColisionBordeIzquierdo(this.autos[0]) || this.autos[0] == null) {
					this.autos[0] = null;
					this.autos[0] = new Auto(this.entorno.ancho() + 40, 67, 40, 20, 1, "imagenes/autoHorizontalIzquierda.png");
				}
			}
			
			if(this.autos[1] != null) {
				this.autos[1].moverHorizontalDerecha();
				
				if(this.hayColisionBordeDerecho(this.autos[1]) || this.autos[1] == null) {
					this.autos[1] = null;
					this.autos[1] = new Auto(-50, this.entorno.alto() / 2 - 17, 40, 20, 1, "imagenes/autoHorizontalDerecha.png");
				}
				
			}
			
			if(this.autos[2] != null) {
				this.autos[2].moverVerticar();
				
				if(this.hayColisionBordeInferior(autos[2]) || this.autos[2] == null) {
					this.autos[2] = null;
					this.autos[2] = new Auto(this.entorno.ancho() / 3 + 60, -50, 20, 40, 1, "imagenes/autoVerticalAbajo.png");
				}
			}
			
			if(this.autos[3] != null) {
				this.autos[3].moverHorizontalIzquierda();
				
				if(this.hayColisionBordeIzquierdo(this.autos[3]) || this.autos[3] == null) {
					this.autos[3] = null;
					this.autos[3] = new Auto(this.entorno.ancho() + 40, this.entorno.alto() - 67, 40, 20, 1, "imagenes/autoHorizontalIzquierda.png");
				}
			}
			
			for(int i = 0; i < this.autos.length; i++) {
				if(this.autos[i] == null) {
					if(i == 0) {
						autos[i] = new Auto(this.entorno.ancho() - 20, 67, 40, 20, 1, "imagenes/autoHorizontalIzquierda.png");
					} else if(i == 1) {
						this.autos[1] = new Auto(20, this.entorno.alto() / 2 - 17, 40, 20, 1, "imagenes/autoHorizontalDerecha.png");
					} else if(i == 2) {
						this.autos[2] = new Auto(this.entorno.ancho() / 3 + 60, 20, 20, 40, 1, "imagenes/autoVerticalAbajo.png");
					} else if(i == 3) {
						this.autos[3] = new Auto(this.entorno.ancho() - 20, this.entorno.alto() - 67, 40, 20, 1, "imagenes/autoHorizontalIzquierda.png");
					}
				}
			}
			
			
			//Muestra en pantalla el puntaje
			this.entorno.cambiarFont("Arial", 20, Color.white);
			this.entorno.escribirTexto("PUNTOS:", this.entorno.ancho() - 130, 30);
			this.entorno.escribirTexto(Integer.toString(this.puntos), this.entorno.ancho() - 35, 30);
					
			//Muestra en pantalla cantidad de plantas eliminadas
			this.entorno.cambiarFont("Arial", 20, Color.white);
			this.entorno.escribirTexto("PLANTAS ELIMINADAS:", 10, 30);
			this.entorno.escribirTexto(Integer.toString(this.cantPlantasEliminadas), 240, 30);
			
	
			if(this.hayColisionLaikaYAuto() || this.hayColisionLaikaYPlantas() || this.hayColisionLaikaYBolas()) {
				this.juegoFinalizado = true;
			}
			
			
			if(this.juegoFinalizado) {
				
				this.perder = new ImageIcon("src/juego/juegoFinalizado.PNG").getImage();
				this.entorno.dibujarImagen(this.perder, this.entorno.ancho() / 2, this.entorno.alto() / 2, 0, 1.6);
			}
		}
		
	}
	
	//Metodos para auto
	public boolean hayColisionBordeDerecho(Auto a) {
		return (a.getX() - (a.getAncho() / 2)) >= this.entorno.ancho();
	}
	
	public boolean hayColisionBordeIzquierdo(Auto a) {
		return (a.getX() + (a.getAncho() / 2)) < 0;
	}
	
	public boolean hayColisionBordeSuperior(Auto a) {
		return (a.getY() + (a.getAlto() / 2)) < 0;
	}
	
	public boolean hayColisionBordeInferior(Auto a) {
		return (a.getY() - (a.getAlto() / 2)) > this.entorno.alto();
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
	
	//metodo para crear laser de laika
	public Laser presiono() {
		if(this.laika != null && this.laser == null && this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)) {
			laser = this.laika.lanzarRayo();
		}
		return laser;
	}
	
	//colision laika y auto
	public boolean hayColisionLaikaYAuto() {
		
		for(int i = 0; i < this.autos.length; i++) {
			if(this.autos[i] != null) {
				boolean colision = this.laika.getXLaika() + this.laika.getAncho() / 2 >= this.autos[i].getX() - this.autos[i].getAncho() / 2
						&& this.laika.getXLaika() - this.laika.getAncho() / 2 <= this.autos[i].getX() + this.autos[i].getAncho() / 2
						&& this.laika.getYLaika() + this.laika.getAlto() / 2 >= this.autos[i].getY() - this.autos[i].getAlto() / 2
						&& this.laika.getYLaika() - this.laika.getAlto() / 2 <= this.autos[i].getY() + this.autos[i].getAlto() / 2;
	
				if(colision) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	//colision laika y plantas
	public boolean hayColisionLaikaYPlantas() {
		for(int i = 0; i < this.plantas.length; i++) {
			if(this.plantas[i] != null) {
				this.colision = this.laika.getXLaika() + this.laika.getAncho() / 2 >= this.plantas[i].getXPlanta() - this.plantas[i].getDiametroPlanta() / 2
						&& this.laika.getXLaika() - this.laika.getAncho() / 2 <= this.plantas[i].getXPlanta() + this.plantas[i].getDiametroPlanta() / 2
						&& this.laika.getYLaika() + this.laika.getAlto() / 2 >= this.plantas[i].getYPlanta() - this.plantas[i].getDiametroPlanta()/ 2
						&& this.laika.getYLaika() - this.laika.getAlto() / 2 <= this.plantas[i].getYPlanta() + this.plantas[i].getDiametroPlanta() / 2;
				
				if(this.colision) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	//colision laika y bolas de fuego
	public boolean hayColisionLaikaYBolas() {
		for(int i = 0; i < this.fuego.length; i++) {
			if(this.fuego[i] != null) {
				this.colision = this.laika.getXLaika() + this.laika.getAncho() / 2 >= this.fuego[i].getX() - this.fuego[i].getDiametro() / 2
						&& this.laika.getXLaika() - this.laika.getAncho() / 2 <= this.fuego[i].getX() + this.fuego[i].getDiametro() / 2
						&& this.laika.getYLaika() + this.laika.getAlto() / 2 >= this.fuego[i].getY() - this.fuego[i].getDiametro()/ 2
						&& this.laika.getYLaika() - this.laika.getAlto() / 2 <= this.fuego[i].getY() + this.fuego[i].getDiametro() / 2;
				
				if(this.colision) {
					return true;
				}
			}
		}
		return false;
	}
	
	//colision laser y plantas
	public boolean hayColisionLaserYPlantas() {
		for(int i = 0; i < this.plantas.length; i++) {
			if(this.plantas[i] != null) {
				this.colision = this.laser.getX() + this.laser.getAncho() / 2 >= this.plantas[i].getXPlanta() - this.plantas[i].getDiametroPlanta() / 2
						&& this.laser.getX() - this.laser.getAncho() / 2 <= this.plantas[i].getXPlanta() + this.plantas[i].getDiametroPlanta() / 2
						&& this.laser.getY() + this.laser.getAlto() / 2 >= this.plantas[i].getYPlanta() - this.plantas[i].getDiametroPlanta()/ 2
						&& this.laser.getY() - this.laser.getAlto() / 2 <= this.plantas[i].getYPlanta() + this.plantas[i].getDiametroPlanta() / 2;
				
				if(this.colision) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	//colision laser y bola de fuego
	public boolean hayColisionLaserYFuego() {
		if(this.laser != null) {
			for(int i = 0; i < this.fuego.length; i++) {
				if(this.fuego[i] != null) {
					this.colision = this.laser.getX() + this.laser.getAncho() / 2 >= this.fuego[i].getX() - this.fuego[i].getDiametro() / 2
							&& this.laser.getX() - this.laser.getAncho() / 2 <= this.fuego[i].getX() + this.fuego[i].getDiametro() / 2
							&& this.laser.getY() + this.laser.getAlto() / 2 >= this.fuego[i].getY() - this.fuego[i].getDiametro()/ 2
							&& this.laser.getY() - this.laser.getAlto() / 2 <= this.fuego[i].getY() + this.fuego[i].getDiametro() / 2;
							
					if(this.colision) {
						this.fuego[i] = null;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//colision auto y bolas de fuego
	public boolean hayColisionAutoYFuego() {
		for(int i = 0; i < this.fuego.length; i++) {
			for(int j = 0; j < this.autos.length; j++) {
				if(this.fuego[i] != null && this.autos[j] != null) {
					this.colision = this.autos[j].getX() + this.autos[j].getAncho() / 2 >= this.fuego[i].getX() - this.fuego[i].getDiametro() / 2
							&& this.autos[j].getX() - this.autos[j].getAncho() / 2 <= this.fuego[i].getX() + this.fuego[i].getDiametro() / 2
							&& this.autos[j].getY() + this.autos[j].getAlto() / 2 >= this.fuego[i].getY() - this.fuego[i].getDiametro()/ 2
							&& this.autos[j].getY() - this.autos[j].getAlto() / 2 <= this.fuego[i].getY() + this.fuego[i].getDiametro() / 2;
					
					if(this.colision) {
						this.fuego[i] = null;
						this.autos[j] = null;
						return true;
					}
				}
			}	
		}
		return false;
	}
	
	//colision laser y autos
	public boolean hayColisionLaserYAuto() {
		
		for(int i = 0; i < this.autos.length; i++) {
			if(this.autos[i] != null) {
				boolean colision = this.laser.getX() + this.laser.getAncho() / 2 >= this.autos[i].getX() - this.autos[i].getAncho() / 2
						&& this.laser.getX() - this.laser.getAncho() / 2 <= this.autos[i].getX() + this.autos[i].getAncho() / 2
						&& this.laser.getY() + this.laser.getAlto() / 2 >= this.autos[i].getY() - this.autos[i].getAlto() / 2
						&& this.laser.getY() - this.laser.getAlto() / 2 <= this.autos[i].getY() + this.autos[i].getAlto() / 2;
	
				if(colision) {
					return true;
				}
			}
		}
		return false;
	}
	
	//crea las bolas de fuego
	public void crearboladefuegoD(int i) {
		
		if(this.laser == null && (tiempoActual - this.ultimoLanzamiento >= 0) && this.creadafuego0 == false) {	
	    	if(this.fuego[i] != null) {
	    		if(derfuego) {		    			
	    			if (this.fuego[i].getX() + (this.fuego[i].getDiametro()/2) >= 850) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}else {
	    			if (this.fuego[i].getX() + (this.fuego[i].getDiametro()/2) <= 0) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}
	    	}else {	
	    		this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    		this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    	}
	    }

	    if(this.fuego[i] != null) {
	        this.fuego[i].dibujar(this.entorno);
	        if(derfuego) {
	        	this.fuego[i].moverRight();
	        }else{	        	
	        	this.fuego[i].moverLeft();	
	        }
	    }
	}
	
	public void crearboladefuegoI(int i) {
		
		if(this.laser == null && (tiempoActual - this.ultimoLanzamiento >= 0) && this.creadafuego0 == false) {	
	    	if(this.fuego[i] != null) {
	    		if(izqfuego) {		    			
	    			if (this.fuego[i].getX() + (this.fuego[i].getDiametro()/2) <= 0) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}else {
	    			if (this.fuego[i].getX() + (this.fuego[i].getDiametro()/2) >= 850) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}
	    	}else {	
	    		this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    		this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    	}
	    }

	    if(this.fuego[i] != null) {
	        this.fuego[i].dibujar(this.entorno);
	        if(izqfuego) {
	        	this.fuego[i].moverLeft();
	        }else{	        	
	        	this.fuego[i].moverRight();
	        }
	    }
	}
	
	public void crearboladefuegoU(int i) {
		
		if(this.laser == null && (tiempoActual - this.ultimoLanzamiento >= 0) && this.creadafuego0 == false) {	
	    	if(this.fuego[i] != null) {
	    		if(arribafuego) {		    			
	    			if (this.fuego[i].getY() + (this.fuego[i].getDiametro()/2) <= 0) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}else {
	    			if (this.fuego[i].getY() + (this.fuego[i].getDiametro()/2) >= 650) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}
	    	}else {	
	    		this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    		this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    	}
	    }

	    if(this.fuego[i] != null) { 
	        this.fuego[i].dibujar(this.entorno);
	        if(arribafuego) {
	        	this.fuego[i].moverUp();
	        }else{	        	
	        	this.fuego[i].moverDown();
	        }
	    }
	}
	
	public void crearboladefuegoDown(int i) {
		
		if(this.laser == null && (tiempoActual - this.ultimoLanzamiento >= 0) && this.creadafuego0 == false) {	
	    	if(this.fuego[i] != null) {
	    		if(abajofuego) {		    			
	    			if (this.fuego[i].getY() + (this.fuego[i].getDiametro()/2) >= 650) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}else {
	    			if (this.fuego[i].getY() + (this.fuego[i].getDiametro()/2) <= 0) {
	    				this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    				this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    			}
	    		}
	    	}else {	
	    		this.fuego[i] = (Fuego) this.plantas[i].lanzarFuego();
	    		this.ultimoLanzamiento = tiempoActual; // Actualizar el tiempo del último lanzamiento
	    	}
	    }

	    if(this.fuego[i] != null) {
	        this.fuego[i].dibujar(this.entorno);
	        if(abajofuego) {
	        	this.fuego[i].moverDown();
	        }else{	        	
	        	this.fuego[i].moverUp();
	        }
	    }
	}
	
	//verifica la cantidad de plantas que hay en la pantalla
	public boolean hayMenosDeCuatroPlantas() {
		int cont = 0;
		for(int i = 0; i < plantas.length; i++) {
			if(plantas[i] != null) {
				cont++;
			}
			if(cont < 4) {
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
