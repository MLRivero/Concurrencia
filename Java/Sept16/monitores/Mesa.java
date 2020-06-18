package monitores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
public class Mesa {
	private Map<Jugador,Boolean> _jugadas;
	private int numJugadores;
	
	public Mesa(int N){
		numJugadores=N;
		_jugadas=new HashMap();
	}
	
	/**
	 * 
	 * @param id del jugador que llama al método
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @param jugador 
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
	public int nuevaJugada(int id,boolean cara, Jugador jugador) throws InterruptedException{
		
		if(id< numJugadores-1){
		jugador.wait();
		}
		
		añadirJugada(jugador, cara);
		
		if(_jugadas.size()==numJugadores){
			jugador.wait();
			comprobarGanador();
		}
			
		return 0;
	}

	private synchronized void añadirJugada(Jugador jugador, Boolean jugada) {
		_jugadas.put(jugador, jugada);
		
	}

	private void comprobarGanador() {
		_jugadas.get(key)
	}	
		
		
	
}
