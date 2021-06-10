package com.tecacet.relational.metadata;

import java.util.ArrayList;
import java.util.List;

public class LookupKey {

    private String name;
    private final List<String> columns = new ArrayList<>();

    public LookupKey() {
    }

    public LookupKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getColumn(int index) {
        return columns.get(index);
    }

    public boolean containsColumn(String columnName) {
        return columns.contains(columnName);
    }

    public String[] getColumns() {
        return columns.toArray(new String[columns.size()]);
    }

    public int getNumberOfColumns() {
        return columns.size();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void addColumn(String column) {
        columns.add(column);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof  LookupKey)) {
            return false;
        }
        LookupKey k = (LookupKey) o;
        if (!name.equals(k.name)) {
            return false;
        }
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            if (!column.equals(k.getColumn(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
