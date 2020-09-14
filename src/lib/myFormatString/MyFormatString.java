package lib.myFormatString;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class MyFormatString {
    private MyFormatString() {}

    /**
     * Форматирует значение на входе (наносекунды) в строку на выходе в формате (милисекунды)
     */
    public static String formatNanoTimeToMillis(long d) {
        StringBuilder str = new StringBuilder(Long.toString(d));
        char[] ch = str.reverse().toString().toCharArray();
        str = new StringBuilder();

        if (ch.length <= 6) {
            str.append("0.");
            for (int i = 0; i < 6 - ch.length; i++) {
                str.append('0');
                if (i == 2) str.append(' ');
            }
        }

        for (int i = ch.length - 1; i >= 0; i--) {
            str.append(ch[i]);
            if (i == 3 || i == 9) str.append(' ');
            if (i == 6) str.append('.');
        }
        return str.toString();
    }

    /**
     * Возвращает текущую дату в формате
     */
    public static String date() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh-mm-ss");
        return dateFormat.format(calendar.getTime());
    }
}
