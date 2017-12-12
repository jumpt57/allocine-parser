package fr.jumpt.allocine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.jumpt.allocine.models.Movie;
import fr.jumpt.allocine.workers.MovieWorker;

/**
 * 
 * @author Julien
 *
 */
public class Application {

	public final static int MAX_THREAD = 256;

	private List<Movie> movies;

	public static void main(String[] args) {
		Application app = new Application();
		app.start();
	}

	public Application() {
		movies = new ArrayList<>();
	}

	public void start() {
		System.out.println("Application started !");

		ExecutorService excutor = Executors.newFixedThreadPool(MAX_THREAD);
		List<Callable<List<Movie>>> tasks = new ArrayList<>();

		for (int i = 0; i < MAX_THREAD; i++) {

			final int currentThreadCount = i;

			Callable<List<Movie>> task = () -> {

				MovieWorker movieWorker = new MovieWorker(Thread.currentThread(), MAX_THREAD, currentThreadCount);
				return movieWorker.load();
			};

			tasks.add(task);
		}

		try {
			List<Future<List<Movie>>> futures = excutor.invokeAll(tasks);

			for (Future<List<Movie>> future : futures) {
				try {
					movies.addAll(future.get());
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			excutor.shutdownNow();

			System.out.println("End of loading with " + movies.size());
		}

	}

}
