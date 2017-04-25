package sample;

import com.mysql.jdbc.Connection;
import javafx.application.Platform;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller implements Runnable {

    private String host = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11170108?zeroDateTimeBehavior=convertToNull";
    private String username = "sql11170108";
    private String password = "gHBfXulEu8";

    Connection con;
    FXMLController maps;
    Lock lock;


    public Controller(Lock lock, FXMLController maps)
    {
        this.lock = lock;
        this.maps = maps;
    }

    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = (Connection) DriverManager.getConnection(host, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();


        }
    }

    public void select()
    {

        try {
            Statement st = con.createStatement();
            String sql = ("Select * from DPT");
            ResultSet res = st.executeQuery(sql);

            Platform.runLater(() ->
            {
                try {
                    while ( res.next( ) ) {
                        System.out.println(
                                res.getTimestamp("Timestamp") + " " +
                                        res.getString("Name") + " " +
                                        res.getTime("Time").toString() + " " +
                                        res.getDate("Time").toString() + " " +
                                        res.getString("Origin") + " "+
                                        res.getString("Destination") + " " +
                                        res.getInt("Status") + " "


                        );
                        maps.addMarker(res.getString("Name"),
                        res.getString("Origin"),
                                res.getString("Name"));

                            }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                maps.refresh();
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        synchronized(lock)
        {
            try {
                System.out.println("loading...");
                lock.wait();
            } catch (InterruptedException ex) {

            }

        }

        System.out.println("unlocked");
        connect();
        select();
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
