package fr.jumpt.allocine.workers;

import org.jsoup.nodes.Document;

/**
 * @author Julien
 */
public interface Worker<T> {

    void load();

    T processHtml(final Document document);

}
