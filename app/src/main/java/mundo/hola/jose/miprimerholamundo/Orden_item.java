package mundo.hola.jose.miprimerholamundo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Order;
import mundo.hola.jose.miprimerholamundo.modelo.OrderItem;

public class Orden_item extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private WebView tablaProductos, tablaDetalles, order_item_estatus;
    private TextView txt_order_item_pago;

    private AsyncHttpClient client = new AsyncHttpClient();
    private Conversor conversor = new Conversor();
    private String dir_entrega;
    private String dir_cobro;
    private String envio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_item);

        conversor.setContext(this.getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tablaProductos = (WebView) findViewById(R.id.tablaProductos);
        tablaDetalles = (WebView) findViewById(R.id.tablaDetalles);
        order_item_estatus = (WebView) findViewById(R.id.order_item_estatus);
        txt_order_item_pago = (TextView) findViewById(R.id.txt_order_item_pago);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Order order = (Order) extras.getSerializable("order");
            getSupportActionBar().setTitle(order.getReference());

            txt_order_item_pago.setText(order.getPayment());
            buscarEstado(order);
            cargarproductos(order);
            if (order.getId_address_delivery().equals(order.getId_address_invoice())) {
                buscarDirecciones(order, true);
            } else {
                buscarDirecciones(order, false);
            }
        }
    }

    private void cargarproductos(Order order) {
        String tabla = "<style>\n" +
                "table, th, td {\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "    padding: 5px;\n" +
                "    text-align: left;\n" +
                "}\n" +
                "</style>" +

                        "<table style=\"width:100%\">" +
                            "<thead>" +
                                "<tr>" +
                                    "<th style=\"background-color: #f77e40 !important; color: white;\">Producto</th>" +
                                    "<th style=\"background-color: #f77e40 !important; color: white;\">Cantidad</th>" +
                                    "<th style=\"background-color: #f77e40 !important; color: white;\">Precio unitario</th>" +
                                    "<th style=\"background-color: #f77e40 !important; color: white;\">Precio total</th>" +
                                "</tr>" +
                            "</thead>" +
                            "<tbody>";
        for (OrderItem item : order.getProductos()) {
            double precio = Double.valueOf(item.getUnit_price_tax_incl()) * Double.valueOf(order.getTotal_paid());
            tabla += "<tr>" +
                        "<th>" + item.getProduct_name() + "</th>" +
                        "<th>" + item.getProduct_quantity() + "</th>" +
                        "<th> $" + String.format("%.2f", Double.valueOf(item.getUnit_price_tax_incl())) + "</th>" +
                        "<th> $" + String.format("%.2f", precio) + "</th>" +
                    "</tr>";
        }
        tabla += "</tbody>" +
                    "<tfoot>" +
                        "<tr>" +
                            "<th colspan=\"1\">" +
                                "<strong>artículos</strong>" +
                            "</th>" +
                            "<th colspan=\"3\"> $" + String.format("%.2f", Double.valueOf(order.getTotal_products_wt())) + "</th>" +
                        "</tr>" +
                        "<tr>" +
                            "<th colspan=\"1\">" +
                                "<strong>Envío</strong>" +
                            "</th>" +
                            "<th colspan=\"3\"> $" + String.format("%.2f", Double.valueOf(order.getTotal_shipping_tax_incl())) + "</th>" +
                        "</tr>" +
                        "<tr>" +
                            "<th colspan=\"1\" style=\"background-color: #FC5F10 !important; color: white;\">" +
                                "<strong>Total</strong>" +
                            "</th>" +
                            "<th colspan=\"3\"> $" + String.format("%.2f", Double.valueOf(order.getTotal_paid_tax_incl())) + "</th>" +
                        "</tr>" +
                    "</tfoot>" +
                "</table>";

        tablaProductos.loadDataWithBaseURL(null, tabla, "text/html", "utf-8", null);
    }

    private void buscarDirecciones(final Order order, final boolean iguales) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/addresses/" + order.getId_address_delivery(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                dir_entrega = conversor.buscarDireccion(res).getAlias();
                if (iguales) {
                    dir_cobro = dir_entrega;
                    buscarEnvio(order);
                } else {
                    buscarOtraDireccion(order);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarOtraDireccion(final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/addresses/" + order.getId_address_invoice(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                dir_cobro = conversor.buscarDireccion(res).getAlias();
                buscarEnvio(order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarEnvio(final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carriers/" + order.getId_carrier(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                envio = conversor.buscarEnvio(res).getName();
                imprimirDetalles(order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void imprimirDetalles(Order order) {
        String tabla2 = "<style>\n" +
                "table, th, td {\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "    padding: 5px;\n" +
                "    text-align: left;\n" +
                "}\n" +
                "</style>" +

                "<table style=\"width:100%\">" +
                "<tbody>" +
                "<tr>" +
                "<th colspan=\"1\" style=\"background-color: #f77e40 !important; color: white;\">Transportista</th>" +
                "<th colspan=\"3\">" + envio + "</th>" +
                "</tr>" +
                "<tr>" +
                "<th colspan=\"1\" style=\"background-color: #f77e40 !important; color: white;\">Dirección de entrega</th>" +
                "<th colspan=\"3\">" + dir_entrega + "</th>" +
                "</tr>" +
                "<tr>" +
                "<th colspan=\"1\" style=\"background-color: #f77e40 !important; color: white;\">Dirección de facturación</th>" +
                "<th colspan=\"3\">" + dir_cobro + "</th>" +
                "</tr>" +
                "</tbody>" +
                "</table>";

        tablaDetalles.loadDataWithBaseURL(null, tabla2, "text/html", "utf-8", null);
    }

    private void buscarEstado(Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/order_states/" + order.getCurrent_state(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String[] valores = conversor.buscarEstado(res);
                String estado = "<span style=\"background-color:" + valores[0] + "; border-color:" + valores[0] + ";padding: 6px 10px; color: white;\">" + valores[1] + "</span>";
                order_item_estatus.loadDataWithBaseURL(null, estado, "text/html", "utf-8", null);
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
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            case R.id.inicioHome:
                Intent i = new Intent(this.getApplicationContext(), Inicio.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
