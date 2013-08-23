/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.tool.marketdata;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.component.tool.AbstractTool;
import com.opengamma.financial.tool.ToolContext;
import com.opengamma.id.UniqueId;
import com.opengamma.integration.copier.snapshot.copier.SimpleSnapshotCopier;
import com.opengamma.integration.copier.snapshot.copier.SnapshotCopier;
import com.opengamma.integration.copier.snapshot.reader.FileSnapshotReader;
import com.opengamma.integration.copier.snapshot.reader.MasterSnapshotReader;
import com.opengamma.integration.copier.snapshot.reader.SnapshotReader;
import com.opengamma.integration.copier.snapshot.writer.MasterSnapshotWriter;
import com.opengamma.integration.copier.snapshot.writer.SnapshotWriter;
import com.opengamma.master.marketdatasnapshot.MarketDataSnapshotMaster;
import com.opengamma.scripts.Scriptable;

/** The entry point for running OpenGamma batches. */
@Scriptable
public class MarketDataSnapshotImportTool extends AbstractTool<ToolContext> {

  /** Logger. */
  private static final Logger s_logger = LoggerFactory.getLogger(MarketDataSnapshotImportTool.class);

  /** File name option flag */
  private static final String FILE_NAME_OPTION = "f";
  /** Snapshot name option flag */
  private static final String SNAPSHOT_NAME_OPTION = "n";

  private static ToolContext s_context;

  //-------------------------------------------------------------------------

  /**
   * Main method to run the tool. No arguments are needed.
   *
   * @param args the arguments, no null
   */
  public static void main(final String[] args) { // CSIGNORE
    final boolean success = new MarketDataSnapshotImportTool().initAndRun(args, ToolContext.class);
    System.exit(success ? 0 : 1);
  }

  @Override
  protected void doRun() throws Exception {
    s_context = getToolContext();

    SnapshotReader snapshotReader = constructSnapshotReader(getCommandLine().getOptionValue(FILE_NAME_OPTION));
    SnapshotWriter snapshotWriter = constructSnapshotWriter();
    SnapshotCopier snapshotCopier = new SimpleSnapshotCopier();

    snapshotCopier.copy(snapshotReader, snapshotWriter);

    // close the reader and writer
    snapshotReader.close();
    snapshotWriter.close();

  }

  private static SnapshotReader constructSnapshotReader(String filename) {
    return new FileSnapshotReader(filename);
  }

  private static SnapshotWriter constructSnapshotWriter() {
    MarketDataSnapshotMaster marketDataSnapshotMaster = s_context.getMarketDataSnapshotMaster();
    if (marketDataSnapshotMaster == null) {
      s_logger.warn("No market data snapshot masters found at {}", s_context);

    }
    return new MasterSnapshotWriter(marketDataSnapshotMaster);
  }

  //-------------------------------------------------------------------------
  @Override
  protected Options createOptions(boolean mandatoryConfig) {
    final Options options = super.createOptions(mandatoryConfig);
    options.addOption(createFilenameOption());
    options.addOption(createSnapshotNameOption());
    return options;
  }

  private static Option createFilenameOption() {
    final Option option = new Option(FILE_NAME_OPTION, "filename", true, "The path to the file to import");
    option.setRequired(true);
    return option;
  }

  private static Option createSnapshotNameOption() {
    final Option option = new Option(SNAPSHOT_NAME_OPTION, "snapshotName", true, "The snapshot name to create");
    option.setArgName("snapshot uid");
    option.setRequired(true);
    return option;
  }

}
