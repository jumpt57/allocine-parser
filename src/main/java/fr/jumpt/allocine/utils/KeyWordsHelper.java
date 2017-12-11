package fr.jumpt.allocine.utils;

/**
 * 
 * @author Julien
 *
 */
public enum KeyWordsHelper {
	
	DATE_DE_SORTIE("Date de sortie"),
	DATE_DE_REPRISE("Date de reprise"),
	DE("De"),
	AVEC("Avec"),
	GENRES("Genres"),
	NATIONALITE("Nationalité"),
	THREED("3D"),
	DASH("-"),
	PLUS("plus"),
	INCONNUE("inconnue "),
	VOD("VOD");
	
	private String value;
	
	private KeyWordsHelper(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
