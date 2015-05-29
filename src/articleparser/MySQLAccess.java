package articleparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String password="buenosS1";

    public void writeDataBase(String guid, String description, String title, String text, String res, String lang, String image, String pubdate) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/aqparat?"
                    + "user=root&password="+password+"&characterEncoding=utf-8&useUnicode=true");

            // Statements allow to issue SQL queries to the database
            //statement = connect.createStatement();
            // Result set get the result of the SQL query
            //resultSet = statement.executeQuery("select * from aqparat.categories");
            // writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            String query="INSERT INTO `aqparat`.`news` (`id`, `title`, `description`, `text`, `photo`, `link`, `time`, `resource_id`, `language_id`) VALUES (NULL, '" + title + "', '" + description + "', '" + text + "', '"+image+"', '" + guid + "', '" + pubdate + "', '" + res + "', '" + lang + "');";
           String qr="INSERT INTO `aqparat`.`news` (`id`, `title`, `description`, `text`, `photo`, `link`, `time`, `resource_id`, `language_id`) VALUES (NULL, '" + title + "', '" + description + "', '" + "text" + "', '"+image+"', '" + guid + "', CURRENT_TIMESTAMP, '" + res + "', '" + lang + "');";

                //   System.out.println(qr);
            preparedStatement = connect.prepareStatement(query);
            // "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
            // Parameters start with 1
//      preparedStatement.setString(1, "Test");
//      preparedStatement.setString(2, "TestEmail");
//      preparedStatement.setString(3, "TestWebpage");
//      preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
//      preparedStatement.setString(5, "TestSummary");
//      preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();
//
//      preparedStatement = connect
//          .prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from feedback.comments");
//      resultSet = preparedStatement.executeQuery();
            //resultSet=statement.executeQuery("INSERT INTO `aqparat`.`news` (`id`, `title`, `text`, `photo`, `link`, `time`, `resource_id`, `language_id`) VALUES (NULL, 'my title', 'this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news this is news ', 'photo', 'link', CURRENT_TIMESTAMP, '1', '1')");
            //writeResultSet(resultSet);

            // Remove again the insert comment
//      preparedStatement = connect
//      .prepareStatement("delete from feedback.comments where myuser= ? ; ");
//      preparedStatement.setString(1, "Test");
//      preparedStatement.executeUpdate();

            //resultSet = statement.executeQuery("select * from feedback.comments");
            //writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //   Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);

            String name = resultSet.getString("name");

            String id = resultSet.getString("id");

            System.out.print("Topic # " + id + " ");
            System.out.println(name);


        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }

    void changeLastNews(String string, String guid) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager.getConnection("jdbc:mysql://localhost/aqparat?"
                + "user=root&password="+password+"&characterEncoding=utf-8&useUnicode=true");


        preparedStatement = connect.prepareStatement("UPDATE  `aqparat`.`lastnews` SET  `guid` =  '" + guid + "' WHERE  `lastnews`.`resource_id` ='" + string + "';");

        preparedStatement.executeUpdate();
    }

    String getLastNews(String string) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager.getConnection("jdbc:mysql://localhost/aqparat?"
                + "user=root&password="+password+"&characterEncoding=utf-8&useUnicode=true");

        statement = connect.createStatement();
        String query = "SELECT  `guid` FROM  `lastnews` WHERE  `resource_id` ='" + string + "';";
        resultSet = statement.executeQuery(query);
        String result = "";
        if (resultSet.next()) {
            result = resultSet.getString("guid");
        }
        return result;
    }
    public void writeBest(int id, String title,String link, String image, String res, String resimage, String time, String shared, String fulltext) throws ClassNotFoundException, SQLException{
       try{
        Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://localhost/aqparat?"
                    + "user=root&password="+password+"&characterEncoding=utf-8&useUnicode=true");
            
             String query="UPDATE  `aqparat`.`bestnews` SET  `title` =  '"+title+"',`link` =  '"+link+"',`imagelink` =  '"+image+"',`resource` =  '" + res + "',`res_imagelink` =  '"+resimage+"',`time` =  '"+time+"',`shared` =  '"+shared+"',`fulltext` =  '"+fulltext+"' WHERE  `bestnews`.`id` ="+id+";";
     //   System.out.println(query);
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.executeUpdate();
             } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
}
