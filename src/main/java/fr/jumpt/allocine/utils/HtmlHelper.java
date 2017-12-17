package fr.jumpt.allocine.utils;

/**
 * Important links to Allocine website
 *
 * @author Julien
 */
public enum HtmlHelper {

    META_BODY_HELPER("div.meta-body-item"),
    TITLE_BAR("div.titlebar-title.titlebar-title-lg"),
    SYNOPSIS("#synopsis-details > div.synopsis-txt"),
    AGE_LIMIT("#synopsis-details > span"),
    POSTER("#row-col-sticky > div > div.card.card-entity.card-movie-overview.row.row-col-padded-10.cf > figure > a > img"),
    POSTER_DIV("#row-col-sticky > div > div.card.card-entity.card-movie-overview.row.row-col-padded-10.cf > figure > div > img"),
    DETAIL("#synopsis-details > div.ovw-synopsis-info > div.more-hidden > div");


    private String value;

    private HtmlHelper(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
