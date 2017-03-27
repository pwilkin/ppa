import org.junit.Test;

import java.sql.*;

/**
 * Created by pwilkin on 16-Mar-17.
 */
public class DatabaseTest {

    @Test
    public void testConnection() throws SQLException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            try (Connection c = DriverManager.getConnection(
             "jdbc:hsqldb:file:testdb",
             "SA",
             "")) {
                DatabaseMetaData md = c.getMetaData();
                md.getTables(null, null, null, null);
            }
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testQueries() throws Exception {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection c = DriverManager.getConnection(
                "jdbc:hsqldb:file:testdb",
                "SA",
                "")) {
            c.setAutoCommit(false);
            try {

                DatabaseMetaData md = c.getMetaData();
                try (ResultSet tf = md.getTables(null, null, "TESTTABLE", null)) {
                    if (!tf.next()) {
                        final PreparedStatement ps = c.prepareStatement("CREATE TABLE TESTTABLE " +
                                "(ID INT IDENTITY PRIMARY KEY, SOMENUMBER INT, SOMESTRING VARCHAR(255))");
                        ps.execute();
                    }
                }
            /*final PreparedStatement del = c.prepareStatement("DELETE FROM TESTTABLE");
            del.execute();*/
                final PreparedStatement insert = c.prepareStatement("INSERT INTO TESTTABLE " +
                        "(ID, SOMENUMBER, SOMESTRING) VALUES (NULL, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < 10; i++) {
                    insert.clearParameters();
                    insert.setInt(1, i);
                    insert.setString(2, "number" + i);
                    insert.execute();
                    try (ResultSet keys = insert.getGeneratedKeys()) {
                        keys.next();
                        System.out.println("Created entry: " + keys.getInt(1));
                    }
                }
                final PreparedStatement update = c.prepareStatement("UPDATE TESTTABLE SET SOMESTRING=?" +
                        " WHERE SOMENUMBER=?");
                update.setString(1, "pomidor");
                update.setInt(2, 7);
                update.execute();
                final Statement retrieve = c.createStatement();
                try (ResultSet rs = retrieve.executeQuery("SELECT * FROM TESTTABLE")) {
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        int num = rs.getInt("SOMENUMBER");
                        String str = rs.getString("SOMESTRING");
                        System.out.println("Entry " + id + ": number " + num + ", string " + str);
                    }
                }
                final PreparedStatement rps = c.prepareStatement("SELECT * FROM TESTTABLE WHERE SOMENUMBER > ? AND SOMENUMBER < ?");
                rps.setInt(1, 5);
                rps.setInt(2, 8);
                try (ResultSet rs = rps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        int num = rs.getInt("SOMENUMBER");
                        String str = rs.getString("SOMESTRING");
                        System.out.println("Select with where: entry " + id + ": number " + num + ", string " + str);
                    }
                }
            } finally {
                c.rollback();
            }
        }
    }

}
