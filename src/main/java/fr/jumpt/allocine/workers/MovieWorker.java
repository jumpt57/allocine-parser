package fr.jumpt.allocine.workers;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.jumpt.allocine.models.Movie;
import fr.jumpt.allocine.processors.MovieProcessor;
import fr.jumpt.allocine.utils.HtmlHelper;
import fr.jumpt.allocine.utils.KeyWordsHelper;
import fr.jumpt.allocine.utils.LinksHelper;

/**
 * 
 * @author Julien
 *
 */
public class MovieWorker implements Worker {
		
	private Integer currentMovieId = 1;
	
	private Integer timeout = 10;
	
	/**
	 * 
	 */
	public void load() {
		System.out.println("loading movies ...");
		
		boolean lastMovieFound = false;
		
		while(!lastMovieFound) {
			String movieUrl = LinksHelper.FICHE_FILM.getValue().replace("{id}", currentMovieId.toString());
			
			try {
				processHtml(Jsoup.connect(movieUrl).get());
				
				timeout = 10;
			} catch (Exception e) {			
				
				timeout--;
				
				if(timeout <= 0) {
					lastMovieFound = true;
				}				
				
			}
			
			currentMovieId++;
		}
		
		System.out.println("loading over !");		
	}	

	/**
	 * 
	 */
	public void load(Integer paramId) {
		String movieUrl = LinksHelper.FICHE_FILM.getValue().replace("{id}", currentMovieId.toString());
		
		try {
			processHtml(Jsoup.connect(movieUrl).get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void processHtml(final Document document) {
		Movie movie = new Movie();
		movie.setId(currentMovieId);
		
		Element titlebar = document.selectFirst(HtmlHelper.TITLEBAR.getValue());
		movie.setTitle(MovieProcessor.title(titlebar));
		
		Elements metaBody = document.select(HtmlHelper.META_BODY_HELPER.getValue());		
		for(Element el : metaBody) {
			if(el.text().contains(KeyWordsHelper.DATE_DE_REPRISE.getValue())) {
				movie.setReReleaseDate(MovieProcessor.reReleaseDate(el));
			} 
			else if(el.text().contains(KeyWordsHelper.DATE_DE_SORTIE.getValue())) {
				movie.setReleaseDate(MovieProcessor.releaseDate(el));
				movie.setLength(MovieProcessor.length(el));
			} 
			else if(el.text().contains(KeyWordsHelper.DE.getValue())) {
				movie.setDirectors(MovieProcessor.directors(metaBody.get(1)));
			}
			else if(el.text().contains(KeyWordsHelper.AVEC.getValue())) {
				movie.setActors(MovieProcessor.actors(el));
			}
			else if(el.text().contains(KeyWordsHelper.GENRES.getValue())) {
				movie.setGenres(MovieProcessor.genres(el));
			}
			else if(el.text().contains(KeyWordsHelper.NATIONALITE.getValue())) {
				movie.setNationalities(MovieProcessor.nationalities(el));
			}
		}
		
		Element synopsis = document.selectFirst(HtmlHelper.SYNOPSIS.getValue());
		movie.setSynopsis(MovieProcessor.synopsis(synopsis));
		
		Element ageLimit = document.selectFirst(HtmlHelper.AGE_LIMIT.getValue());
		movie.setAgeLimit(MovieProcessor.ageLimit(ageLimit));

		Element poster = document.selectFirst(HtmlHelper.POSTER.getValue());
		movie.setPoster(MovieProcessor.poster(poster));
				
		System.out.println(movie.toString());
	}

}
