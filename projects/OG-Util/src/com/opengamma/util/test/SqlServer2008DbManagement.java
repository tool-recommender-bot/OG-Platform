/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SQLServerDialect;

import com.opengamma.OpenGammaRuntimeException;

/**
 * Database management for Postgres databases.
 */
public final class SqlServer2008DbManagement extends AbstractDbManagement {

  /**
   * SQL to retrieve all the columns.
   */
  private static final String GET_ALL_COLUMNS_SQL =
    "SELECT column_name AS name,data_type AS datatype,is_nullable AS allowsnull,column_default AS defaultvalue FROM information_schema.columns WHERE table_catalog='";
  /**
   * The Postgres default schema.
   */
  private static final String SQLSERVER2008_DEFAULT_SCHEMA = "public";
  /**
   * Singleton instance.
   */
  private static final SqlServer2008DbManagement INSTANCE = new SqlServer2008DbManagement();

  /**
   * The underlying Hibernate dialect.
   */
  private SQLServerDialect _hibernateDialect;

  /**
   * Restricted constructor.
   */
  private SqlServer2008DbManagement() {
  }

  /**
   * Gets the singleton instance.
   * 
   * @return the instance, not null
   */
  public static SqlServer2008DbManagement getInstance() {
    return INSTANCE;
  }

  //-------------------------------------------------------------------------
  @Override
  public synchronized Dialect getHibernateDialect() {
    if (_hibernateDialect == null) {
      // constructed lazily so we don't get log message about 'using dialect' if we're not actually using it
      _hibernateDialect = new SQLServerDialect();
    }
    return _hibernateDialect;
  }

  @Override
  public Class<?> getJDBCDriverClass() {
    return com.microsoft.sqlserver.jdbc.SQLServerDriver.class;
  }

  @Override
  public String getDatabaseName() {
    return "sqlserver2008";
  }

  //-------------------------------------------------------------------------
  @Override
  public String getAllSchemasSQL(String catalog) {
    return "SELECT nspname AS name from pg_namespace";
  }

  @Override
  public String getAllForeignKeyConstraintsSQL(String catalog, String schema) {
    if (schema == null) {
      schema = SQLSERVER2008_DEFAULT_SCHEMA;
    }
    String sql = "SELECT constraint_name AS name, table_name FROM information_schema.table_constraints WHERE " +
      "constraint_catalog = '" + catalog + "' AND constraint_schema = '" + schema + "'" + " AND constraint_type = 'FOREIGN KEY'";
    return sql;
  }

  @Override
  public String getAllSequencesSQL(String catalog, String schema) {
    if (schema == null) {
      schema = SQLSERVER2008_DEFAULT_SCHEMA;
    }
    String sql = "SELECT sequence_name AS name FROM information_schema.sequences WHERE " +
      "sequence_catalog = '" + catalog + "'" + " AND sequence_schema = '" + schema + "'";
    return sql;
  }

  @Override
  public String getAllTablesSQL(String catalog, String schema) {
    if (schema == null) {
      schema = SQLSERVER2008_DEFAULT_SCHEMA;
    }
    String sql = "SELECT table_name AS name FROM information_schema.tables WHERE " +
      "table_catalog = '" + catalog + "'" + " AND table_schema = '" + schema + "' AND table_type = 'BASE TABLE'";
    return sql;
  }

  @Override
  public String getAllViewsSQL(String catalog, String schema) {
    if (schema == null) {
      schema = SQLSERVER2008_DEFAULT_SCHEMA;
    }
    String sql = "SELECT table_name AS name FROM information_schema.tables WHERE " +
      "table_catalog = '" + catalog + "'" + " AND table_schema = '" + schema + "' AND table_type = 'VIEW'";
    return sql;
  }

  @Override
  public String getAllColumnsSQL(String catalog, String schema, String table) {
    if (schema == null) {
      schema = SQLSERVER2008_DEFAULT_SCHEMA;
    }
    StringBuilder sql = new StringBuilder(GET_ALL_COLUMNS_SQL);
    sql.append(catalog).append("' AND table_schema='").append(schema).append("' AND table_name='");
    sql.append(table).append("'");
    return sql.toString();
  }

  @Override
  public String getCreateSchemaSQL(String catalog, String schema) {
    return "CREATE SCHEMA " + schema;
  }

  @Override
  public CatalogCreationStrategy getCatalogCreationStrategy() {
    return new SQLCatalogCreationStrategy(
        getDbHost(), 
        getUser(), 
        getPassword(), 
        "SELECT datname AS name FROM pg_database",
        "template1");
  }

  @Override
  public void dropSchema(String catalog, String schema) {
    if (schema != null) {
      super.dropSchema(catalog, schema);
    } else {
      try {
        if (!getCatalogCreationStrategy().catalogExists(catalog)) {
          return;
        }
        Connection conn = connect(catalog);
        Statement statement = conn.createStatement();
        //TODO default schema
        statement.executeUpdate("DROP SCHEMA IF EXISTS public CASCADE;CREATE SCHEMA public;");
        statement.close();
        conn.close();
      } catch (SQLException se) {
        throw new OpenGammaRuntimeException("Failed to drop the default schema", se);
      }
    }
  }
}
