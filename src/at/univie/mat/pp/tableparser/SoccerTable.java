// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import at.univie.mat.pp.tableparser.Columns.ColDefString;
import at.univie.mat.pp.tableparser.Columns.Column;
import at.univie.mat.pp.tableparser.Columns.ColumnDate;
import at.univie.mat.pp.tableparser.Columns.ColumnInteger;

/**
 * Holds the parsed data.
 * 
 * Data Source: http://www.football-data.co.uk/italym.php
 */
public class SoccerTable extends AbstractTable {
  final private static String linebreak  = System.getProperty("line.separator");
  private StringBuilder       sb         = new StringBuilder();
  private DateFormat          dateFmtOut = new SimpleDateFormat("yyyy-MM-dd");

  private ColumnDate          date;
  private ColDefString        home;
  private ColDefString        away;
  private ColumnInteger       homeGoals;
  private ColumnInteger       awayGoals;
  private ColumnInteger       homeShoots;
  private ColumnInteger       awayShoots;

  public SoccerTable() {
    date = new ColumnDate(1, "Date", "dd/MM/yy");
    home = new ColDefString(2, "HomeTeam");
    away = new ColDefString(3, "AwayTeam");
    homeGoals = new ColumnInteger(4, "FTHG");
    awayGoals = new ColumnInteger(5, "FTAG");
    homeShoots = new ColumnInteger(10, "HS");
    awayShoots = new ColumnInteger(11, "AS");

    table = new Column[] { date, home, away, homeGoals, awayGoals, homeShoots, awayShoots };
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
  public void parseRow(String[] tokens) throws ParseException {
    for (int i = 0; i < table.length; i++) {
      Columns.Column<?> cd = table[i];
      table[i].add(cd.parse(tokens[cd.getIdx()]));
    }
  }

  public Integer totalGoals() {
    Integer sum = 0;
    for (ColumnInteger goalCol : new ColumnInteger[] { homeGoals, awayGoals }) {
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