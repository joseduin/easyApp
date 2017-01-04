package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Customer;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class Login extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private EditText txtCorreo, txtClave;
    private Button btnLogin, btnRegistrar;

    private SessionManager session;
    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());
        isLogIn();

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        btnLogin.setOnClickListener(this);
		btnRegistrar.setOnClickListener(this);
    }

    private void isLogIn() {
        if(session.isLoggedIn()){
            Intent i = new Intent(getApplicationContext(), Inicio.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnLogin:
		
				if (!txtCorreo.getText().toString().trim().equals("") && !txtClave.getText().toString().trim().equals("")){

				existeCustomer(txtCorreo.getText().toString(), conversor.getMD5(txtClave.getText().toString()));
				} else {
					if (txtCorreo.getText().toString().trim().equals("")) {
						conversor.mensajeCorto("Introduzca el correo electronico");
					} else {
						conversor.mensajeCorto("Introduzca su contraseña");
					}

				}
				break;
			case R.id.btnRegistrar:
                Intent i = new Intent(this, Registrarse.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
		}
    }

    public void existeCustomer(String email, String password) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/customers?filter[passwd]=" + password + "&filter[email]=" + email, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String existe = conversor.existeCustomer(res);
                if (!existe.equals("no")) {
                    buscarCustomer(existe);
                } else {
                    conversor.mensajeLargo("La combinación de correo/contraseña incorrecta");
                }
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
