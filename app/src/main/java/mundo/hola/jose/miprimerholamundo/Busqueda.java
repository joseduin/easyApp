package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.Adaptador.InteractiveScrollView;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Descuento;
import mundo.hola.jose.miprimerholamundo.modelo.Producto;

public class Busqueda extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, NavigationView.OnNavigationItemSelectedListener {

    private EditText txtBusqueda;
    private Button btn_ordenar_ninguno, btn_ordenar_mayor, btn_ordenar_menor, btn_filtro;
    private ImageButton btnatras;
    private LinearLayout contenedorBusqueda, contenedorFiltros, contenedorBadSearch;
    private TextView textExistenciaSearch;
    private GridLayout recycleProductosSearch;
    private ProgressBar loadingProductsSearch;
    private InteractiveScrollView scrollSearch;

    private String ORDENAR = "";
    private static int cantidadDeProductos = 2;     // Cantidad de productos por carga!!
    private int contarProductosImpresos = 1;
    private boolean loadingproductos = false;
    private int hijoProducto = 0;
    private ArrayList<Producto> productoLista = new ArrayList<>();
    private Calendar fechaActual = Calendar.getInstance();
    private static int widthProductos;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setCustomView(R.layout.activity_busqueda_toolbar);

        conversor.setContext(this.getApplicationContext());

        txtBusqueda = (EditText) actionBar.getCustomView().findViewById(R.id.txtBusqueda);
        btnatras = (ImageButton) actionBar.getCustomView().findViewById(R.id.btnatras);
        btn_ordenar_ninguno = (Button) findViewById(R.id.btn_ordenar_ninguno);
        btn_ordenar_mayor = (Button) findViewById(R.id.btn_ordenar_mayor);
        btn_ordenar_menor = (Button) findViewById(R.id.btn_ordenar_menor);
        btn_filtro = (Button) findViewById(R.id.btn_filtro);
        contenedorBusqueda = (LinearLayout) findViewById(R.id.contenedorBusqueda);
        contenedorFiltros = (LinearLayout) findViewById(R.id.contenedorFiltros);
        textExistenciaSearch = (TextView) findViewById(R.id.textExistenciaSearch);
        recycleProductosSearch = (GridLayout) findViewById(R.id.recycleProductosSearch);
        contenedorBadSearch = (LinearLayout) findViewById(R.id.contenedorBadSearch);
        scrollSearch = (InteractiveScrollView) findViewById(R.id.scrollSearch);
        loadingProductsSearch = (ProgressBar) findViewById(R.id.loadingProductsSearch);

        botonEstilo(true, btn_ordenar_ninguno);
        botonEstilo(false, btn_ordenar_mayor);
        botonEstilo(false, btn_ordenar_menor);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        txtBusqueda.setOnEditorActionListener(this);
        btn_ordenar_ninguno.setOnClickListener(this);
        btn_ordenar_mayor.setOnClickListener(this);
        btn_ordenar_menor.setOnClickListener(this);
        btnatras.setOnClickListener(this);
        btn_filtro.setOnClickListener(this);

        scrollSearch.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                if ((loadingproductos) && (hijoProducto < productoLista.size())) {
                    loadingproductos = false;
                    buscarImagene();
                    contarProductosImpresos = 1;
                    loadingProductsSearch.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void buscar() {
        if (!txtBusqueda.getText().toString().trim().equals("")) {
            txtBusqueda.setEnabled(false);
            buscarProductos(txtBusqueda.getText().toString());
        } else {
            conversor.mensajeLargo("Escriba lo que desee buscar");
        }
    }

    private void buscarProductos(String producto) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/products/?display=full&filter[name]=[" + producto + "]%25" + ORDENAR, parametros, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String res) {
                    productoLista = conversor.buscarProductoSearch(res);
                    hijoProducto = 0;
                    if (productoLista.size() > 0) {
                        recycleProductosSearch.removeAllViews();
                        buscarImagene();
                    } else {
                        sad_search();
                        txtBusqueda.setEnabled(true);
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    conversor.errorLoading();
                }
            });

    }

    private void sad_search() {
        if (contenedorBusqueda.getVisibility() == View.VISIBLE) {
            contenedorBusqueda.setVisibility(View.GONE);
        }
        if (contenedorFiltros.getVisibility() == View.VISIBLE) {
            contenedorFiltros.setVisibility(View.GONE);
        }
        contenedorBadSearch.setVisibility(View.VISIBLE);
    }


    private void buscarImagene() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/images/products/" + productoLista.get(hijoProducto).getId() + "/" + productoLista.get(hijoProducto).getImagenes().get(0) + "/large_default?", parametros, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                ArrayList<String> imagenes = productoLista.get(hijoProducto).getImagenes();
                imagenes.set(0 ,conversor.BitMapToString(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                productoLista.get(hijoProducto).setImagenes(imagenes);
                //buscarSiTieneDescuento();
                seguirBuscandoProducto();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                conversor.errorLoading();
            }
        });
    }
