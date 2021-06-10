package com.tecacet.relational.metadata;

/**
 * The Metadata describing a column in a database table
 */
public interface DatabaseColumn {

    /**
     * the name of the column
     *
     * @return  the name of the column
     */
    String getName();

    DatabaseTable getTable();

    String getTableName();

    /**
     * the data type of a column
     *
     * @return the java.sql.Types enum
     */
    int getSQLType();

    /**
     * the data type of a column
     *
     * @return the java.sql.Types String
     */
    String getSQLTypeName();

    boolean isPrimaryKey();

    /**
     * does this column allow null?
     *
     * @return whether or not the column has null Allowed
     */
    boolean isNullable();

    /**
     * does this column auto increment?
     *
     * @return whether or not this column auto increments
     */
    boolean isAutoIncrement();

    /**
     * is this column read only?
     *
     * @return whether or not this column is read only
     */
    boolean isReadOnly();

    /**
     * the precision of the column
     *
     * @return the precision of the column
     */
    int getPrecision();

    /**
     * the storage length of a column
     *
     * @return the storage length of a column
     */
    int getLength();

}
