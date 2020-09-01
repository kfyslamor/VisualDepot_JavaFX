package sample.Database;

import javafx.application.Platform;

import java.sql.*;
import java.util.Scanner;

/*
1. import
2. load & register the driver -> com.mysql.jdbc.Driver
3. connection
4. create a statement
5. execute the query
6. process the result
7. close

 */


public class MySQLConnection {
    final String DRIVER_URL = "com.mysql.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost:3306/demodb";
    String UN = "root";
    String PW = "Rootkfy2000#";

    /**
     * Entering a database and also entering username and password. Basically user access.
     * @param DB_URL
     * @param UN
     * @param PW
     */
    public MySQLConnection(String DB_URL, String UN,String PW){
        this.DB_URL = "jdbc:mysql://localhost:3306/" + DB_URL;
        this.UN = UN;
        this.PW =PW;
    }

    /**
     *
     * @param DB_URL = Entering a database specified by the root user. For admin and root access.
     */
    public MySQLConnection(String DB_URL){
        this.DB_URL = "jdbc:mysql://localhost:3306/" + DB_URL;
    }
    public MySQLConnection(){

    }

    /**
     * Made as a prototype later to be improved depending on the necessities and expectations.
     * @param SQLQuery
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getTable(String SQLQuery) throws ClassNotFoundException, SQLException {
        //String query = "SELECT * FROM Raf_A";
        Statement st=null;
        ResultSet rs=null;
        Connection con = null;
        try{
            Class.forName(DRIVER_URL);
            con = DriverManager.getConnection(DB_URL,UN,PW);
            st = con.createStatement();
            rs = st.executeQuery(SQLQuery); // RETURNS THE TABLE STRUCTURE YOU HAVE REQUESTED
        }
        catch(Exception e){
            e.printStackTrace();
        }


        System.out.println("productName - productAmount - lastuseDate - entryDate - capacity");
        while(rs.next()){
            //String name = rs.getString("product_name");
            System.out.println(rs.getString(1) + " " + rs.getInt(2) + " " +  rs.getDate(3) + " "+  rs.getDate(4) + " " + rs.getInt(5));
            //System.out.println(name);
        }
        con.close();
        st.close();
        // we need a class that implements Connection or we need to search for a method
        // which will return an instance of Connection.



        System.out.println("St closed,con closed");
        return rs;
    }
    /**
     *
     * @param SQLQuery
     */
    //TODO http://alvinalexander.com/java/java-mysql-insert-example-preparedstatement/
    public void insertToTable(String SQLQuery) throws SQLException {
        Statement st=null;
        Connection con = null;
        String SQLQueryFULL = "INSERT INTO urundepo (irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu) " +
                SQLQuery;
        //String SQLQuery1 = "INSERT INTO Users(id,username,password) VALUES("+ (nextMember + 1) +",\"" + name + "\"," + "\""+generatedSecuredPasswordHash + "\");";
        try{
            Class.forName(DRIVER_URL);
            con = DriverManager.getConnection(DB_URL,UN,PW);
            st = con.createStatement();
            st.executeUpdate(SQLQueryFULL);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            st.close();
            con.close();
        }
    }

    /**
     * I DON'T KNOW WHAT THE FUCK IS SHIT METHOD IS DOING RN, IT MAY INVADE MY COMPUTER IDK
     * @param name
     * @return
     */
    public String checkLogin(String name) throws SQLException {
        Statement st=null;
        ResultSet rs=null;
        Connection con = null;
        String requiredPass=null;
        boolean answer=false;
        String SQLQuery = "SELECT username,password FROM Users WHERE username = '" + name + "';"; // returns the username
        //String SQLQuery2= "SELECT password FROM Users WHERE username = '" + name + "';"; // returns the username's password.
        try{
            Class.forName(DRIVER_URL);
            con = DriverManager.getConnection(DB_URL,UN,PW);
            st = con.createStatement();
            rs = st.executeQuery(SQLQuery); // RETURNS THE TABLE STRUCTURE YOU HAVE REQUESTED
            while(rs.next()){
                if (rs.getString("username").equals(name)){
                    answer = true;
                    requiredPass = rs.getString("password");
                    System.out.println(rs.getString("password"));
                } // if there is a match in the database for the name inputted, set answer to true.
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            st.close();
            rs.close();
            con.close();
        }
        if (answer){
            return requiredPass;
        }
        else{
            System.out.println("NO MATCH FOR THE NAME");
            System.out.println(requiredPass);
            System.out.println(" ^^ ");
            Platform.exit();
            return null;
        }
    }

    /**
     * Takes 2 parameters to register into database's USERS table.
     * @param
     * @param generatedSecuredPasswordHash
     * @return true if
     * @throws SQLException
     */
    //TODO refactor the while loop in a way so you can't register an already registered name & surname.
    public boolean insertSignup(String name,String generatedSecuredPasswordHash) throws SQLException {
        Statement st=null;
        ResultSet rs1=null,rs=null;
        Connection con = null;
        boolean answer= false;


        //INSERT INTO Users(id,username,password) VALUES(1,"yoshi","yarrak");
        // INSERT INTO Users(id,username,password) VALUES(1,"yoshi","yarrak");
        try{
            Class.forName(DRIVER_URL);
            con = DriverManager.getConnection(DB_URL,UN,PW);
            st = con.createStatement();

            rs1 = st.executeQuery("SELECT MAX(id) FROM Users;");
            rs1.next();
            int nextMember = rs1.getInt(1);
            //INSERT INTO Users(id,username,password) VALUES(X,"name","password");
            String SQLQuery1 = "INSERT INTO Users(id,username,password) VALUES("+ (nextMember + 1) +",\"" + name + "\"," + "\""+generatedSecuredPasswordHash + "\");"; //TODO FIX THIS SHIT
            System.out.println(SQLQuery1);
            st.executeUpdate(SQLQuery1); // EXECUTES THE QUERY.
            rs = st.executeQuery("SELECT * FROM Users");
            System.out.println();
            while (rs.next()){
                System.out.println(new StringBuilder().append(rs.getString("id")).append(" ").append(rs.getString("username")).append(" ").append(rs.getString("password")).toString());
                if (rs.getString("username").equals(name) && rs.getString("password").equals(generatedSecuredPasswordHash)){
                    answer = true;
                }
            }
            System.out.println();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            st.close();
            rs.close(); rs1.close();
            con.close();
        }
        return answer;
    }
}
