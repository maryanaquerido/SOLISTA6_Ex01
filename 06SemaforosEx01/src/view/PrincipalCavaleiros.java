package view;
import controller.ControllerCavaleiros;

public class PrincipalCavaleiros {
	public static void main (String[] args) { 
		
		for (int i = 0; i<4; i++) {
		ControllerCavaleiros thread = new ControllerCavaleiros(i);
		thread.start();
		}
	}
}
