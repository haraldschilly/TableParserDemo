// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.text.ParseException;

import at.univie.mat.pp.tableparser.Columns.Column;

/**
 * All Tables, currently just one, need to implement this interface. This is
 * used by the {@link CsvUrlParser} to feed into the data.
 */
public abstract class AbstractTable {
  protected Column<?>[] table;

  public abstract void parseRow(String[] tokens) throws ParseException;

  /**
   * This assumes a CSV-like table, where the first row's content matches the
   * column name definitions.
   * 
   * @param tokens
   * @return
   * @throws Exception
   */
  public boolean checkFirstRow(String[] tokens) throws Exception {
    boolean ok = true;
    for (Column<?> def : table) {
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
