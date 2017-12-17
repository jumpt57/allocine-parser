package fr.jumpt.allocine.utils;

/**
 * @author Julien
 */
public enum MonthHelper {

    JANVIER("01", "janvier"),
    FEVRIER("02", "février"),
    MARS("03", "mars"),
    AVRIL("04", "avril"),
    MAI("05", "mai"),
    JUIN("06", "juin"),
    JUILLET("07", "juillet"),
    AOUT("08", "août"),
    SEPTEMBRE("09", "septembre"),
    OCTOBRE("10", "octobre"),
    NOVEMBRE("11", "novembre"),
    DECEMBRE("12", "décembre");

    private String value;
    private String name;

    private MonthHelper(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * @param monthName
     * @return
     */
    public static String getMonthNumber(String monthName) {
        for (MonthHelper month : MonthHelper.values()) {
            if (month.name.toUpperCase().equalsIgnoreCase(monthName.toUpperCase())) {
                return month.value;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
