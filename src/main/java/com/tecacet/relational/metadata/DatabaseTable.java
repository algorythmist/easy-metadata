package com.tecacet.relational.metadata;


/**
 * Represents a database table as a named collection of columns
 */
public interface DatabaseTable {

    /**
     * Get the table name
     *
     * @return table name
     */
    String getName();

    /**
     * Get a column by name
     *
     * @param columnName the column name
     * @return column
     */
    DatabaseColumn getColumn(String columnName);

    boolean containsColumn(String columnName);

    /**
     * Get a column by index
     *
     * @param index index of column in the table
     * @return column
     */
    DatabaseColumn getColumn(int index);

    int getNumberOfColumns();

    LookupKey getPrimaryKey();

    // TODO this is not right is there is more than one
    String getPrimaryKeyColumn();

}
