// (c) Harald Schilly 2013, License: Apache 2.0

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import at.univie.mat.pp.tableparser.CsvUrlParser;
import at.univie.mat.pp.tableparser.IParser;
import at.univie.mat.pp.tableparser.AbstractParser;
import at.univie.mat.pp.tableparser.CsvParser;
import at.univie.mat.pp.tableparser.SoccerTable;

/**
 * Runs the parser, data from <a
 * href="http://www.football-data.co.uk/italym.php">football-data.co.uk</a>.
 */
class Run {
  public static void main(String... args) throws Exception {

    // list of URLs, we could also do this as Files or general InputStreams
    List<String> urls = new ArrayList<String>();
    urls.add("http://www.football-data.co.uk/mmz4281/1213/I1.csv");
    urls.add("http://www.football-data.co.uk/mmz4281/1112/I1.csv");
    urls.add("http://www.football-data.co.uk/mmz4281/1011/I1.csv");

    // iterate over the URLs and use the parser from above to get the tables
    int sumTotalGoals = 0;

    // chooses the implementation of the Parser we want to use and the
    // corresponding target class
    AbstractParser parser = new CsvParser(SoccerTable.class);
    for (String url : urls) {
      SoccerTable t = (SoccerTable) parser.parse(new URL(url));
      System.out.println(t);
      int totalGoals = t.totalGoals();
      sumTotalGoals += totalGoals;
      System.out.println("total goals: " + totalGoals);
      System.out.println("max. number of goals: " + t.maxGoals());
      System.out.println();
    }
    System.out.println("In total, there have been " + sumTotalGoals + " goals.");
  }
}
