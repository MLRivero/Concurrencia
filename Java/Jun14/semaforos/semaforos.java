package semaforos;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class semaforos {
	private static int[] habitantes = new int [60];
	
	private static Semaphore [] semSitiosHelicoptero = new Semaphore [6];
	private static Random r = new Random();
	private static Semaphore semaforo = new Semaphore (0, true);
	public static class Habitante extends Thread{
		
	}
	
	public static class Helicoptero extends Thread {
		public void run() {
			while (true) {
				try {
					semaforo.acquire(6);
					semSitiosHelicoptero[0].release();
					semSitiosHelicoptero[1].release();
					semSitiosHelicoptero[2].release();
					semSitiosHelicoptero[3].release();
					semSitiosHelicoptero[4].release();
					semSitiosHelicoptero[5].release();
				}
			}
		}
	}
}
