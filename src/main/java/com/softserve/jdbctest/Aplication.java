package com.softserve.jdbctest;

import java.sql.*;
import java.util.Scanner;


public class Aplication {
//    private static final String url = "jdbc:sqlserver://ANDRIY-PC:1433;database=DB_JDBC;user=sa;password=1";
private static final String url ="jdbc:sqlserver://ANDRIY-PC:1433;database=DB_JDBC";
    private static final String user = "sa";
    private static final String password = "1";

    private static Connection connection=null;
    private static Statement statement=null;
    private static ResultSet rs=null;

    public static void main(String args[]){
        try {
//region    0. This will load the MySQL driver, each DB has its own driver //
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //endregion

//region    1. Get a connection to database //
            connection = DriverManager.getConnection(url, user, password);
            //endregion

//region  2. Create a statement
            // Statements allow to issue SQL queries to the database
            statement=connection.createStatement();
            //endregion

            //readData();

            updateDataCity();
            readData();

            //insertDataCity();

            //DeleteDataCity();

            //CallProcedureForInsertToPersonBook();


        } catch (ClassNotFoundException e) {
            System.out.println("MS SQL Server Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try { rs.close(); } catch (SQLException e) { } // ignore
            if (statement != null) try { statement.close(); } catch (SQLException e) { }
            if (connection != null) try { connection.close(); } catch (SQLException e) { }
        }
    }

    private static void readData() throws SQLException {
//region    SELECT COUNT(*) FROM Person //
            // 3. executing SELECT query
            rs=statement.executeQuery("SELECT COUNT(*) FROM Person");

            // 4. Process the result set
            while (rs.next()) {
                int count = rs.getInt(1);
                // Simply Print the results
                System.out.format("\ncount: %d\n", count);
            }
            //endregion

//region    SELECT * FROM Person //
            // 3. executing SELECT query
            rs=statement.executeQuery("SELECT * FROM Person");

            // 4. Process the result set
            System.out.format("\nTable Person --------------------\n");
            System.out.format("%3s %-12s %-12s %-10s %s\n", "ID", "Surname", "Name", "City", "Email");
            while (rs.next())
            {
                int id=rs.getInt("IDPerson");
                String surname = rs.getString("Surname");
                String name = rs.getString("Name");
                String city=rs.getString("City");
                String email=rs.getString("Email");
                // Simply Print the results
                System.out.format("%3d %-12s %-12s %-10s %s\n", id, surname, name, city, email);
            }
            //endregion

//region    SELECT * FROM Book //
            // 3. executing SELECT query
            rs=statement.executeQuery("SELECT * FROM Book");

            // 4. Process the result set
            System.out.format("\nTable Book --------------------\n");
            System.out.format("%3s %-18s %-18s %s\n", "ID", "BookName", "Author", "Amount");
            while (rs.next())
            {
                int id=rs.getInt("IDBook");
                String bookName = rs.getString("BookName");
                String author = rs.getString("Author");
                String amount=rs.getString("Amount");
                // Simply Print the results
                System.out.format("%3d %-18s %-18s %s\n", id, bookName, author, amount);
            }
            //endregion

//region    SELECT * FROM City //
            // 3. executing SELECT query
            rs=statement.executeQuery("SELECT * FROM City");

            // 4. Process the result set
            System.out.format("\nTable City --------------------\n");
            System.out.format("%s\n", "City");
            while (rs.next())
            {
                String city = rs.getString("City");
                // Simply Print the results
                System.out.format("%s\n", city);
            }
            //endregion

//region    SELECT * FROM PersonBook //
            // 3. executing SELECT query
            String query="Select " +
                    "(SELECT Surname FROM person WHERE IDPerson=P.IDPerson) AS Surname, " +
                    "(SELECT BookName FROM book WHERE IDBook=P.IDBook) AS BookName "+
                    "FROM personbook AS P";
            rs=statement.executeQuery(query);

            // 4. Process the result set
            System.out.format("\nJoining Table PersonBook --------------------\n");
            System.out.format("%-15s %s\n", "Surname", "BookName");
            while (rs.next())
            {
                String surname = rs.getString("Surname");
                String bookName = rs.getString("BookName");
                // Simply Print the results
                System.out.format("%-15s %s\n", surname, bookName);
            }
            //endregion

    }

    private static void updateDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name city what you want to update: ");
        String city = input.next();
        System.out.println("Input new name city for %s: "+ city);
        String citynew = input.next();

        // 3. executing SELECT query
// 1
        statement.execute("UPDATE city SET City='"+citynew+"' WHERE City='"+city+"';");

// 2  Returns count of updated rows
//        int n=statement.executeUpdate("UPDATE city SET City='"+citynew+"' WHERE City='"+city+"';");
//        System.out.println("Count rows that updated: "+n);

// 3  PreparedStatements can use variables and are more efficient
//        PreparedStatement preparedStatement;
//        preparedStatement=connection.prepareStatement("UPDATE city SET City=? WHERE City=?;");
//        preparedStatement.setString(1, citynew);
//        preparedStatement.setString(2, city);
//        int n=preparedStatement.executeUpdate();
//        System.out.println("Count rows that updated: "+n);

    }

    private static void insertDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name city: ");
        String newcity = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("INSERT city VALUES (?)");
        preparedStatement.setString(1, newcity);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: "+n);

    }

    private static void DeleteDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name city for delete: ");
        String city = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("DELETE FROM city WHERE City=?");
        preparedStatement.setString(1, city);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: "+n);
    }

    private static void CallProcedureForInsertToPersonBook() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput Surname for Person: ");
        String surname = input.next();
        System.out.println("Input NameBook for Book: ");
        String book = input.next();

        CallableStatement callableStatement;
        callableStatement= connection.prepareCall("{call InsertPersonBook(?, ?)}");
        callableStatement.setString("SurmanePersonIn",surname);
        callableStatement.setString("BookNameIN",book);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next())
        {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: "+msg);
        }
    }

}
