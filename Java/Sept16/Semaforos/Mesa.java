package Semaforos;

import java.util.concurrent.*;
public class Mesa {
	private int numjugadores, ganador, auxiliar;
	private Boolean [] tiradas = new Boolean [5];
	private Semaphore mutex = new Semaphore(1, true), semJugadores = new Semaphore(0, true);
	
	public Mesa(int N){
		numjugadores = N;
		auxiliar= N;
	}
	
	/**
	 * 
	 * @param id del jugador que llama al método
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ningún jugador 
	 * ha ganado y hay que repetir la partida. Si  devuelve un número menor que N, es el id del
	 * jugador ganador.
	 * 
	 * Un jugador llama al método nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el último, espera a que el resto de 
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El último jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *  
	 */
	
	public int ComprobarGanador(Boolean [] t){
		int trues=0;
		int falses=0;
		for (int i = 0; i<t.length;i++) {
			if (t[i].equals(true)) {
				trues++;
			} else {
				falses++;
			}
		}
		if(trues==1){
			for (int i = 0; i<t.length;i++) {
				if (t[i].equals(true)) {
					System.out.println("El ganador es "+i);
					return i;
				} 
			}
			
		}else if(falses ==1){
			for (int i = 0; i<t.length;i++) {
				if (t[i].equals(false)) {
					System.out.println("El ganador es "+i);
					return i;
				} 
			}
		}
		System.out.println("No hay ganadores");	
		return auxiliar;
		
	}
	
	
	
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		mutex.acquire();
		while(numjugadores>0){
			if(numjugadores!=1){
				System.out.println("El jugador con id "+id+" coloca la tirada "+cara);
				tiradas[id]=cara;
				
			}else{
			System.out.println("El ultimo jugador es "+id+" con la tirada "+cara);
			tiradas[id]=cara;
			ganador= ComprobarGanador(tiradas);
			}			
		numjugadores--;

		mutex.release();
		
		mutex.acquire();
		}
		
		
		mutex.release();

		return ganador;
	}
}
