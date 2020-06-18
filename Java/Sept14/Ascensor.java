package semaforosSept2014;
import java.util.concurrent.*;
/**
 * Clase del ascensor que tiene capacidad para una persona
 */
public class Ascensor{
	
	
	private int sig = 1; // para saber si el ascensor sube o baja
	private int planta = 0; //planta en la que se encuentra el ascensor
	private int numPlantas;
	private Semaphore mutex= new Semaphore(1, true), ascensorLibre = new Semaphore (0,true);
	boolean ocupado = false;
	
	private int cEspera;
	private int cFuera;
	
	public Ascensor(int C){
	// C es el numero de plantas del edificio
		numPlantas = C;
	
	}
	
	public void clienteEsperaAscensor (int orig, int id) throws InterruptedException {
		mutex.acquire();
		while (ocupado) {
			System.out.println("El cliente "+id+" con origen "+orig+" en espera.");
			cEspera++;
			mutex.release();
			ascensorLibre.acquire();
			mutex.acquire();
		}
		System.out.println("El cliente "+id+" con origen "+orig+" ha entrado.");
		mutex.release();
		
	}
	
	public void clienteSubeAscensor (int dest, int id) throws InterruptedException {
		mutex.acquire();
		System.out.println("El cliente "+id+" con destino "+dest+" ha subido.");
		ocupado=true;
		mutex.release();
	
	}
	
	public void clienteBajaAscensor (int dest, int id) throws InterruptedException {
		mutex.acquire();
		ocupado=false;
		while (cEspera!=0) {
			ascensorLibre.release();
			cEspera--;
		}
		System.out.println("El cliente "+id+" con destino "+dest+" ha bajado.");
		mutex.release();
	}
	

	
	

}	
	
	
