package fr.jumpt.allocine;

import fr.jumpt.allocine.models.Movie;
import fr.jumpt.allocine.workers.MovieWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Julien
 */
public class Application {

    private final static int MAX_THREAD = 256;

    private List<Movie> movies;

    private Application() {
        movies = new ArrayList<>();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

    private void start() {
        System.out.println("Application started !");

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD);
        List<Callable<Object>> tasks = new ArrayList<>();

        for (int i = 0; i < MAX_THREAD; i++) {

            final int currentThreadCount = i;

            Runnable task = () -> {
                MovieWorker.init(MAX_THREAD, currentThreadCount).load();
            };

            tasks.add(Executors.callable(task));
        }

        try {
            executor.invokeAll(tasks);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();

            System.out.println("End of loading with " + movies.size());
        }

    }

}
