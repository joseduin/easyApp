package mundo.hola.jose.miprimerholamundo;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.*;

public class Contacto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView emailContacto, telefonoMovilContacto, telefonoFijoContacto;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contacte con nosotros");

        emailContacto = (TextView) findViewById(R.id.emailContacto);
        telefonoMovilContacto = (TextView) findViewById(R.id.telefonoMovilContacto);
        telefonoFijoContacto = (TextView) findViewById(R.id.telefonoFijoContacto);

        buscarDatos();
    }

    private void buscarDatos() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/addresses/1", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                mundo.hola.jose.miprimerholamundo.modelo.Direccion direccion = conversor.buscarDireccion(res);

                emailContacto.setText(direccion.getOther());
                telefonoFijoContacto.setText(direccion.getPhone());
                telefonoMovilContacto.setText(direccion.getPhone_mobile());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
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
