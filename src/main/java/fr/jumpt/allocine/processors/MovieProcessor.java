package fr.jumpt.allocine.processors;

import fr.jumpt.allocine.utils.KeyWordsHelper;
import fr.jumpt.allocine.utils.MonthHelper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author Julien
 */
public class MovieProcessor {

    /**
     * @param element
     * @return
     */
    public static String title(Element element) {
        if (element == null) {
            return null;
        }

        return element.text();
    }

    /**
     * @param element
     * @return
     */
    public static Date reReleaseDate(Element element) {

        if (element == null) {
            return null;
        }

        try {
            String data = element.text();
            String[] dataArray = data.split("\\(");
            String releaseDateString = dataArray[0];

            if (releaseDateString.contains(KeyWordsHelper.THREED.getValue())) {
                dataArray = releaseDateString.split(KeyWordsHelper.DASH.getValue());

                releaseDateString = dataArray[0].trim();
            }


            releaseDateString = releaseDateString.replace(KeyWordsHelper.DATE_DE_REPRISE.getValue(), "");
            releaseDateString = releaseDateString.trim();

            String[] fullStringDate = releaseDateString.split(" ");
            String day = fullStringDate[0];
            String month = MonthHelper.getMonthNumber(fullStringDate[1]);
            String year = fullStringDate[2];

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            Date date = format.parse(day + "/" + month + "/" + year);

            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param element
     * @return
     */
    public static Date releaseDate(Element element) {

        if (element == null) {
            return null;
        }

        try {
            String data = element.text();

            if (data.contains(KeyWordsHelper.INCONNUE.getValue()) || data.contains(KeyWordsHelper.VOD.getValue())) {
                return null;
            }

            String[] dataArray = data.split("\\(");
            String releaseDateString = dataArray[0];
            releaseDateString = releaseDateString.replace(KeyWordsHelper.DATE_DE_SORTIE.getValue(), "");
            releaseDateString = releaseDateString.trim();

            try {
                String[] fullStringDate = releaseDateString.split(" ");

                String day;
                String month;
                String year;
                if (fullStringDate.length == 1) {
                    day = "1";
                    month = "1";
                    year = fullStringDate[0];
                } else if (fullStringDate.length == 2) {
                    day = "1";
                    month = MonthHelper.getMonthNumber(fullStringDate[0]);
                    year = fullStringDate[1];
                } else {
                    day = fullStringDate[0];
                    month = MonthHelper.getMonthNumber(fullStringDate[1]);
                    year = fullStringDate[2];
                }

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
                Date date = format.parse(day + "/" + month + "/" + year);

                return date;
            } catch (Exception e) {
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param element
     * @return
     */
    public static List<String> directors(Element element) {

        if (element == null) {
            return null;
        }

        String data = element.text();
        String[] dataArray = data.replace(KeyWordsHelper.DE.getValue(), "").trim().split("\\,");

        return Arrays.asList(dataArray).stream().map(director -> director.trim()).collect(Collectors.toList());
    }

    /**
     * @param element
     * @return
     */
    public static List<String> actors(Element element) {

        if (element == null) {
            return null;
        }

        String data = element.text();
        String[] dataArray = data.replace(KeyWordsHelper.AVEC.getValue(), "")
                .replace(KeyWordsHelper.PLUS.getValue(), "").trim().split("\\,");

        return Arrays.asList(dataArray).stream().map(actor -> actor.trim()).collect(Collectors.toList());
    }

    /**
     * @param element
     * @return
     */
    public static String length(Element element) {

        if (element == null) {
            return null;
        }

        String data = element.text();
        String[] dataArray = data.split("\\(");

        String length = dataArray[1].replaceAll("\\)", "").trim();

        return length;
    }

    /**
     * @param element
     * @return
     */
    public static List<String> genres(Element element) {

        if (element == null) {
            return null;
        }

        String data = element.text();

        String[] dataArray = data.replace(KeyWordsHelper.GENRE.getValue(), "").split("\\,");

        if(data.contains(KeyWordsHelper.GENRES.getValue())){
            dataArray = data.replace(KeyWordsHelper.GENRES.getValue(), "").split("\\,");
        }

        return Arrays.asList(dataArray).stream().map(genre -> genre.trim()).collect(Collectors.toList());
    }

    /**
     * @param element
     * @return
     */
    public static List<String> nationalities(Element element) {

        if (element == null) {
            return null;
        }

        String data = element.text();
        String[] dataArray = data.replace(KeyWordsHelper.NATIONALITE.getValue(), "").split("\\,");

        return Arrays.asList(dataArray).stream().map(nationality -> StringUtils.capitalize(nationality.trim())).collect(Collectors.toList());
    }

    /**
     * @param element
     * @return
     */
    public static String synopsis(Element element) {

        if (element == null) {
            return null;
        }

        return element.text();
    }

    /**
     * @param element
     * @return
     */
    public static String ageLimit(Element element) {

        if (element == null) {
            return null;
        }

        return element.text();
    }

    /**
     * @param element
     * @return
     */
    public static String poster(Element element) {

        if (element == null) {
            return null;
        }

        return element.absUrl("src");
    }

    /**
     *
     * @param element
     * @return
     */
    public static Integer year(Element element){

        if (element == null) {
            return null;
        }

        return Integer.parseInt(element.text().replace(KeyWordsHelper.ANNEE_PRODUCTION.getValue(), "").trim());
    }

    /**
     *
     * @param element
     * @return
     */
    public static String type(Element element) {

        if (element == null) {
            return null;
        }

        return element.text().replace(KeyWordsHelper.TYPE_FILM.getValue(), "").trim();
    }

}
