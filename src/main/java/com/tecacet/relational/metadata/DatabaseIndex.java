package com.tecacet.relational.metadata;

/**
 * Represents a lookup index on a table
 */
public class DatabaseIndex extends LookupKey {
    private final boolean unique = true;

    public DatabaseIndex(String name, String columnName) {
        super(name);
        addColumn(columnName);
    }

    public boolean isUnique() {
        return unique;
    }

}
