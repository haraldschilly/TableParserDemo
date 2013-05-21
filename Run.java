// (c) Harald Schilly 2013, License: Apache 2.0

/**
 * Runs the parser, data from <a
 * href="http://www.football-data.co.uk/italym.php">football-data.co.uk</a>.
 */
class Run {
  public static void main(String... args) throws Exception {

    String s2012A = "http://www.football-data.co.uk/mmz4281/1213/I1.csv";
    String s2011A = "http://www.football-data.co.uk/mmz4281/1112/I1.csv";
    String s2010A = "http://www.football-data.co.uk/mmz4281/1011/I1.csv";

    for (String url : new String[] { s2012A, s2011A, s2010A }) {
      Parser parser = new Parser(url);
      Table t = parser.parse();
      System.out.println(t);
      System.out.println("total goals: " + t.totalGoals());
      System.out.println("max. number of goals: " + t.maxGoals());
      System.out.println();
    }
  }
}