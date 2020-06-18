package aseos;

import java.util.concurrent.Semaphore;

public class AseosInjsuto {
	private Semaphore mutex= new Semaphore (1, true), banioLibre = new Semaphore (0, true),
			puedeEntrarEq = new Semaphore (0, true);
	private boolean equipoDentro = false;
	private boolean equipoEsperando=false;
	private int cEspera=0;
	private int ocupacion=0;

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos CS Version
	 * injusta: El cliente espera si el equipo de limpieza está trabajando CS
	 * Version justa: El cliente espera si el equipo de limpieza está trabajando
	 * o está esperando para poder limpiar los aseos
	 * @throws InterruptedException 
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		mutex.acquire();
		while(equipoDentro) {
			cEspera++;
			System.out.println("Cliente "+id+ " en espera.");
			mutex.release();
			banioLibre.acquire();
			mutex.acquire();
		}
		System.out.println("Cliente "+id+ " ha entrado.");
		ocupacion++;
		mutex.release();
		
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * @throws InterruptedException 
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();
		System.out.println("Cliente "+id+ " ha salido.");
		ocupacion--;
		if (ocupacion==0 && equipoEsperando) {
			puedeEntrarEq.release();
		}
		mutex.release();

	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos CS:
	 * El equipo de trabajo está solo en los aseos, es decir, espera hasta que
	 * no haya ningún cliente.
	 * @throws InterruptedException 
	 * 
	 */
	public void entraEquipoLimpieza() throws InterruptedException {
		mutex.acquire();
		while (ocupacion>0) {
			
			System.out.println("Equipo de limpieza en espera.");
			equipoEsperando=true;
			mutex.release();
			puedeEntrarEq.acquire();
			mutex.acquire();
		}
		System.out.println("Equipo de limpieza ha entrado.");
		equipoDentro=true;
		equipoEsperando=false;
		mutex.release();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * @throws InterruptedException 
	 * 
	 * 
	 */
	public void saleEquipoLimpieza() throws InterruptedException {
		mutex.acquire();
		equipoDentro=false;
		System.out.println("Equipo de limpieza ha salido.");
		while(cEspera>0) {
			banioLibre.release();
			cEspera--;
			
		}
		mutex.release();

		
	}
}
