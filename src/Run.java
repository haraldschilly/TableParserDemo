// (c) Harald Schilly 2013, License: Apache 2.0

import java.util.ArrayList;
import java.util.List;

import at.univie.mat.pp.tableparser.CsvUrlParser;
import at.univie.mat.pp.tableparser.IParser;
import at.univie.mat.pp.tableparser.SoccerTable;

/**
 * Runs the parser, data from <a
 * href="http://www.football-data.co.uk/italym.php">football-data.co.uk</a>.
 */
class Run {
  public static void main(String... args) throws Exception {

    // list of Strings that are URLs (a parser for files would take file paths)
    List<String> urls = new ArrayList<String>();
    urls.add("http://www.football-data.co.uk/mmz4281/1213/I1.csv");
    urls.add("http://www.football-data.co.uk/mmz4281/1112/I1.csv");
    urls.add("http://www.football-data.co.uk/mmz4281/1011/I1.csv");

    // creating the parser as an instance of the CSV-URL parser
    IParser parser = new CsvUrlParser(SoccerTable.class);

    // iterate over the URLs and use the parser from above to get the tables
    int sumTotalGoals = 0;
    for (String url : urls) {
      SoccerTable t = (SoccerTable) parser.parse(url);
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