/*
    private void buscarSiTieneDescuento() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/specific_prices?filter[id_product]=" +  productoLista.get(hijoProducto).getId(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ArrayList<String> descuento = conversor.buscarDescuentos(res);
                if (descuento.size() > 0) {
                    buscarDescuento(new ArrayList<Descuento>(), descuento, 0);
                } else {
                    seguirBuscandoProducto();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarDescuento(final ArrayList<Descuento> desc, final ArrayList<String> descuentos_ids, final int hijo) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/specific_prices/" + descuentos_ids.get(hijo), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Descuento descuento = conversor.buscarDescuento(res);
                desc.add(descuento);
                seguirBuscandoDescuento(desc, descuentos_ids, hijo);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoDescuento(final ArrayList<Descuento> desc, final ArrayList<String> descuentos_ids, int hijo) {
        hijo++;
        if (hijo < descuentos_ids.size()) {     // ¿Hay mas descuentos?
            buscarDescuento(desc, descuentos_ids, hijo);
        } else {
            productoLista.get(hijoProducto).setDescuentos(desc);
            seguirBuscandoProducto();
        }
    }
*/
    private void seguirBuscandoProducto() {
        if (loadingProductsSearch.getVisibility() == View.VISIBLE) {
            loadingProductsSearch.setVisibility(View.GONE);
        }
        //buscarHoraEcuador();
        validarImprimirProductos();
    }
