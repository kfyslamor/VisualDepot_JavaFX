package sample.Database;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Boxes.AlertBox;
import sample.Scenes.ProductScenes.Product;

import java.sql.*;
import java.time.LocalDate;
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

    }//TODO put the necessary connection statements inside the class constructors.

    /**
     * Used for executing updates in the database.
     * @param SQLQuery takes a full SQL Query and uses executeUpdate() to execute the query in the database.
     * @throws SQLException
     */
    public void queryToDB(String SQLQuery){
        Statement st=null;
        ResultSet rs=null; // RS isn't necessary as we don't get any table data.
        Connection con = null;
        try{
            Class.forName(DRIVER_URL);
            con = DriverManager.getConnection(DB_URL,UN,PW);
            st = con.createStatement();
            st.executeUpdate(SQLQuery);
            con.close();
            st.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println("Connection closed.");
        }
    }
    /**
     *
     * @param SQLQuery
     * @return returns an ObservableList<Product>
     * @throws ClassNotFoundException = throws this exception if Class.forName() can't register the driver.
     * @throws SQLException
     */
    public ObservableList<Product> getTable(String SQLQuery) {
        Statement st=null;
        ResultSet rs=null;
        Connection con = null;
        ObservableList<Product> queryResult = FXCollections.observableArrayList();
        try{
            Class.forName(DRIVER_URL);
            con = DriverManager.getConnection(DB_URL,UN,PW);
            st = con.createStatement();
            rs = st.executeQuery(SQLQuery); // RETURNS THE TABLE STRUCTURE YOU HAVE REQUESTED

            // System.out.println("irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu");
            System.out.println("VV Requested Products VV");
            while(rs.next()){
                queryResult.add(new Product(rs.getString("irsaliyeNo"),rs.getString("urunAdi"),rs.getInt("urunMiktari"),LocalDate.parse(rs.getString("girisTarihi")) ,LocalDate.parse(rs.getString("SKTTarihi"))));
                System.out.println("--------------------------------------------------------------------------------------------------------------------");
                System.out.println(new Product(rs.getString("irsaliyeNo"),rs.getString("urunAdi"), rs.getInt("urunMiktari"),LocalDate.parse(rs.getString("girisTarihi")) , LocalDate.parse(rs.getString("SKTTarihi"))));
                //System.out.println(name);
            } //urun adi,miktar, SKT
            //TODO https://howtodoinjava.com/java/date-time/localdate-format-example/ change the format to dd-MM-YY

            con.close();
            st.close();
            rs.close();
        }
        catch(Exception e){
            new AlertBox().display("Lütfen girdiğiniz değerleri kontrol ediniz.");
            e.printStackTrace();
        }


        //System.out.println("Connection is closed.");
        // we need a class that implements Connection or we need to search for a method
        // which will return an instance of Connection.



        System.out.println("Connection Closed. queryResult returned.");
        return queryResult;
    }

    //TODO http://alvinalexander.com/java/java-mysql-insert-example-preparedstatement/
    public void insertToTable(String tableName,String SQLQuery) throws SQLException {
        Statement st=null;
        Connection con = null;
        String SQLQueryFULL = "INSERT INTO " + tableName +" (irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu) " +
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
     *
     * @param name = end-user's input for "user-name" attribute.
     * @return hashes the user input and then compares the hashed version fetched from the database.
     * function returns true, if there is a match in the database.
     * returns false if there isn't a match in the database.
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
            System.out.println("Connection Closed.");
        }
        if (answer){
            return requiredPass;
        }
        else{
            System.out.println("NO MATCH FOR THE NAME");
            System.out.println(requiredPass);
            System.out.println(" ^^ ");
            //new AlertBox().display("Üzgünüz, bu kullanıcı adı mevcut değil.");
            //Platform.exit();

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
            String SQLQuery1 = "INSERT INTO Users(id,username,password) VALUES("+ (nextMember + 1) +",\"" + name + "\"," + "\""+generatedSecuredPasswordHash + "\");";
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
