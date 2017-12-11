package fr.jumpt.allocine.utils;

/**
 * Important links to Allocine website
 * @author jumpt57
 *
 */
public enum LinksHelper {
	
	FICHE_FILM("http://www.allocine.fr/film/fichefilm_gen_cfilm={id}.html");
	
	private String value;
	
	private LinksHelper(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
