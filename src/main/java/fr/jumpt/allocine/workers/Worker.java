package fr.jumpt.allocine.workers;

import org.jsoup.nodes.Document;

/**
 * 
 * @author Julien
 *
 */
public interface Worker {

	void load();
	
	void load(Integer paramId);
	
	void processHtml(final Document document);
	
}
