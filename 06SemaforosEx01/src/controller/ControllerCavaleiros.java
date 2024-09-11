package controller;
import java.util.concurrent.*;
import java.util.Random;

public class ControllerCavaleiros extends Thread {  
	private static Semaphore semaforoTocha = new Semaphore (1);
	private static Semaphore semaforoPedra = new Semaphore (1);
	private static Semaphore semaforoPorta = new Semaphore (4);
	
	private int tidCavaleiro;
	private double velocidade = 1;
	private int posicao = 0;
	
	private Random random = new Random();
	private static final int corredor = 2000;
	private static final int posicaoTocha = 500;
	private static final int posicaoPedra = 1500;
	private static final int portaCerta = 3;
	
	
	public ControllerCavaleiros(int tidCavaleiro) {
		this.tidCavaleiro = tidCavaleiro;
		
		
	}
	
	public void run() {
		try {
			Calc();
		} catch (InterruptedException e) {	
			System.err.println(e.getMessage());
		}
	}
	
	public void Calc () throws InterruptedException {
		try {
			while (posicao < corredor) {
				CavaleiroAnda();
				
				if ((posicao >= posicaoTocha) && (posicao < posicaoPedra) && semaforoTocha.tryAcquire()) {
					velocidade =+ 2;
					System.out.println ("Cavaleiro #" + tidCavaleiro + " pegou a tocha. Velocidade = " + velocidade);
				}
				
				if ((posicao >= posicaoPedra) && semaforoPedra.tryAcquire()) {
					velocidade =+ 2;
					System.out.println ("Cavaleiro #" + tidCavaleiro + " pegou a pedra. Velocidade = " + velocidade);
				}
				
				Thread.sleep(50);
			}
			
			Portas();
			
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public synchronized void CavaleiroAnda () {
		posicao += velocidade;
		
	}
	
	public void Portas () throws InterruptedException {
		semaforoPorta.acquire();
		int numeroPorta = (random.nextInt(4)+1);
		
		if (numeroPorta == portaCerta) {
			System.out.println("Porta Certa! Cavaleiro #" + tidCavaleiro + " venceu!");
		}
			else {
				System.out.println("Porta Errada! Cavaleiro #" + tidCavaleiro + " morreu!");
			
		}
		
		semaforoPorta.release();
		
	}
	

}
