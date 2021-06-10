package com.tecacet.relational.util;

import com.tecacet.relational.metadata.Database;
import com.tecacet.relational.metadata.impl.MetaDataDatabase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;


public class TestBase {

    public static final String DRIVER = "org.h2.Driver";
    public static final String URL = "jdbc:h2:./test";

    public static final String SUPPLIER_TABLE = "SUPPLIER";
    public static final String STUDENT_ID = "STUDENT_ID";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";

    protected BasicDataSource dataSource;

    protected Connection connection;

    protected DatabaseMetaData metaData;

    protected Database database;

    public TestBase() {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername("sa");
        // dataSource.setPassword("dimitri");
        try {
            connection = dataSource.getConnection();
            metaData = dataSource.getConnection().getMetaData();
            database = new MetaDataDatabase(metaData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    public void setUp() throws Exception {
        JdbcScriptExecutor scriptRunner = new JdbcScriptExecutor(dataSource);
        scriptRunner.execute("project-schema.sql");
    }

    @AfterEach
    public void tearDown() throws Exception {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("DROP table ACCOUNTING_ENTRY");
            st.executeUpdate("DROP table ACCOUNTING_CATEGORY");
            st.executeUpdate("DROP table SUPPLIER");
        }
        connection.close();

    }
}
