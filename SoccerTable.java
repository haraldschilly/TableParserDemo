// (c) Harald Schilly 2013, License: Apache 2.0

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Holds the parsed data.
 * 
 * Data Source: http://www.football-data.co.uk/italym.php
 */
class SoccerTable implements ITable {

  private static DateFormat   dateFmtOut = new SimpleDateFormat("yyyy-MM-dd");

  private List<Date>          date;
  private List<String>        home;
  private List<String>        away;
  private List<Integer>       homeGoals;
  private List<Integer>       awayGoals;
  private List<Integer>       homeShoots;
  private List<Integer>       awayShoots;

  private List[]              table;

  private StringBuilder       sb         = new StringBuilder();
  final private static String linebreak  = System.getProperty("line.separator");

  SoccerTable() {
    date = new ArrayList<Date>();
    home = new ArrayList<String>();
    away = new ArrayList<String>();
    homeGoals = new ArrayList<Integer>();
    awayGoals = new ArrayList<Integer>();
    homeShoots = new ArrayList<Integer>();
    awayShoots = new ArrayList<Integer>();
    table = new List[] { date, home, away, homeGoals, awayGoals, homeShoots, awayShoots };
  }

  void addRow(Date d, String h, String a, int hGoal, int aGoal, int hShoots, int aShoots) {
    date.add(d);
    home.add(h);
    away.add(a);
    homeGoals.add(hGoal);
    awayGoals.add(aGoal);
    homeShoots.add(hShoots);
    awayShoots.add(aShoots);
  }

  @Override
  public String toString() {
    sb.setLength(0);
    for (int i = 0; i < date.size(); i++) {
      sb.append(dateFmtOut.format(date.get(i))).append(": ");
      sb.append(String.format("%s : %s ", home.get(i), away.get(i)));
      sb.append(String.format(" = %d : %d", homeGoals.get(i), awayGoals.get(i)));
      sb.append(String.format(" (shoots: %d vs. %d)", homeShoots.get(i), awayShoots.get(i)));
      sb.append(linebreak);
    }
    return sb.toString();
  }

  @Override
  public void addRow(String[] tokens) throws ParseException {
    for (int i = 0; i < table.length; i++) {
      Columns.ColDef<?> cd = Columns.colDefs[i];
      table[i].add(cd.parse(tokens[cd.getIdx()]));
    }
  }

  public Integer totalGoals() {
    Integer sum = 0;
    for (List<Integer> goalCol : new List[] { homeGoals, awayGoals }) {
      for (Integer goals : goalCol) {
        sum += goals;
      }
    }
    return sum;
  }

  /** helper for maxGoals */
  private void accumulateGoals(HashMap<String, Integer> goals, List<String> who, List<Integer> howmany) {
    for (int idx = 0; idx < date.size(); idx++) {
      String t1 = who.get(idx);
      Integer g1 = goals.containsKey(t1) ? goals.get(t1) : 0;
      goals.put(t1, g1 + howmany.get(idx));
    }
  }

  /**
   * @return the team name, that had the most goals in total
   */
  public String maxGoals() {
    HashMap<String, Integer> goals = new HashMap<String, Integer>();
    accumulateGoals(goals, home, homeGoals);
    accumulateGoals(goals, away, awayGoals);
    String winner = "<error>";
    Integer max = -1;
    for (Entry<String, Integer> entry : goals.entrySet()) {
      if (max < entry.getValue()) {
        winner = entry.getKey();
        max = entry.getValue();
      }
    }
    return String.format("%s (%d goals)", winner, max);
  }
}