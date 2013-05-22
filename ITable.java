import java.text.ParseException;

// (c) Harald Schilly 2013, License: Apache 2.0

/**
 * All Tables, currently just one, need to implement this interface. This is
 * used by the {@link Parser} to feed into the data.
 */
public interface ITable {
  public void addRow(String[] tokens) throws ParseException;
}
