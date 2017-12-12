package fr.jumpt.allocine.workers;

import java.util.List;

import org.jsoup.nodes.Document;

/**
 * 
 * @author Julien
 *
 */
public interface Worker<T> {

	List<T> load();
		
	void processHtml(final Document document);
	
}
