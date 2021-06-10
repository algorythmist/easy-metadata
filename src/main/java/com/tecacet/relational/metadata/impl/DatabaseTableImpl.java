package com.tecacet.relational.metadata.impl;


import com.tecacet.relational.metadata.DatabaseColumn;
import com.tecacet.relational.metadata.DatabaseIndex;
import com.tecacet.relational.metadata.DatabaseTable;
import com.tecacet.relational.metadata.LookupKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation of DatabaseTable
 */
public class DatabaseTableImpl implements DatabaseTable {
    private String name;

    // columns by name
    private final Map<String, DatabaseColumn> nameToColumnMap = new HashMap<>();
    // columns by index
    private final List<DatabaseColumn> columns = new ArrayList<>();

    private LookupKey primaryKey = null;
    private final List<DatabaseIndex> uniqueIndices = new ArrayList<>();

    DatabaseTableImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DatabaseColumn getColumn(String columnName) {
        return nameToColumnMap.get(columnName);
    }

    @Override
    public boolean containsColumn(String columnName) {
        return nameToColumnMap.containsKey(columnName);
    }

    @Override
    public DatabaseColumn getColumn(int index) {
        return columns.get(index);
    }

    @Override
    public int getNumberOfColumns() {
        return columns.size();
    }

    @Override
    public LookupKey getPrimaryKey() {
        return primaryKey;
    }

    // TODO this is not right if there is more than one
    @Override
    public String getPrimaryKeyColumn() {
        return primaryKey.getColumn(0);
    }

    protected void addColumn(DatabaseColumn column) {
        nameToColumnMap.put(column.getName(), column);
        columns.add(column);
    }

    protected void setPrimaryKey(LookupKey key) {
        primaryKey = key;
    }

    protected void addUniqueIndex(String indexName, String columnName) {
        DatabaseColumn column = nameToColumnMap.get(columnName);
        // TODO this const is not implemented
        DatabaseIndex index = new DatabaseIndex(indexName, column.getName());
        uniqueIndices.add(index);
    }

    @Override
    public String toString() {
        return name;
    }

}
