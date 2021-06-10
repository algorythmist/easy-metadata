package com.tecacet.relational.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class JdbcScriptExecutorTest extends TestBase {

    @Test
    void execute() throws SQLException, IOException {
        JdbcScriptExecutor runScript = new JdbcScriptExecutor(dataSource);
        runScript.execute("test.sql");
    }
}