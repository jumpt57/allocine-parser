package fr.jumpt.allocine.workers;

import fr.jumpt.allocine.dba.Dba;
import fr.jumpt.allocine.models.Movie;
import fr.jumpt.allocine.processors.MovieProcessor;
import fr.jumpt.allocine.utils.HtmlHelper;
import fr.jumpt.allocine.utils.KeyWordsHelper;
import fr.jumpt.allocine.utils.LinksHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Julien
 */
public class MovieWorker implements Worker<Movie> {

    private Integer movieNumber = 0;
    private Integer timeout = 10;

    private Integer currentMovieId;
    private Integer step;

    /**
     * @param step
     * @param startingMovieId
     */
    private MovieWorker(final Integer step, final Integer startingMovieId) {
        this.step = step;
        this.currentMovieId = startingMovieId;
    }

    /**
     * @param step
     * @param startingMovieId
     * @return
     */
    public static MovieWorker init(final Integer step, final Integer startingMovieId) {
        return new MovieWorker(step, startingMovieId);
    }

    /**
     *
     */
    public void load() {
        System.out.println("starting worker on thrad " + Thread.currentThread().getName());

        boolean lastMovieFound = false;

        while (!lastMovieFound && movieNumber != 10) {
            String movieUrl = LinksHelper.FICHE_FILM.getValue().replace("{id}", currentMovieId.toString());

            try {
                Dba.get().getDatastore().save(processHtml(Jsoup.connect(movieUrl).get()));

                movieNumber++;
                timeout = 10;
            } catch (Exception e) {

                timeout--;

                if (timeout <= 0) {
                    lastMovieFound = true;
                }

            }

            currentMovieId = currentMovieId + step;
        }

        System.out.println("loading over !");
    }

    /**
     *
     */
    public Movie processHtml(final Document document) {
        Movie movie = new Movie();
        movie.setId(currentMovieId);

        Element titleBar = document.selectFirst(HtmlHelper.TITLEBAR.getValue());
        movie.setTitle(MovieProcessor.title(titleBar));

        Elements metaBody = document.select(HtmlHelper.META_BODY_HELPER.getValue());
        for (Element el : metaBody) {
            if (el.text().contains(KeyWordsHelper.DATE_DE_REPRISE.getValue())) {
                movie.setReReleaseDate(MovieProcessor.reReleaseDate(el));
            } else if (el.text().contains(KeyWordsHelper.DATE_DE_SORTIE.getValue())) {
                movie.setReleaseDate(MovieProcessor.releaseDate(el));
                movie.setLength(MovieProcessor.length(el));
            } else if (el.text().contains(KeyWordsHelper.DE.getValue())) {
                movie.setDirectors(MovieProcessor.directors(metaBody.get(1)));
            } else if (el.text().contains(KeyWordsHelper.AVEC.getValue())) {
                movie.setActors(MovieProcessor.actors(el));
            } else if (el.text().contains(KeyWordsHelper.GENRES.getValue())) {
                movie.setGenres(MovieProcessor.genres(el));
            } else if (el.text().contains(KeyWordsHelper.NATIONALITE.getValue())) {
                movie.setNationalities(MovieProcessor.nationalities(el));
            }
        }

        Element synopsis = document.selectFirst(HtmlHelper.SYNOPSIS.getValue());
        movie.setSynopsis(MovieProcessor.synopsis(synopsis));

        Element ageLimit = document.selectFirst(HtmlHelper.AGE_LIMIT.getValue());
        movie.setAgeLimit(MovieProcessor.ageLimit(ageLimit));

        Element poster = document.selectFirst(HtmlHelper.POSTER.getValue());
        movie.setPoster(MovieProcessor.poster(poster));

        System.out.println(Thread.currentThread().getName() + " " + movie.toString());

        return movie;
    }

}
