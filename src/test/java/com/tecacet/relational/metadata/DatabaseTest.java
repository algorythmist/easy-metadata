package com.tecacet.relational.metadata;

import com.tecacet.relational.util.TestBase;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DatabaseTest extends TestBase {

    @Test
    void getTables() throws SQLException {
        Database database = Database.create(connection);
        List<DatabaseTable> tables = database.getAllTables();
        assertEquals(3, tables.size());
    }


    @Test
    public void getTable() throws Exception {
        Database database = Database.create(connection);
        DatabaseTable table = database.getTable("SUPPLIER");
        assertNotNull(table);
        assertEquals("SUPPLIER_ID", table.getPrimaryKeyColumn());
        DatabaseColumn column = table.getColumn("ADDRESS");
        assertNotNull(column);
        assertEquals("ADDRESS", column.getName());
        assertEquals(Types.VARCHAR, column.getSQLType());
        assertEquals(100, column.getLength());
    }

    @Test
    public void getForeignKeys() throws SQLException {
        Database database = Database.create(connection);
        List<ForeignKey> foreignKeys = database.getForeignKeys("ACCOUNTING_ENTRY");
        assertEquals(2, foreignKeys.size());
        ForeignKey key1 = foreignKeys.get(0);
        assertEquals("CATEGORY_ID", key1.getSourceColumnName());
        assertEquals("ACCOUNTING_CATEGORY", key1.getTargetTableName());
        assertEquals("ACCOUNTING_CATEGORY_ID", key1.getTargetColumnName());
    }
}
