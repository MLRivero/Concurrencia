package monitores;

import java.util.Random;

public class Jugador extends Thread{
	
	private static Random r = new Random();
	private Mesa mesa;
	private int id, N;
	
	
	public Jugador(int id,Mesa mesa, int N){
		this.id = id;
		this.mesa = mesa;
		this.N = N;
	}

	
	public void run(){
		boolean cara = r.nextBoolean();
		int ganador = N;
		try{
			while (ganador == N){
				ganador = mesa.nuevaJugada(id, cara, this);
				cara = r.nextBoolean();
			}

		}catch (InterruptedException ie) {

		}
	}

	

	public int getJugadorId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
}
