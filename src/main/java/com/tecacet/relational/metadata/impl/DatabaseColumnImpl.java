package com.tecacet.relational.metadata.impl;


import com.tecacet.relational.metadata.DatabaseColumn;
import com.tecacet.relational.metadata.DatabaseTable;

/**
 * Simple implementation of DatabaseColumn
 */
public class DatabaseColumnImpl implements DatabaseColumn {
    /**
     * name of the column
     */
    private final String name;
    /**
     * example: this column is of type "String"
     */
    private String columnTypeName = "";
    /**
     * what java.sql.Type is this column?
     */
    private final int type;
    /**
     * name of table that this column belongs to
     */
    private DatabaseTableImpl table;

    private boolean isPrimaryKey = false;

    /***************************************************************************
     * is null allowed for this column? *
     **************************************************************************/
    private boolean nullAllowed = false;
    /**
     * is this an auto increment column?
     */
    private boolean autoIncrement = false;
    /**
     * is this a read only column?
     */
    private boolean readOnly = false;

    /**
     * what is the precision of this column?
     */
    private int precision = -1;
    /**
     * what is the length of this column?
     */
    private int length = -1;


    public DatabaseColumnImpl(DatabaseTableImpl table, String name, int type) {
        this.table = table;
        this.name = name;
        this.type = type;
        this.table.addColumn(this);
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    public void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }

    public int getType() {
        return type;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public String getName() {
        return this.name;
    }

    public DatabaseTable getTable() {
        return table;
    }

    public String getTableName() {
        return table.getName();
    }

    public int getSQLType() {
        return type;
    }

    @Override
    public String getSQLTypeName() {
        return columnTypeName;
    }

    @Override
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setAsPrimaryKey() {
        isPrimaryKey = true;
    }

    @Override
    public boolean isNullable() {
        return nullAllowed;
    }

    public void setNullable(boolean nullable) {
        nullAllowed = nullable;
    }

    @Override
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public int getPrecision() {
        return precision;
    }

    @Override
    public int getLength() {
        return length;
    }

}
