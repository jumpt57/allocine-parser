package fr.jumpt.allocine;

import fr.jumpt.allocine.workers.MovieWorker;

/**
 * 
 * @author Julien
 *
 */
public class Application {
	
	private MovieWorker movieWorker = new MovieWorker();

	public static void main(String[] args) {
		Application app = new Application();
		app.start();		
	}
	
	public void start() {
		System.out.println("Application started !");

		movieWorker.load();		
		
		System.out.println("End of loading");
	}

}
