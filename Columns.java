// (c) Harald Schilly 2013, License: Apache 2.0

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Definitions of table columns. This is just one way how this can be
 * accomplished.
 */
public class Columns {
  /**
   * this is the abstract base class for all column definitions.
   * 
   * @param <T>
   */
  static abstract class ColDef<T> {
    private String  name;
    private Integer idx;

    /**
     * 
     * @param idx
     *          which column in the original table
     * @param name
     *          the associated name in the original table
     * @param type
     *          the class of the type in this column
     */
    ColDef(Integer idx, String name, Class<T> type) {
      this.idx = idx;
      this.name = name;
    }

    String getName() {
      return name;
    }

    Integer getIdx() {
      return idx;
    }

    abstract T parse(String token) throws ParseException;
  }

  /**
   * this column definition processes a string as a string and trims it.
   */
  static class ColDefString extends ColDef<String> {
    ColDefString(Integer idx, String name) {
      super(idx, name, String.class);
    }

    @Override
    String parse(String token) {
      return token.trim();
    }
  }

  /**
   * This column definition parses a single integer.
   */
  static class ColDefInt extends ColDef<Integer> {
    ColDefInt(Integer idx, String name) {
      super(idx, name, Integer.class);
    }

    @Override
    Integer parse(String token) {
      return token.length() > 0 ? Integer.parseInt(token) : 0;
    }
  }

  final public static ColDef<Date> dateCD;
  final static ColDefString        homeCD;      // home team name
  final static ColDefString        awayCD;      // away team name
  final static ColDefInt           homeGoalsCD; // home goals
  final static ColDefInt           awayGoalsCD; // away goals
  final static ColDefInt           homeShootsCD; // home shoots
  final static ColDefInt           awayShootsCD; // away shoots

  final static ColDef<?>[]         colDefs;

  static {
    dateCD = new ColDef<Date>(1, "Date", Date.class) {
      private DateFormat dateFmt = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);

      @Override
      Date parse(String token) throws ParseException {
        return dateFmt.parse(token);
      }
    };

    homeCD = new ColDefString(2, "HomeTeam");
    awayCD = new ColDefString(3, "AwayTeam");

    homeGoalsCD = new ColDefInt(4, "FTHG");
    awayGoalsCD = new ColDefInt(5, "FTAG");
    homeShootsCD = new ColDefInt(10, "HS");
    awayShootsCD = new ColDefInt(11, "AS");

    colDefs = new ColDef[] { dateCD, homeCD, awayCD, homeGoalsCD, awayGoalsCD, homeShootsCD, awayShootsCD };
  }

  // disabling constructor because there are just static fields and methods
  private Columns() {
  }

  static boolean checkFirstRow(String[] tokens) throws Exception {
    boolean ok = true;
    for (ColDef<?> def : colDefs) {
      int i = def.getIdx();
      String str = def.getName();
      if (!tokens[i].equals(str)) {
        System.err.println(String.format("column %d is not %s but %s", i, str, tokens[i]));
        ok = false;
      }
    }
    if (!ok) {
      throw new Exception("wrong columns");
    }
    return true;
  }
}
