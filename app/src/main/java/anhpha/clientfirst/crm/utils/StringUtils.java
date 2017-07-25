package anhpha.clientfirst.crm.utils;

import android.util.Patterns;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Nguyen on 4/25/2015.
 */
public class StringUtils {

    public static String fixUrl(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }

        int i = url.lastIndexOf("http://");
        if (i != 0) {
            return url.substring(i);
        }
        return url;
    }

    public static String formatServerDateTime(Calendar calendar, boolean time) {
        if (calendar == null) {
            return null;
        }
        try {
            if (time) {
                return String.format("%02d/%02d/%02d %02d:%02d", calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            } else {
                return String.format("%02d/%02d/%02d", calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR));
            }
        } catch (Exception ex) {
            LogUtils.e(StringUtils.class.getSimpleName(), "formatServerDateTime", ex);
            return "";
        }
    }

    public static String formatTime(Calendar calendar) {
        if (calendar == null) {
            return "";
        }
        try {
            return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        } catch (Exception ex) {
            LogUtils.e(StringUtils.class.getSimpleName(), "formatClientDateTime", ex);
            return "";
        }
    }

    public static String formatClientDateTime(Calendar calendar, boolean time) {
        if (calendar == null) {
            return "";
        }
        try {
            if (time) {
                return String.format("%02d/%02d/%02d %02d:%02d", calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            } else {
                return String.format("%02d/%02d/%02d", calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            }
        } catch (Exception ex) {
            LogUtils.e(StringUtils.class.getSimpleName(), "formatClientDateTime", ex);
            return "";
        }
    }

    public static boolean isDate(String s) {
        return Utils.tryParseDate(s) == null ? false : true;
    }

    public static boolean isDateTime(String s) {
        return Utils.tryParseDateTime(s) == null ? false : true;
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static String getExtension(String path, boolean withOutDot) {

        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        } else {
            return "";
        }

        return (withOutDot) ? extension : "." + extension;
    }

    /**
     * @param s hh:mm:ss.0
     * @return hh:mm
     */
    public static String formatTime(String s) {
        try {
            String[] splits = s.split(":");
            return splits[0] + ":" + splits[1];
        } catch (Exception ex) {
            LogUtils.e(StringUtils.class.getSimpleName(), "formatTime", ex);
            return (s == null) ? "" : s;
        }
    }

    public static String removeNonNumeric(String s) {
        return s.replaceAll("[^\\d.]", "");
    }

    public static String join(Collection collection, String separator) {

        if (collection == null || collection.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        int size = collection.size();
        int i = 0;

        for (Object s : collection) {
            sb.append(s.toString());

            if (i < size - 1) {
                sb.append(separator);
            }
            i++;
        }

        return sb.toString();
    }

    public static String join(Collection collection, char separator) {

        if (collection == null || collection.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Object s : collection) {
            sb.append(s.toString()).append(separator);
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static String format2Digits(double value) {
        DecimalFormat df = new DecimalFormat("0.00##");
        return df.format(value);
    }

//    public static String formatCurrency(double value) {
//        String pattern = "###,###";
//        DecimalFormat decimalFormat = new DecimalFormat(pattern);
//        return decimalFormat.format(Math.abs(value)) + "";
//    }
//
//    public static String formatCurrency(double value, String symbol) {
//        String pattern = "###,###";
//        DecimalFormat decimalFormat = new DecimalFormat(pattern);
//        String s = (symbol != null) ? decimalFormat.format(Math.abs(value)) + symbol : decimalFormat.format(value);
//        return s.replace(".", ",");
//    }

    public static boolean validateEmail(String s) {
        if (s == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(s).matches();
        }
    }

    public static boolean validatePhone(String s) {
        if (s == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(s).matches();
        }
    }

    public static boolean validateWebUrl(String s) {
        if (s == null) {
            return false;
        } else {
            return Patterns.WEB_URL.matcher(s).matches();
        }
    }
}
