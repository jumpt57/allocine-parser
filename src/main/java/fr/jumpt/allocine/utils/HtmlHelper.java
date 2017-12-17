package fr.jumpt.allocine.utils;

/**
 * Important links to Allocine website
 *
 * @author Julien
 */
public enum HtmlHelper {

    META_BODY_HELPER("div.meta-body-item"),
    TITLEBAR("div.titlebar-title.titlebar-title-lg"),
    SYNOPSIS("synopsis-txt"),
    AGE_LIMIT("synopsis-certificate"),
    POSTER("thumbnail-img");

    private String value;

    private HtmlHelper(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
