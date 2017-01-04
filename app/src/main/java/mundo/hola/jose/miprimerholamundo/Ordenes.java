package mundo.hola.jose.miprimerholamundo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Order;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class Ordenes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewGroup contenedorOrdenes;
    private TextView existenciaOrdenes;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();
    private String ID_USUARIO;

    private ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mis Pedidos");

        conversor.setContext(this.getApplicationContext());
        ID_USUARIO = new SessionManager(getApplicationContext()).getCustomerCurrent().getId();

        contenedorOrdenes = (ViewGroup) findViewById(R.id.contenedorOrdenes);
        existenciaOrdenes = (TextView) findViewById(R.id.ecistenciaOrdenes);

        existeOrdenes();
    }

    private void existeOrdenes() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/orders?filter[id_customer]=" + ID_USUARIO + "&sort=[id_DESC]", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ids = conversor.existeOrdenes(res);
                if (ids.size() > 0) {
                    buscarOrden(0);
                } else {
                    existenciaOrdenes.setText("Ud aún no tiene un historial de ordenes.");
                    existenciaOrdenes.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarOrden(final int hijo) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/orders/" + ids.get(hijo), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Order order = conversor.buscarOrden(res);
                seguirBuscando(order, hijo);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscando(Order order, int hijo) {
        imprimir(order);

        hijo++;
        if (hijo < ids.size()) {
            buscarOrden(hijo);
        }
    }

    private void imprimir(final Order order) {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.orden_item, contenedorOrdenes, false);

        TextView txtFechaOrden = (TextView) newView.findViewById(R.id.txtFechaOrden);
        TextView txtReferenciaOrden = (TextView) newView.findViewById(R.id.txtReferenciaOrden);
        TextView txtPrecioOrden = (TextView) newView.findViewById(R.id.txtPrecioOrden);
        TextView txtPagoOrden = (TextView) newView.findViewById(R.id.txtPagoOrden);
        WebView txtEstadoOrden = (WebView) newView.findViewById(R.id.txtEstadoOrden);

        txtFechaOrden.setText(order.getDate_add());
        txtReferenciaOrden.setText(order.getReference());
        txtPrecioOrden.setText("$" + String.format("%.2f", Double.valueOf(order.getTotal_paid())));
        txtPagoOrden.setText(order.getPayment());
        buscarEstado(txtEstadoOrden, order);

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irOrden(order);
            }
        });

        contenedorOrdenes.addView(newView);
    }

    private void buscarEstado(final WebView txtEstadoOrden, Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/order_states/" + order.getCurrent_state(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String[] valores = conversor.buscarEstado(res);
                String estado = "<span style=\"background-color:" + valores[0] + "; border-color:" + valores[0] + ";padding: 6px 10px; color: white;\">" + valores[1] + "</span>";
                txtEstadoOrden.loadDataWithBaseURL(null, estado, "text/html", "utf-8", null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void irOrden(Order order) {
        Intent intent = new Intent(this.getApplicationContext(), Orden_item.class);
        intent.putExtra("order", order);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
