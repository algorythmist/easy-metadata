package com.tecacet.relational.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Executes a SQL script
 *
 * @author Dimitri Papaioannou
 */
public class JdbcScriptExecutor {

    // TODO not so portable
    private final static char QUERY_ENDS = ';';

    private final DataSource dataSource;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JdbcScriptExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void execute(String filename) throws IOException, SQLException {
        File script = new File(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(script), Charset.defaultCharset()));
        String line;
        StringBuilder query = new StringBuilder();
        boolean queryEnds;
        Connection connection = dataSource.getConnection();
        Statement stat = connection.createStatement();
        try {
            while ((line = reader.readLine()) != null) {
                if (isComment(line)) {
                    continue;
                }
                queryEnds = checkStatementEnds(line);
                query.append(line);
                if (queryEnds) {
                    log.info("query->" + query);
                    stat.addBatch(query.toString());
                    query.setLength(0);
                }
            }
            stat.executeBatch();
        } finally {
            stat.close();
            connection.close();
            reader.close();
        }
    }

    private boolean isComment(String line) {
        if ((line != null) && (line.length() > 0)) {
            return (line.charAt(0) == '#');
        }
        return false;
    }

    private boolean checkStatementEnds(String s) {
        return (s.indexOf(QUERY_ENDS) != -1);
    }

}
