/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author NQ
 */
public class SQLUtil {

    public static final String ID_FIELD = "ID";
    public static final String USER_NAME_FIELD = "USERNAME";
    public static final String PASSWORD_FIELD = "PASSWORD";
    public static final String USER_TYPE_FIELD = "USER_TYPE";
    public static final String FIRST_NAME_FIELD = "FIRST_NAME";
    public static final String LAST_NAME_FIELD = "LAST_NAME";
    public static final String PHONE_NO_FIELD = "PHONE_NO";
    public static final String REGISTRATION_DATE_FIELD = "REGISTRATION_DATE";

    public static final String MODEL_FIELD = "MODEL";
    public static final String IMAGE_FIELD = "IMAGE";

    public static final String USER_ID_FIELD = "USER_ID";
    public static final String VEHICLE_ID_FIELD = "VEHICLE_ID";
    public static final String PICKUP_LOCATION_FIELD = "PICKUP_LOCATION";
    public static final String STATUS_FIELD = "STATUS";
    public static final String AVAILABLE_SEATS_FIELD = "AVAILABLE_SEATS";
    public static final String PUBLISH_DATE_FIELD = "PUBLISH_DATE";
    public static final String PICKUP_TIME_FIELD = "PICKUP_TIME";
    public static final String FINAL_DESTINATION_FIELD = "FINAL_DESTINATION";

    private static SQLUtil instance = null;
    private Connection conn = null;

    private SQLUtil() {
        // Exists only to defeat instantiation.
    }

    public static SQLUtil getInstance() {
        if (instance == null) {
            instance = new SQLUtil();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
            try {
                //            String dbURL = "jdbc:derby://localhost:1527/RideSharing";
//            Properties properties = new Properties();
//            properties.put("user", "uts");
//            properties.put("password", "utsadmin");
                DataSource ds = (DataSource) InitialContext.doLookup("jdbc/ridesharing");
                conn = ds.getConnection();
            } catch (NamingException ex) {
                Logger.getLogger(SQLUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        return conn;
    }

    public String getStringDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        return df.format(today);
    }
    
    public String getStringDateByFormat(String format) {
        DateFormat df = new SimpleDateFormat(format);
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        return df.format(today);
    }

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(SQLUtil.getInstance().getStringDate());
//    }
}