/*
    private void buscarHoraEcuador() {

        client.get(conversor.WEB_API_AUX + "hora.php", null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                try {
                    fechaActual.setTime(conversor.dateFormat.parse(res));
                    validarImprimirProductos();
                } catch (ParseException e) {}
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }
*/
    private void validarImprimirProductos() {
        imprimirProductos(productoLista.get(hijoProducto));

        hijoProducto++;
        if (contarProductosImpresos != cantidadDeProductos) {

            contarProductosImpresos++;
            if (hijoProducto < productoLista.size()) {     // ¿Hay mas productos?
                buscarImagene();
            } else {
                txtBusqueda.setEnabled(true);
                loadingproductos = true;
            }
        } else {
            txtBusqueda.setEnabled(true);
            loadingproductos = true;
        }
    }

    private void imprimirProductos(final Producto producto) {
        if (contenedorBusqueda.getVisibility() == View.GONE) {
            contenedorBusqueda.setVisibility(View.VISIBLE);
            contenedorFiltros.setVisibility(View.GONE);
        }
        if (contenedorBadSearch.getVisibility() == View.VISIBLE) {
            contenedorBadSearch.setVisibility(View.GONE);
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation == 0 || rotation == 180) {
            widthProductos = width;
        } else {
            widthProductos = (int) (width * 0.45);
        }

        String valor = productoLista.size() + " ";
        valor += (productoLista.size() == 1) ? "resultado encontrado." : "resultados encontrados.";
        textExistenciaSearch.setText(valor);
        textExistenciaSearch.setWidth((int) (width / 1.5));

        final Context context = this.getApplicationContext();
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.producto_item, recycleProductosSearch, false);

        TextView nombreProducto = (TextView) newView.findViewById(R.id.nombreProducto);
        TextView precioProducto = (TextView) newView.findViewById(R.id.precioProducto);
        TextView condicionProducto = (TextView) newView.findViewById(R.id.condicionProducto);
        ImageView imagenProducto = (ImageView) newView.findViewById(R.id.imagenProducto);

        LinearLayout contenedorDescuento = (LinearLayout)  newView.findViewById(R.id.contenedorDescuento);
        TextView txtPrecioDescuento = (TextView) newView.findViewById(R.id.txtPrecioDescuento);
        TextView txtPrecioDescuentoTachado = (TextView) newView.findViewById(R.id.txtPrecioDescuentoTachado);
        TextView txtDescuento = (TextView) newView.findViewById(R.id.txtDescuento);

        if (producto.getDescuentos() == null) {
            precioProducto.setText("$" + producto.getPrice());

        } /* else {

            if (producto.getDescuentos().size() > 0) {
                boolean encontro = false;
                Calendar fechaInicio = Calendar.getInstance();
                Calendar fechaFin = Calendar.getInstance();

                //for (int i = 0; i < pro.getDescuentos().size() && !encontro; i++) {         // Sospecho que agarra el 1er descuentoo!!
                Descuento desc = producto.getDescuentos().get(0); // .get(i);

                boolean descuentoVigente = false;
                if (!desc.getFrom().equals("0000-00-00 00:00:00")) {

                    try {
                        fechaFin.setTime(conversor.dateFormat.parse(desc.getTo()));
                        fechaInicio.setTime(conversor.dateFormat.parse(desc.getFrom()));
                    } catch (ParseException e) {}

                    if (fechaActual.getTime().before(fechaFin.getTime())
                            && fechaInicio.getTime().before(fechaActual.getTime())) {
                        descuentoVigente = true;
                    }
                } else {
                    descuentoVigente = true;
                }

                if (descuentoVigente && Integer.valueOf(desc.getFrom_quantity()) == 1) {
                    double descuentoS = Double.valueOf(desc.getReduction());
                    double descuento = descuentoS * 100;
                    txtDescuento.setText(String.format("%.0f",descuento) + "%");

                    txtPrecioDescuentoTachado.setText("$" + producto.getPrice());
                    txtPrecioDescuentoTachado.setPaintFlags(txtPrecioDescuentoTachado.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    double precio = Double.valueOf(producto.getPrice()) - (Double.valueOf(producto.getPrice()) * descuentoS);
                    txtPrecioDescuento.setText("$" + String.format("%.2f", precio));
                    encontro = true;
                }
                //}

                if (!encontro) {
                    precioProducto.setText("$" + producto.getPrice());
                } else {
                    contenedorDescuento.setVisibility(View.VISIBLE);
                    precioProducto.setVisibility(View.GONE);
                }
            }
        }
*/
        nombreProducto.setText(producto.getName());
        condicionProducto.setText(producto.getCondition());
        imagenProducto.setImageBitmap(conversor.StringToBitMap(producto.getImagenes().get(0)));

        imagenProducto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthProductos, widthProductos);
        imagenProducto.setLayoutParams(params);
        imagenProducto.setScaleType(ImageView.ScaleType.FIT_CENTER);

        imagenProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickImagenProducto(producto);
            }
        });

        recycleProductosSearch.addView(newView);
    }

    private void clickImagenProducto(Producto producto) {
        Intent intent = new Intent(this.getApplicationContext(), ProductoDetalle.class);
        intent.putExtra("producto", producto);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void mostrarFiltros() {
        if (contenedorFiltros.getVisibility() == View.GONE) {
            contenedorBusqueda.setVisibility(View.GONE);
            contenedorFiltros.setVisibility(View.VISIBLE);
        }
    }

    /*  Navigation
    * */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnBuscar:
                buscar();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        buscar();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnatras:
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
            case R.id.btn_ordenar_ninguno:
                ORDENAR = "";
                cambiarEstiloBotones(btn_ordenar_ninguno);
                break;
            case R.id.btn_ordenar_mayor:
                ORDENAR = "&sort=[price_DESC]";
                cambiarEstiloBotones(btn_ordenar_mayor);
                break;
            case R.id.btn_ordenar_menor:
                ORDENAR = "&sort=[price_ASC]";
                cambiarEstiloBotones(btn_ordenar_menor);
                break;
            case R.id.btn_filtro:
                mostrarFiltros();
                break;
        }
    }

    private void cambiarEstiloBotones(Button boton) {

        botonEstilo(false, btn_ordenar_ninguno);
        botonEstilo(false, btn_ordenar_mayor);
        botonEstilo(false, btn_ordenar_menor);
        botonEstilo(true, boton);

    }

    private void botonEstilo(boolean valor, Button boton) {
        boton.setTextColor(getApplication().getResources().getColor(R.color.colorNegro));
        if (valor) {
            boton.setBackgroundColor(getApplication().getResources().getColor(R.color.colorAbajo1));
        } else {
            boton.setBackgroundColor(getApplication().getResources().getColor(R.color.colorLineaDividir));
        }
    }
}
