package mundo.hola.jose.miprimerholamundo.modelo;

/**
 * Created by Jose on 1/11/2016.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import mundo.hola.jose.miprimerholamundo.Inicio;
import mundo.hola.jose.miprimerholamundo.Login;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";
    public static final String KEY_LAST_NAME = "lastname";
    public static final String KEY_FIRST_NAME = "firstname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "id_gender";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Customer customer) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID, customer.getId());
        editor.putString(KEY_LAST_NAME, customer.getLastname());
        editor.putString(KEY_FIRST_NAME, customer.getFirstname());
        editor.putString(KEY_EMAIL, customer.getEmail());
        editor.putString(KEY_GENDER, customer.getId_gender());
        editor.putString(KEY_BIRTHDAY, customer.getBirthday());
        editor.putString(KEY_COMPANY, customer.getCompany());
        editor.putString(KEY_PASSWORD, customer.getPasswd());

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkIn(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void checkOut(){
        if(this.isLoggedIn()){
            Intent i = new Intent(_context, Inicio.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }



    /**
     * Get stored session data
     * */
    public Customer getCustomerCurrent(){
        Customer customer = new Customer();

        customer.setId(pref.getString(KEY_ID, null));
        customer.setLastname(pref.getString(KEY_LAST_NAME, null));
        customer.setFirstname(pref.getString(KEY_FIRST_NAME, null));
        customer.setEmail(pref.getString(KEY_EMAIL, null));
        customer.setId_gender(pref.getString(KEY_GENDER, null));
        customer.setBirthday(pref.getString(KEY_BIRTHDAY, null));
        customer.setCompany(pref.getString(KEY_COMPANY, null));
        customer.setPasswd(pref.getString(KEY_PASSWORD, null));

        return customer;
    }

    /**
     * Clear session details
     * */
    public void logout(){
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}
