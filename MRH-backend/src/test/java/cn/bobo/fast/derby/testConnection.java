package cn.bobo.fast.derby;

import org.aspectj.weaver.ast.Test;

import java.sql.*;

/**
 * Created by Administrator on 2018/12/25 0025.
 */
public class testConnection {

        private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        private static String protocol = "jdbc:derby:";
        String dbName = "C:\\Program Files\\derby\\db-derby-10.14.2.0-bin\\db-derby-10.14.2.0-bin\\bin\\cardMouth";

        static void loadDriver() {
            try {
                Class.forName(driver).newInstance();
                System.out.println("Loaded the appropriate driver");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void doIt() {
            Connection conn = null;
            Statement s = null;
            ResultSet rs = null;

            System.out.println("starting");
            try {
                conn = DriverManager.getConnection(protocol + dbName
                        + ";create=true");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Connected to and created database " + dbName);

            try {

                s=conn.createStatement();
                rs=s.executeQuery("select * from sys_user");

                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString(3));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                conn.close();
                conn = null;
                s.close();
                s = null;
                rs.close();
                rs = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
           testConnection t =new testConnection();

           t.doIt();
        }
    }
