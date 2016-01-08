package algorithm;

import javax.ejb.Stateless;

/**
 * Created by Tomek on 2015-12-15.
 */


public class StringUtils {

    public StringUtils() {
    }

    public String getWord(String s, int i) {
        String wynik = "";
        while (Character.isLetter(s.charAt(i)) || s.charAt(i) == ' ') {
            wynik += s.charAt(i);
            i++;
        }
        return wynik;
    }

    public int getInteger(String s, int i) {
        int wynik = 0;
        while (Character.isDigit(s.charAt(i))) {
            wynik = wynik * 10 + s.charAt(i) - '0';
            i++;
        }
        return wynik;
    }

    public String getDouble(String line, int i) {
        String result = "";
        while (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.' || line.charAt(i) == '.') {
            result += line.charAt(i);
            i++;
        }
        return result;
    }
}
