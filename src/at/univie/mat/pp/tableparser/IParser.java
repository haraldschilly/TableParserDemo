// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.io.InputStream;

/**
 * This is the interface for all parsers, right now there is only
 * {@link CsvParser} via {@link AbstractParser}.
 * 
 */
public interface IParser {
  public AbstractTable parse(InputStream input) throws Exception;
}
