package resume_extractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Jerry on 2017/7/28.
 */
public class Application {

  private static SimpleDateFormat getDataFormator() {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  }

  public static int getMonthSpace(String date1, String date2) throws ParseException {
    int result = 0;
    SimpleDateFormat sdf = getDataFormator();
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    c1.setTime(sdf.parse(date1));
    c2.setTime(sdf.parse(date2));
    result = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12  + c2.get(Calendar.MONTH) -
    c1.get(Calendar.MONTH);

    return result == 0 ? 0 :result;
  }

  private static  void init(HashMap<Object, Object> map) {
    map.put("10", Integer.parseInt("1"));
    map.put("1", Integer.parseInt("2"));

  }

  public static void main(String[] args) throws Exception {
    String description = "\r\n";
    String value = Base64.getEncoder().encodeToString(description.getBytes());
    System.out.println("=" + value + "=");
  }
}
