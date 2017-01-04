package mundo.hola.jose.miprimerholamundo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Customer;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class Registrarse extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout contenedorDireccionRegistro, contenedorDatosPersonales;
    private TextView txtDatosP1R, txtDatosP2OR, txtDatosP3OR, txtDatosP5R, txtDatosP4OR, txtDatosP26OR, txtDatosD5R, txtDatosD6R, txtDatosD7R, txtDatosD8R, txtDatosD11R, txtDatosD12R, txtDatosD9R, txtDatosD10R, txtDatosD13R, txtRUCLabel;
    private EditText txtNombreR, txtApellidoR, txtFechaNacimientoR, txtCorreoR, txtClaveDR, txtDireccionDirR, txtDireccionDir2R, txtCiudadDirR, txtPaisDirR, txtEstadoDirR, txtInfoAdicionalDirR, txtTlfDirR, txtTlfMovilDirR, txtAliasDirR, txtRub;
    private RadioButton radioSrR, radioSraR;
    private ImageButton btnCalendarioR;
    private Button btnSiguienteR, btnRegistrarse;
    private ProgressBar loading_spinner;

    private int mShortAnimationDuration;

    static final int DIALOG_ID = 0;
    private int year_x, month_x, day_x;

    private SessionManager session;
    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrarse");
        getSupportActionBar().setSubtitle("Paso 1 / 2");

        contenedorDatosPersonales = (LinearLayout) findViewById(R.id.contenedorDatosPersonales);
        txtDatosP1R = (TextView) findViewById(R.id.txtDatosP1R);
        txtDatosP2OR = (TextView) findViewById(R.id.txtDatosP2OR);
        txtDatosP3OR = (TextView) findViewById(R.id.txtDatosP3OR);
        txtDatosP5R = (TextView) findViewById(R.id.txtDatosP5R);
        txtDatosP4OR = (TextView) findViewById(R.id.txtDatosP4OR);
        txtDatosP26OR = (TextView) findViewById(R.id.txtDatosP26OR);
        txtNombreR = (EditText) findViewById(R.id.txtNombreR);
        txtApellidoR = (EditText) findViewById(R.id.txtApellidoR);
        txtFechaNacimientoR = (EditText) findViewById(R.id.txtFechaNacimientoR);
        txtCorreoR = (EditText) findViewById(R.id.txtCorreoR);
        txtClaveDR = (EditText) findViewById(R.id.txtClaveDR);
        radioSrR = (RadioButton) findViewById(R.id.radioSrR);
        radioSraR = (RadioButton) findViewById(R.id.radioSraR);
        btnCalendarioR = (ImageButton) findViewById(R.id.btnCalendarioR);
        btnSiguienteR = (Button) findViewById(R.id.btnSiguienteR);

        contenedorDireccionRegistro = (LinearLayout) findViewById(R.id.contenedorDireccionRegistro);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        txtDatosD5R = (TextView) findViewById(R.id.txtDatosD5R);
        txtDatosD6R = (TextView) findViewById(R.id.txtDatosD6R);
        txtDatosD7R = (TextView) findViewById(R.id.txtDatosD7R);
        txtDatosD8R = (TextView) findViewById(R.id.txtDatosD8R);
        txtDatosD11R = (TextView) findViewById(R.id.txtDatosD11R);
        txtDatosD12R = (TextView) findViewById(R.id.txtDatosD12R);
        txtDatosD9R = (TextView) findViewById(R.id.txtDatosD9R);
        txtDatosD10R = (TextView) findViewById(R.id.txtDatosD10R);
        txtDatosD13R = (TextView) findViewById(R.id.txtDatosD13R);
        txtRUCLabel = (TextView) findViewById(R.id.txtRUCLabel);
        txtDireccionDirR = (EditText) findViewById(R.id.txtDireccionDirR);
        txtDireccionDir2R = (EditText) findViewById(R.id.txtDireccionDir2R);
        txtCiudadDirR = (EditText) findViewById(R.id.txtCiudadDirR);
        txtPaisDirR = (EditText) findViewById(R.id.txtPaisDirR);
        txtEstadoDirR = (EditText) findViewById(R.id.txtEstadoDirR);
        txtInfoAdicionalDirR = (EditText) findViewById(R.id.txtInfoAdicionalDirR);
        txtTlfDirR = (EditText) findViewById(R.id.txtTlfDirR);
        txtTlfMovilDirR = (EditText) findViewById(R.id.txtTlfMovilDirR);
        txtAliasDirR = (EditText) findViewById(R.id.txtAliasDirR);
        txtRub = (EditText) findViewById(R.id.txtRub);

        loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);

        // Gorditos y bonitos :3
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int tamano = (int) (displayMetrics.widthPixels / 2.7);

        txtDatosP1R.setWidth(tamano);
        txtDatosP2OR.setWidth(tamano);
        txtDatosP3OR.setWidth(tamano);
        txtDatosP5R.setWidth(tamano);
        txtDatosP4OR.setWidth(tamano);
        txtDatosP26OR.setWidth(tamano);
        txtDatosD5R.setWidth(tamano);
        txtDatosD6R.setWidth(tamano);
        txtDatosD7R.setWidth(tamano);
        txtDatosD8R.setWidth(tamano);
        txtDatosD11R.setWidth(tamano);
        txtDatosD12R.setWidth(tamano);
        txtDatosD9R.setWidth(tamano);
        txtDatosD10R.setWidth(tamano);
        txtDatosD13R.setWidth(tamano);
        txtRUCLabel.setWidth(tamano);

        Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR) - 18;      // Mayoria de edad
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

        txtPaisDirR.setText("Ecuador");
        txtEstadoDirR.setText("Azuay");

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        btnCalendarioR.setOnClickListener(this);
        btnSiguienteR.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCalendarioR:
                showDialog(DIALOG_ID);
                break;
            case R.id.btnSiguienteR:
                siguiente();
                break;
            case R.id.btnRegistrarse:
                registrarse();
                break;
        }
    }

    private void registrarse() {
        if (validarCampos()) {
            return;
        }

        showProgressbar();

        RequestParams parametros = new RequestParams();
        parametros.add("firstname", txtNombreR.getText().toString().trim());
        parametros.add("lastname", txtApellidoR.getText().toString().trim());
        parametros.add("birthday", txtFechaNacimientoR.getText().toString().trim());
        parametros.add("email", txtCorreoR.getText().toString().trim());
        parametros.add("passwd", txtClaveDR.getText().toString().trim());
        parametros.add("id_gender", radioSrR.isChecked() ? "1" : "2");

        client.post(conversor.WEB_API_AUX + "CCustomer.php?Create=Creating", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                buscarCustomerId();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private boolean validarCampos() {
        if (!txtNombreR.getText().toString().trim().equals("")
                && !txtApellidoR.getText().toString().trim().equals("")
                && !txtFechaNacimientoR.getText().toString().trim().equals("")
                && !txtCorreoR.getText().toString().trim().equals("")
                && !txtClaveDR.getText().toString().trim().equals("")
                && !txtDireccionDirR.getText().toString().trim().equals("")
                && !txtDireccionDir2R.getText().toString().trim().equals("")
                && !txtCiudadDirR.getText().toString().trim().equals("")
                && !txtPaisDirR.getText().toString().trim().equals("")
                && !txtEstadoDirR.getText().toString().trim().equals("")
                && !txtInfoAdicionalDirR.getText().toString().trim().equals("")
                && !txtAliasDirR.getText().toString().trim().equals("")
                && !txtRub.getText().toString().trim().equals("")
                && (!txtTlfDirR.getText().toString().trim().equals("")
                || !txtTlfMovilDirR.getText().toString().trim().equals(""))) {
            return false;
        } else {
            conversor.mensajeCorto("Rellene todos los campos");
            return true;
        }
    }

    private void buscarCustomerId() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/customers?filter[firstname]=" + txtNombreR.getText().toString().trim() + "&filter[lastname]=" + txtApellidoR.getText().toString().trim() + "&email=" + txtCorreoR.getText().toString().trim(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String id = conversor.existeCustomer(res);
                agregarNuevaDireccion(id);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void agregarNuevaDireccion(final String id) {

        RequestParams parametros = new RequestParams();
        parametros.add("id_customer", id);
        parametros.add("id_country", "81");
        parametros.add("id_state", "313");
        parametros.add("alias", txtAliasDirR.getText().toString().trim());
        parametros.add("firstname", txtNombreR.getText().toString().trim());
        parametros.add("lastname", txtApellidoR.getText().toString().trim());
        parametros.add("address1", txtDireccionDirR.getText().toString().trim());
        parametros.add("address2", txtDireccionDir2R.getText().toString().trim());
        parametros.add("city", txtCiudadDirR.getText().toString().trim());
        parametros.add("phone", txtTlfDirR.getText().toString().trim().equals("") ? "" : txtTlfDirR.getText().toString().trim());
        parametros.add("phone_mobile", txtTlfMovilDirR.getText().toString().trim().equals("") ? "" : txtTlfMovilDirR.getText().toString().trim());
        parametros.add("other", txtInfoAdicionalDirR.getText().toString().trim());
        parametros.add("dni", txtRub.getText().toString().trim());

        conversor.parametrosKey(parametros);

        client.post(conversor.WEB_API_AUX + "CAddress.php?Create=Creating", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                buscarCustomer(id);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarCustomer(String id) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/customers/" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Customer customer = conversor.buscarCustomer(res);
                login(customer);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void login(Customer customer) {
        session.createLoginSession(customer);

        Intent i = new Intent(this, Inicio.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    private void showProgressbar() {
        loading_spinner.setAlpha(0f);
        loading_spinner.setVisibility(View.VISIBLE);
        loading_spinner.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        contenedorDireccionRegistro.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                contenedorDireccionRegistro.setVisibility(View.GONE);
            }
        });
    }

    private void siguiente() {
        contenedorDireccionRegistro.setVisibility(View.VISIBLE);
        contenedorDatosPersonales.setVisibility(View.GONE);
        getSupportActionBar().setSubtitle("Paso 2 / 2");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            txtFechaNacimientoR.setText(year_x + "-" + month_x + "-" + day_x);
        }
    };

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (contenedorDireccionRegistro.getVisibility() == View.VISIBLE) {
                    contenedorDireccionRegistro.setVisibility(View.GONE);
                    contenedorDatosPersonales.setVisibility(View.VISIBLE);
                    getSupportActionBar().setSubtitle("Paso 1 / 2");
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}