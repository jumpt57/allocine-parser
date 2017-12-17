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
    private Integer max;

    private Integer currentMovieId;
    private Integer step;

    /**
     * @param step
     * @param startingMovieId
     */
    private MovieWorker(final Integer step, Integer startingMovieId, Integer max) {
        this.step = step;
        this.currentMovieId = startingMovieId;
        this.max = max;
    }

    /**
     * @param step
     * @param startingMovieId
     * @return
     */
    public static MovieWorker init(Integer step, Integer startingMovieId, Integer max) {
        return new MovieWorker(step, startingMovieId, max);
    }

    /**
     *
     */
    @Override
    public void load() {
        System.out.println("starting worker on thrad " + Thread.currentThread().getName());

        boolean lastMovieFound = false;

        while (!lastMovieFound && !movieNumber.equals(max)) {
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
    @Override
    public Movie processHtml(final Document document) {
        Movie movie = new Movie();
        movie.setId(currentMovieId);

        Element titleBar = document.selectFirst(HtmlHelper.TITLE_BAR.getValue());
        movie.setTitle(MovieProcessor.title(titleBar));

        document.select(HtmlHelper.META_BODY_HELPER.getValue()).stream().forEach(el -> {
            if (el.text().startsWith(KeyWordsHelper.DATE_DE_REPRISE.getValue())) {
                movie.setReReleaseDate(MovieProcessor.reReleaseDate(el));
            } else if (el.text().startsWith(KeyWordsHelper.DATE_DE_SORTIE.getValue())) {
                movie.setReleaseDate(MovieProcessor.releaseDate(el));
                movie.setLength(MovieProcessor.length(el));
            } else if (el.text().startsWith(KeyWordsHelper.DE.getValue())) {
                movie.setDirectors(MovieProcessor.directors(el));
            } else if (el.text().startsWith(KeyWordsHelper.AVEC.getValue())) {
                movie.setActors(MovieProcessor.actors(el));
            } else if (el.text().startsWith(KeyWordsHelper.GENRE.getValue())) {
                movie.setGenres(MovieProcessor.genres(el));
            } else if (el.text().startsWith(KeyWordsHelper.NATIONALITE.getValue())) {
                movie.setNationalities(MovieProcessor.nationalities(el));
            }
        });

        Element synopsis = document.select(HtmlHelper.SYNOPSIS.getValue()).first();
        movie.setSynopsis(MovieProcessor.synopsis(synopsis));

        Element ageLimit = document.select(HtmlHelper.AGE_LIMIT.getValue()).first();
        movie.setAgeLimit(MovieProcessor.ageLimit(ageLimit));

        Element poster = document.select(HtmlHelper.POSTER.getValue()).first();
        if(poster == null){
            poster = document.select(HtmlHelper.POSTER_DIV.getValue()).first();
        }
        movie.setPoster(MovieProcessor.poster(poster));

        document.select(HtmlHelper.DETAIL.getValue()).stream().forEach(el -> {
            if(el.text().startsWith(KeyWordsHelper.ANNEE_PRODUCTION.getValue())){
                movie.setYear(MovieProcessor.year(el));
            }
            else if(el.text().startsWith(KeyWordsHelper.TYPE_FILM.getValue())){
                movie.setType(MovieProcessor.type(el));
            }
        });

        System.out.println(Thread.currentThread().getName() + " " + movie.toString());

        return movie;
    }

}
