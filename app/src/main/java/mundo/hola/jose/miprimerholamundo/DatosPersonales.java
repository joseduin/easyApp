package mundo.hola.jose.miprimerholamundo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.StringTokenizer;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Customer;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class DatosPersonales extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ImageButton btnCalendario;
    private Button btnGuardar;
    private EditText txtFechaNacimiento, txtCorreo, txtApellido, txtNombre, txtClaveD, txtClaveND, txtReClaveND;
    private RadioButton radioSr, radioSra;
    private TextView txtDatosP1, txtDatosP2O, txtDatosP3O, txtDatosP4O, txtDatosP5, txtDatosP26O, txtDatosP7, txtDatosP8;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    private SessionManager session;

    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    private String USER_ID = "";
    private String USER_PASSWORD = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Datos Personales");

        btnCalendario = (ImageButton) findViewById(R.id.btnCalendario);
        txtFechaNacimiento = (EditText) findViewById(R.id.txtFechaNacimiento);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        radioSr = (RadioButton) findViewById(R.id.radioSr);
        radioSra = (RadioButton) findViewById(R.id.radioSra);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        txtDatosP1 = (TextView) findViewById(R.id.txtDatosP1);
        txtDatosP2O = (TextView) findViewById(R.id.txtDatosP2O);
        txtDatosP3O = (TextView) findViewById(R.id.txtDatosP3O);
        txtDatosP4O = (TextView) findViewById(R.id.txtDatosP4O);
        txtDatosP5 = (TextView) findViewById(R.id.txtDatosP5);
        txtDatosP26O = (TextView) findViewById(R.id.txtDatosP26O);
        txtDatosP7 = (TextView) findViewById(R.id.txtDatosP7);
        txtDatosP8 = (TextView) findViewById(R.id.txtDatosP8);
        txtClaveD = (EditText) findViewById(R.id.txtClaveD);
        txtClaveND = (EditText) findViewById(R.id.txtClaveND);
        txtReClaveND = (EditText) findViewById(R.id.txtReClaveND);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());
        Customer customer = session.getCustomerCurrent();

        USER_ID = customer.getId();
        USER_PASSWORD = customer.getPasswd();

        txtCorreo.setText(customer.getEmail());
        txtNombre.setText(customer.getFirstname());
        txtApellido.setText(customer.getLastname());

        StringTokenizer token = new StringTokenizer(customer.getBirthday(), "-");
        year_x = Integer.parseInt(token.nextToken());
        month_x = Integer.parseInt(token.nextToken());
        day_x = Integer.parseInt(token.nextToken());

        txtFechaNacimiento.setText(year_x + "-" + month_x + "-" + day_x);

        if (customer.getId_gender().equals("1")) {
            radioSr.setChecked(true);
        } else {
            radioSra.setChecked(true);
        }

        // Gorditos y bonitos :3
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int tamano = (int) (displayMetrics.widthPixels / 2.7);

        txtDatosP1.setWidth(tamano);
        txtDatosP2O.setWidth(tamano);
        txtDatosP3O.setWidth(tamano);
        txtDatosP4O.setWidth(tamano);
        txtDatosP5.setWidth(tamano);
        txtDatosP26O.setWidth(tamano);
        txtDatosP7.setWidth(tamano);
        txtDatosP8.setWidth(tamano);

        btnCalendario.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCalendario:
                showDialog(DIALOG_ID);
                break;
            case R.id.btnGuardar:
                guardar();
                break;
        }
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
            txtFechaNacimiento.setText(year_x + "-" + month_x + "-" + day_x);
        }
    };

    private void guardar() {
        if (validarGuardar()) {
            return;
        }

        String reClave = validarCambioDeClave() ? txtClaveND.getText().toString().trim() : txtClaveD.getText().toString().trim();
        int genero = radioSr.isChecked() ? 1 : 2;

        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_API_AUX + "UCustomerData.php?id=" + USER_ID + "&lstn=" + txtApellido.getText().toString().trim() + "&fstn=" + txtNombre.getText().toString().trim() + "&eml=" + txtCorreo.getText().toString().trim() + "&gndr=" + genero + "&brdy=" + txtFechaNacimiento.getText().toString().trim() + "&pssw=" + conversor.getMD5(txtClaveD.getText().toString().trim()) + "&nwpw=" + reClave + "&nwpwcn=" + reClave, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                conversor.mensajeCorto("Cambios guardados!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private boolean validarGuardar() {
        if (!txtNombre.getText().toString().trim().equals("")
                && !txtApellido.getText().toString().trim().equals("")
                && !txtCorreo.getText().toString().trim().equals("")
                && !txtClaveD.getText().toString().trim().equals("")) {

            if (USER_PASSWORD.equals(conversor.getMD5(txtClaveD.getText().toString()))) {
                return false;
            } else {
                conversor.mensajeLargo("Fallo en autentificar su contraseña actual");
                return true;
            }
        } else {
            conversor.mensajeLargo("Los campos obligatorios debe ser llenados");
            return true;
        }
    }

    private boolean validarCambioDeClave() {
        if (!txtClaveND.getText().toString().trim().equals("")
                || !txtReClaveND.getText().toString().trim().equals("")) {

            if (txtClaveND.getText().toString().trim().equals(txtReClaveND.getText().toString().trim())) {
                return true;
            } else {
                conversor.mensajeLargo("Contraseñas no coinciden");
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
