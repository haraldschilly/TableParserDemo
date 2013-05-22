// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Definitions of table columns. This is just one way how this can be
 * accomplished.
 */
public class Columns {
  /**
   * This is the abstract base class for all column definitions. A column is
   * bascially an extended {@link ArrayList} and it additionally records the
   * index of the column in the source table, the name in the first row and the
   * target type.
   * 
   * @param <T>
   */
  @SuppressWarnings("serial")
  public static abstract class Column<T> extends ArrayList<T> {
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
    Column(Integer idx, String name, Class<T> type) {
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

    /**
     * calls the {@link ArrayList#add(Object)} function after an unsafe cast
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(Object parse) {
      return super.add((T) parse);
    }
  }

  /**
   * this column definition processes a string as a string and
   * {@link String#trim() trims} it.
   */
  @SuppressWarnings("serial")
  public static class ColumnString extends Column<String> {
    ColumnString(Integer idx, String name) {
      super(idx, name, String.class);
    }

    @Override
    String parse(String token) {
      return token.trim();
    }
  }

  /**
   * This column definition parses a single {@link Integer}.
   */
  @SuppressWarnings("serial")
  static class ColumnInteger extends Column<Integer> {
    ColumnInteger(Integer idx, String name) {
      super(idx, name, Integer.class);
    }

    @Override
    Integer parse(String token) {
      return token.length() > 0 ? Integer.parseInt(token) : 0;
    }
  }

  /**
   * This column holds {@link Date} Objects
   */
  @SuppressWarnings("serial")
  static class ColumnDate extends Column<Date> {
    private DateFormat dateFmt;

    public ColumnDate(Integer idx, String name, String dateFmt) {
      super(idx, name, Date.class);
      this.dateFmt = new SimpleDateFormat(dateFmt, Locale.ENGLISH);
    }

    @Override
    Date parse(String token) throws ParseException {
      return dateFmt.parse(token);
    }
  };

  // disabling constructor because there are just static fields and methods
  private Columns() {
  }

}
