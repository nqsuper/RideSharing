package uts.edu.aip.utilities;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author NQ
 * @version 1.0
 * 
 * a singleton class which is allowed other class can easily to access the method
 * 
 */
public class AppUtil {

    private static AppUtil instance = null;
    private Connection conn = null;

    private AppUtil() {
        // Exists only to defeat instantiation.
    }
    
    public static AppUtil getInstance() {
        if (instance == null) {
            instance = new AppUtil();
        }
        return instance;
    }
    // get the connection from DataSource
    // return a connection object
    public Connection getConnection() throws SQLException {
        try {
            DataSource ds = (DataSource) InitialContext.doLookup(Constant.DATABASE_URL);
            conn = ds.getConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    // show the message on the xhtml page in myForm:appMessage
    public void showError(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("myForm:appMessage", new FacesMessage(message));
    }

    // return the string of the current date by the defined format
    public String getStringDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        return df.format(today);
    }
    // validate the time provided and the current time from the system
    public boolean isValidTime(String time) {
        String[] parts = time.split(":");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
        cal1.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

        Date today = Calendar.getInstance().getTime();
        return today.getTime() <= cal1.getTime().getTime();
    }

    // return the string of date by the format 
    public String getStringDateByFormat(String format) {
        DateFormat df = new SimpleDateFormat(format);
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        return df.format(today);
    }

    // return the hash string with the supported data which is encrypted using SHA-256 algorithm
    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    // convert byte to hex with bytes as parameter
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(AppUtil.getInstance().getStringDate());
//    }
    }
