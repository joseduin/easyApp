package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Customer;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class Direccion extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    Button btnGuardarDireccion;
    TextView txtDatosD1, txtDatosD2, txtDatosDRuc, txtDatosD3, txtDatosD5, txtDatosD6, txtDatosD7, txtDatosD8, txtDatosD9, txtDatosD10, txtDatosD11, txtDatosD12, txtDatosD13;
    EditText txtNombreDir, txtRucDir, txtApellidoDir, txtEmpresaDir, txtDireccionDir, txtDireccionDir2, txtCiudadDir, txtPaisDir, txtTlfDir, txtTlfMovilDir, txtEstadoDir, txtInfoAdicionalDir, txtAliasDir;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    SessionManager session;
    private String ID_USUARIO;
    private String ID_DIR;
    private boolean NUEVA_DIR = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());

        Customer customer = session.getCustomerCurrent();
        ID_USUARIO = customer.getId();

        txtDatosD1 = (TextView) findViewById(R.id.txtDatosD1);
        txtDatosD2 = (TextView) findViewById(R.id.txtDatosD2);
        txtDatosD3 = (TextView) findViewById(R.id.txtDatosD3);
        txtDatosD5 = (TextView) findViewById(R.id.txtDatosD5);
        txtDatosD1 = (TextView) findViewById(R.id.txtDatosD1);
        txtDatosD6 = (TextView) findViewById(R.id.txtDatosD6);
        txtDatosD7 = (TextView) findViewById(R.id.txtDatosD7);
        txtDatosD8 = (TextView) findViewById(R.id.txtDatosD8);
        txtDatosD9 = (TextView) findViewById(R.id.txtDatosD9);
        txtDatosD10 = (TextView) findViewById(R.id.txtDatosD10);
        txtDatosD11 = (TextView) findViewById(R.id.txtDatosD11);
        txtDatosD12 = (TextView) findViewById(R.id.txtDatosD12);
        txtDatosD13 = (TextView) findViewById(R.id.txtDatosD13);
        txtDatosDRuc = (TextView) findViewById(R.id.txtDatosDRuc);
        btnGuardarDireccion = (Button) findViewById(R.id.btnGuardarDireccion);

        txtEmpresaDir = (EditText) findViewById(R.id.txtEmpresaDir);
        txtDireccionDir2 = (EditText) findViewById(R.id.txtDireccionDir2);
        txtTlfDir = (EditText) findViewById(R.id.txtTlfDir);
        txtTlfMovilDir = (EditText) findViewById(R.id.txtTlfMovilDir);
        txtInfoAdicionalDir = (EditText) findViewById(R.id.txtInfoAdicionalDir);
        txtRucDir = (EditText) findViewById(R.id.txtRucDir);

        txtAliasDir = (EditText) findViewById(R.id.txtAliasDir);
        txtNombreDir = (EditText) findViewById(R.id.txtNombreDir);
        txtApellidoDir = (EditText) findViewById(R.id.txtApellidoDir);
        txtDireccionDir = (EditText) findViewById(R.id.txtDireccionDir);
        txtCiudadDir = (EditText) findViewById(R.id.txtCiudadDir);
        txtPaisDir = (EditText) findViewById(R.id.txtPaisDir);
        txtEstadoDir = (EditText) findViewById(R.id.txtEstadoDir);

        // Gorditos y bonitos :3
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int tamano = (int) (displayMetrics.widthPixels / 2.7);

        txtDatosD1.setWidth(tamano);
        txtDatosD2.setWidth(tamano);
        txtDatosD3.setWidth(tamano);
        txtDatosD5.setWidth(tamano);
        txtDatosD6.setWidth(tamano);
        txtDatosD7.setWidth(tamano);
        txtDatosD8.setWidth(tamano);
        txtDatosD9.setWidth(tamano);
        txtDatosD10.setWidth(tamano);
        txtDatosD11.setWidth(tamano);
        txtDatosD12.setWidth(tamano);
        txtDatosD13.setWidth(tamano);
        txtDatosDRuc.setWidth(tamano);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mundo.hola.jose.miprimerholamundo.modelo.Direccion direccion = (mundo.hola.jose.miprimerholamundo.modelo.Direccion) extras.getSerializable("direccion");

            NUEVA_DIR = true;
            ID_DIR = direccion.getId();

            getSupportActionBar().setTitle(direccion.getAlias());
            txtAliasDir.setText(direccion.getAlias());
            txtNombreDir.setText(direccion.getFirstname());
            txtApellidoDir.setText(direccion.getLastname());
            txtDireccionDir.setText(direccion.getAddress1());
            txtCiudadDir.setText(direccion.getCity());
            try {
                txtRucDir.setText(direccion.getDni());
            } catch (NullPointerException npe) {}


            validartextView(txtEmpresaDir, direccion.getCompany());
            validartextView(txtDireccionDir2, direccion.getAddress2());
            validartextView(txtTlfDir, direccion.getPhone());
            validartextView(txtTlfMovilDir, direccion.getPhone_mobile());
            validartextView(txtInfoAdicionalDir, direccion.getOther());
        } else {
            getSupportActionBar().setTitle("Nueva Dirección");
        }

        txtPaisDir.setText("Ecuador");
        txtEstadoDir.setText("Azuay");

        btnGuardarDireccion.setOnClickListener(this);
    }

    private void validartextView(TextView textView, String valor) {
        if (!valor.equals("")) {
            textView.setText(valor);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardarDireccion:
                guardar();
                break;
        }
    }

    private void guardar() {
        if (NUEVA_DIR) {
            actualizarDireccion();
        } else {
            agregarNuevaDireccion();
        }
    }

    private void actualizarDireccion() {
        if (validarActualizarDireccion()) {
            return;
        }

        String empresa =  txtEmpresaDir.getText().toString().trim().equals("") ? "" : txtEmpresaDir.getText().toString().trim();
        String dir2 = txtDireccionDir2.getText().toString().trim().equals("") ? "" : txtDireccionDir2.getText().toString().trim();
        String tlf = txtTlfDir.getText().toString().trim().equals("") ? "" : txtTlfDir.getText().toString().trim();
        String tlfm = txtTlfMovilDir.getText().toString().trim().equals("") ? "" : txtTlfMovilDir.getText().toString().trim();
        String infoAdi = txtInfoAdicionalDir.getText().toString().trim().equals("") ? "" : txtInfoAdicionalDir.getText().toString().trim();


        RequestParams parametros = new RequestParams();
        parametros.add("id", ID_DIR);
        parametros.add("id_customer", ID_USUARIO);
        parametros.add("alias", txtAliasDir.getText().toString().trim());
        parametros.add("company", empresa);
        parametros.add("lastname", txtApellidoDir.getText().toString().trim());
        parametros.add("firstname", txtNombreDir.getText().toString().trim());
        parametros.add("address1", txtDireccionDir.getText().toString().trim());
        parametros.add("address2", dir2);
        parametros.add("city", txtCiudadDir.getText().toString().trim());
        parametros.add("id_country", "81");
        parametros.add("phone", tlf);
        parametros.add("phone_mobile", tlfm);
        parametros.add("other", infoAdi);
        parametros.add("dni", txtRucDir.getText().toString().trim());

        client.post(conversor.WEB_API_AUX + "UAddress.php?id=" + ID_DIR, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                conversor.mensajeLargo("Se guardaron los cambios");
                guardadoExitoso();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private boolean validarActualizarDireccion() {
        if (!txtAliasDir.getText().toString().trim().equals("")
                && !txtNombreDir.getText().toString().trim().equals("")
                && !txtApellidoDir.getText().toString().trim().equals("")
                && !txtDireccionDir.getText().toString().trim().equals("")
                && !txtCiudadDir.getText().toString().trim().equals("")
                && !txtRucDir.getText().toString().trim().equals("")
                && (!txtTlfDir.getText().toString().trim().equals("")
                || !txtTlfMovilDir.getText().toString().trim().equals(""))) {
            return false;
        } else {
            conversor.mensajeCorto("Llene los campos obligatorios");
            return true;
        }
    }

    private void agregarNuevaDireccion() {
        RequestParams parametros = new RequestParams();
        parametros.add("id_customer", ID_USUARIO);
        parametros.add("id_country", "81");
        parametros.add("id_state", "313");
        parametros.add("alias", txtAliasDir.getText().toString().trim());
        parametros.add("company", txtEmpresaDir.getText().toString().trim().equals("") ? "" : txtEmpresaDir.getText().toString().trim());
        parametros.add("firstname", txtNombreDir.getText().toString().trim());
        parametros.add("lastname", txtApellidoDir.getText().toString().trim());
        parametros.add("address1", txtDireccionDir.getText().toString().trim());
        parametros.add("address2", txtDireccionDir2.getText().toString().trim().equals("") ? "" : txtDireccionDir2.getText().toString().trim());
        parametros.add("city", txtCiudadDir.getText().toString().trim());
        parametros.add("phone", txtTlfDir.getText().toString().trim().equals("") ? "" : txtTlfDir.getText().toString().trim());
        parametros.add("phone_mobile", txtTlfMovilDir.getText().toString().trim().equals("") ? "" : txtTlfMovilDir.getText().toString().trim());
        parametros.add("other", txtInfoAdicionalDir.getText().toString().trim().equals("") ? "" : txtInfoAdicionalDir.getText().toString().trim());
        parametros.add("dni", txtRucDir.getText().toString().trim());

        client.post(conversor.WEB_API_AUX + "CAddress.php?Create=Creating", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                conversor.mensajeLargo("Dirección guardada exitosamente");
                guardadoExitoso();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void guardadoExitoso() {
        Intent intent = new Intent(this, Direcciones.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                guardadoExitoso();
                return true;
            case R.id.inicioHome:
                Intent i = new Intent(this.getApplicationContext(), Inicio.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}
