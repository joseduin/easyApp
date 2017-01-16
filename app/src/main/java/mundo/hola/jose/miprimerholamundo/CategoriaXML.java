package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.Adaptador.InteractiveScrollView;
import mundo.hola.jose.miprimerholamundo.modelo.Categoria;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Customer;
import mundo.hola.jose.miprimerholamundo.modelo.Descuento;
import mundo.hola.jose.miprimerholamundo.modelo.Producto;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class CategoriaXML extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ViewGroup recyclerCategoria;
    private GridLayout recyclerProducto;
    private TextView textsubcategoria, textExistencia, txtSinProductos, txtnombreCategoriaPadre;
    private LinearLayout imageCategoriaPadre, contenedorProductoCategoria;
    private InteractiveScrollView scrollView;
    private ProgressBar loadingProducts;
    private ImageButton flechita_der, flechita_izq;
    private HorizontalScrollView contenedorCategorias;

    private AsyncHttpClient client = new AsyncHttpClient();
    private Conversor conversor = new Conversor();
    private SessionManager session;

    private static int cantidadDeProductos = 2;     // Cantidad de productos por carga!!
    private int contarProductosImpresos = 1;
    private boolean loadingproductos = false;
    private int hijoProducto = 0;
    private ArrayList<String> productos = new ArrayList<>();
    private int contWidthCategorias = 0;

    private Calendar fechaActual = Calendar.getInstance();
    private String ID_USUARIO;
    private List<Categoria> categoriaLista = new ArrayList<>();
    private static int columna = 4;
    private static int imageWigth;
    private static int imageHeight;
    private static int pantallaWidth;
    private static int widthProductos;
    private String padre_titulo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_xml);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            Customer customer = session.getCustomerCurrent();
            ID_USUARIO = customer.getId();
        } else {
            ID_USUARIO = "";
        }

        textsubcategoria = (TextView) findViewById(R.id.textsubcategoria);
        textExistencia = (TextView) findViewById(R.id.textExistencia);
        imageCategoriaPadre = (LinearLayout) findViewById(R.id.imageCategoriaPadre);
        recyclerCategoria = (ViewGroup) findViewById(R.id.recycleCategorias);
        txtnombreCategoriaPadre = (TextView) findViewById(R.id.txtnombreCategoriaPadre);
        scrollView = (InteractiveScrollView) findViewById(R.id.scrollView);
        loadingProducts = (ProgressBar) findViewById(R.id.loadingProducts);
        flechita_der = (ImageButton) findViewById(R.id.flechita_der);
        flechita_izq = (ImageButton) findViewById(R.id.flechita_izq);
        contenedorCategorias = (HorizontalScrollView) findViewById(R.id.contenedorCategorias);
        contenedorProductoCategoria = (LinearLayout) findViewById(R.id.contenedorProductoCategoria);
        txtSinProductos = (TextView) findViewById(R.id.txtSinProductos);

        // Conf
        textExistencia.setVisibility(View.INVISIBLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        pantallaWidth = displayMetrics.widthPixels;
        imageWigth = pantallaWidth / columna;
        imageHeight = imageWigth;       // Las imagenes de las categorias son cuadradas -> 125 x 125
        textsubcategoria.setVisibility(View.GONE);
        flechita_izq.setVisibility(View.INVISIBLE);
        flechita_der.setVisibility(View.INVISIBLE);

        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation == 0 || rotation == 180) {
            widthProductos = pantallaWidth;
        } else {
            widthProductos = (int) (pantallaWidth * 0.45);
        }

        Categoria categoriaPadre = new Categoria();

        Bundle extras = getIntent().getExtras();
        int titulo = 0;
        if (extras != null) {
            titulo = extras.getInt("titulo");
            categoriaPadre = (Categoria) extras.getSerializable("categoria");

            padre_titulo = categoriaPadre.getName();
            getSupportActionBar().setTitle(extras.getString("padre") + categoriaPadre.getName());
            txtnombreCategoriaPadre.setText(categoriaPadre.getName());
        }

        buscarImagen(categoriaPadre.getId(), "categories", 0, null, categoriaPadre, null, "/category_default", true);

        // Productos
        recyclerProducto = (GridLayout) findViewById(R.id.recycleProductos);

        // Busquedas
        if (categoriaPadre.getSubCategorias().size() > 0) {
            textsubcategoria.setVisibility(View.VISIBLE);
            textsubcategoria.setText(titulo == 0 ? "Categorías" : categoriaPadre.getSubCategorias().size() == 1 ? "Subcategoría" : "Subcategorías");
            recyclerCategoria.removeAllViews();
            buscarCategorias(1, categoriaPadre.getSubCategorias());
        }

        productos = categoriaPadre.getProductos();
        if (categoriaPadre.getProductos().size() > 0) {
            textExistencia.setVisibility(View.VISIBLE);
            textExistencia.setText(categoriaPadre.getProductos().size() == 1 ? "Hay 1 producto." : "Hay " + categoriaPadre.getProductos().size() + " productos");
            recyclerProducto.removeAllViews();
            contarProductosImpresos = 1;
            buscarProductos();
        } else {
            contenedorProductoCategoria.setVisibility(View.GONE);
            txtSinProductos.setVisibility(View.VISIBLE);
        }

        flechita_der.setOnClickListener(this);
        flechita_izq.setOnClickListener(this);

        scrollView.setOnBottomReachedListener(new InteractiveScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                if ((loadingproductos) && (hijoProducto < productos.size())) {
                    loadingproductos = false;
                    buscarProductos();
                    contarProductosImpresos = 1;
                    loadingProducts.setVisibility(View.VISIBLE);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contenedorCategorias.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollX > 300) {
                        flechita_izq.setVisibility(View.VISIBLE);
                    } else {
                        flechita_izq.setVisibility(View.INVISIBLE);
                    }
                    if (scrollX > (v.getWidth() - 300)) {
                        flechita_der.setVisibility(View.INVISIBLE);
                    } else {
                        flechita_der.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            flechita_izq.setVisibility(View.GONE);
            flechita_der.setVisibility(View.GONE);
        }
    }

    /*  Metodos de super-recursividad Categorias D:
    * */
    private void buscarCategorias(final int hijo, final ArrayList<String> categorias) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/categories/" + categorias.get(hijo - 1) + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Categoria categoria = conversor.buscarCategoria(res);
                buscarImagen(categoria.getId(), "categories", hijo, categorias, categoria, null, "/medium_default", false);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarImagen(String id, final String tipo, final int hijo, final ArrayList<String> lista, final Categoria categoria, final Producto producto, String imagenSize, final boolean imgPadre) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/images/" + tipo + "/" + id + imagenSize + "?", parametros, new BinaryHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (imgPadre == true) {
                    //imageCategoriaPadre.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    imageCategoriaPadre.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pantallaWidth, (int) (pantallaWidth * 0.25));
                    imageCategoriaPadre.setLayoutParams(params);

                } else {
                    if (tipo.equals("categories")) {
                        categoria.setImagen(conversor.BitMapToString(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                        categoriaLista.add(categoria);
                        seguirBuscandoCategoria(hijo, lista, categoria);
                    } else {
                        ArrayList<String> imagenes = producto.getImagenes();
                        imagenes.set(0 ,conversor.BitMapToString(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                        producto.setImagenes(imagenes);
                        //buscarSiTieneDescuento(producto);
                        seguirBuscandoProducto(producto);
                    }
                }
            }
            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoCategoria(int hijo, ArrayList<String> categorias, Categoria categoria) {
        imprimirSubcategorias(categoria, hijo - 1);

        if (hijo < categorias.size()) {     // ¿Hay mas categorias?
            hijo++;
            buscarCategorias(hijo, categorias);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contWidthCategorias +=  imageWigth;
            if (pantallaWidth < contWidthCategorias) {
                flechita_der.setVisibility(View.VISIBLE);
            }
        }
    }

    private void imprimirSubcategorias(final Categoria categoria, int hijo) {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.categoria_item, recyclerCategoria, false);

        TextView nombreCategoria = (TextView) newView.findViewById(R.id.nombreCategoria);
        ImageView imagenCategoria = (ImageView) newView.findViewById(R.id.imagenCategoria);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWigth, imageHeight);
        imagenCategoria.setLayoutParams(params);
        nombreCategoria.setWidth(imageWigth);

        nombreCategoria.setText(categoria.getName());
        imagenCategoria.setImageBitmap(conversor.StringToBitMap(categoria.getImagen()));

        imagenCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCategoria(categoria);
            }
        });

        recyclerCategoria.addView(newView, hijo);
    }

    private void irCategoria(Categoria categoria) {
        Intent intent = new Intent(this.getApplicationContext(), CategoriaXML.class);
        intent.putExtra("categoria", categoria);
        intent.putExtra("titulo", 1);
        intent.putExtra("padre", padre_titulo + " - ");

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /*  Metodos de super-recursividad Productos D:
   * */
    /*
    private void buscarSiTieneDescuento(final Producto producto) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/specific_prices?filter[id_product]=" + producto.getId(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ArrayList<String> descuento = conversor.buscarDescuentos(res);
                if (descuento.size() > 0) {
                    buscarDescuento(new ArrayList<Descuento>(), producto, descuento, 0);
                } else {
                    seguirBuscandoProducto(producto);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarDescuento(final ArrayList<Descuento> desc, final Producto producto, final ArrayList<String> descuentos_ids, final int hijo) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/specific_prices/" + descuentos_ids.get(hijo), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Descuento descuento = conversor.buscarDescuento(res);
                desc.add(descuento);
                seguirBuscandoDescuento(desc, producto, descuentos_ids, hijo);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoDescuento(final ArrayList<Descuento> desc, final Producto producto, final ArrayList<String> descuentos_ids, int hijo) {
        hijo++;
        if (hijo < descuentos_ids.size()) {     // ¿Hay mas descuentos?
            buscarDescuento(desc, producto, descuentos_ids, hijo);
        } else {
            producto.setDescuentos(desc);
            seguirBuscandoProducto(producto);
        }
    }
*/
    private void buscarProductos() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/products/" + productos.get(hijoProducto) + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Producto producto = conversor.buscarProducto(res);
                buscarImagen(producto.getId() + "/" + producto.getImagenes().get(0) , "products", hijoProducto, productos, null, producto, "/large_default", false);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoProducto(Producto producto) {
        if (loadingProducts.getVisibility() == View.VISIBLE) {
            loadingProducts.setVisibility(View.GONE);
        }
        //buscarHoraEcuador(producto);
        validarImprimirProductos(producto);
    }
/*
    private void buscarHoraEcuador(final Producto producto) {

        client.get(conversor.WEB_API_AUX + "hora.php", null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                try {
                    fechaActual.setTime(conversor.dateFormat.parse(res));
                    validarImprimirProductos(producto);
                } catch (ParseException e) {}
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }
*/
    private void validarImprimirProductos(Producto producto) {
        imprimirProductos(producto);

        hijoProducto++;
        if (contarProductosImpresos != cantidadDeProductos) {

            contarProductosImpresos++;
            if (hijoProducto < productos.size()) {     // ¿Hay mas productos?
                buscarProductos();
            }
        } else {
            loadingproductos = true;
        }
    }

    private void imprimirProductos(final Producto pro) {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.producto_item, recyclerProducto, false);

        TextView nombreProducto = (TextView) newView.findViewById(R.id.nombreProducto);
        TextView condicionProducto = (TextView) newView.findViewById(R.id.condicionProducto);
        ImageView imagenProducto = (ImageView) newView.findViewById(R.id.imagenProducto);

        TextView precioProducto = (TextView) newView.findViewById(R.id.precioProducto);

     /*   LinearLayout contenedorDescuento = (LinearLayout)  newView.findViewById(R.id.contenedorDescuento);
        TextView txtPrecioDescuento = (TextView) newView.findViewById(R.id.txtPrecioDescuento);
        TextView txtPrecioDescuentoTachado = (TextView) newView.findViewById(R.id.txtPrecioDescuentoTachado);
        TextView txtDescuento = (TextView) newView.findViewById(R.id.txtDescuento);
    */
        if (pro.getDescuentos() == null) {
            precioProducto.setText("$" + pro.getPrice());

        } /*else {

            if (pro.getDescuentos().size() > 0) {
                boolean encontro = false;
                Calendar fechaInicio = Calendar.getInstance();
                Calendar fechaFin = Calendar.getInstance();

                //for (int i = 0; i < pro.getDescuentos().size() && !encontro; i++) {         // Sospecho que agarra el 1er descuentoo!!
                    Descuento desc = pro.getDescuentos().get(0); // .get(i);

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

                        txtPrecioDescuentoTachado.setText("$" + pro.getPrice());
                        txtPrecioDescuentoTachado.setPaintFlags(txtPrecioDescuentoTachado.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        double precio = Double.valueOf(pro.getPrice()) - (Double.valueOf(pro.getPrice()) * descuentoS);
                        txtPrecioDescuento.setText("$" + String.format("%.2f", precio));
                        encontro = true;
                    }
                //}

                if (!encontro) {
                    precioProducto.setText("$" + pro.getPrice());
                } else {
                    contenedorDescuento.setVisibility(View.VISIBLE);
                    precioProducto.setVisibility(View.GONE);
                }
            }
        }
*/
        nombreProducto.setText(pro.getName());

        condicionProducto.setText(pro.getCondition());
        imagenProducto.setImageBitmap(conversor.StringToBitMap(pro.getImagenes().get(0)));

        imagenProducto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthProductos, widthProductos);
        imagenProducto.setLayoutParams(params);
        imagenProducto.setScaleType(ImageView.ScaleType.FIT_CENTER);

        imagenProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAProductoDetalle(pro);
            }
        });

        recyclerProducto.addView(newView);
    }

    private void irAProductoDetalle(Producto pro) {
        Intent intent = new Intent(this.getApplicationContext(), ProductoDetalle.class);
        intent.putExtra("producto", pro);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void existeCarrito() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carts?filter[id_customer]=" + ID_USUARIO + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                irCarrito(conversor.existeCarrito(res));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void irCarrito(String id) {
        Intent intent = new Intent(this, Carrito.class);
        intent.putExtra("id", id);

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
            case R.id.inicioOtros:
                Intent i = new Intent(this, Inicio.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            case R.id.carritoOtro:
                existeCarrito();
                return true;
            case R.id.btnBuscarOtros:
                Intent in = new Intent(this, Busqueda.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.otros, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (ID_USUARIO.equals("")) {
            menu.findItem(R.id.carritoOtro).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flechita_der:
                contenedorCategorias.fullScroll(contenedorCategorias.FOCUS_RIGHT);
                break;
            case R.id.flechita_izq:
                contenedorCategorias.fullScroll(contenedorCategorias.FOCUS_LEFT);
                break;
        }
    }
}
