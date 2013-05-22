// (c) Harald Schilly 2013, License: Apache 2.0

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Runs the parser, data from <a
 * href="http://www.football-data.co.uk/italym.php">football-data.co.uk</a>.
 */
class Run {
  public static void main(String... args) throws Exception {
    List<String> urls = new ArrayList<String>();
    if (args.length > 0) {
      Collections.addAll(urls, args);
    } else {
      urls.add("http://www.football-data.co.uk/mmz4281/1213/I1.csv");
      urls.add("http://www.football-data.co.uk/mmz4281/1112/I1.csv");
      urls.add("http://www.football-data.co.uk/mmz4281/1011/I1.csv");
    }

    for (String url : urls) {
      Parser parser = new Parser(url, SoccerTable.class);
      SoccerTable t = (SoccerTable) parser.parse();
      System.out.println(t);
      System.out.println("total goals: " + t.totalGoals());
      System.out.println("max. number of goals: " + t.maxGoals());
      System.out.println();
    }
  }
}