// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

/**
 * This is the interface for all parsers, right now there is only
 * {@link CsvUrlParser}.
 * 
 */
public interface IParser {
  public AbstractTable parse(String url) throws Exception;
}
