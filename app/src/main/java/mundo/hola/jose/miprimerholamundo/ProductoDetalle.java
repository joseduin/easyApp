package mundo.hola.jose.miprimerholamundo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.Adaptador.ImageAdapter;
import mundo.hola.jose.miprimerholamundo.modelo.*;

public class ProductoDetalle extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

    private TextView textProdDetalleId, textNombre, textReferencia, textCondicioon, textDescripcion, textPrecio, textProdDisponibles, txtDescuentoDetalle, txtPrecioTachadoDescuento;
    private Button buttonCarritoProdDetalle, buttonMasCantidad, ButtonMenosCantidad;
    private NumberPicker numberPicker;
    private LinearLayout productoDetalle1, productoDetalle2, contenedorDescuentoDetalle;
    private ImageSwitcher mswitcher;
    private Gallery gallery;
    private GridLayout contenedorFeatures;

    private Conversor conversor = new Conversor();
    private SessionManager session;
    private AsyncHttpClient client = new AsyncHttpClient();
    private ArrayList<Bitmap> bitmap = new ArrayList<>();
    //private Calendar fechaActual = Calendar.getInstance();
    private String ID_USUARIO;
    private int STOCK;
    private double PRECIO_NETO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        conversor.setContext(this.getApplicationContext());

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            ID_USUARIO = session.getCustomerCurrent().getId();
        } else {
            ID_USUARIO = "";
        }

        textProdDetalleId = (TextView) findViewById(R.id.textProdDetalleId);
        textNombre = (TextView) findViewById(R.id.textNombre);
        textReferencia = (TextView) findViewById(R.id.textReferencia);
        textCondicioon = (TextView) findViewById(R.id.textCondicioon);
        textDescripcion = (TextView) findViewById(R.id.textDescripcion);
        textPrecio = (TextView) findViewById(R.id.textPrecio);
        textProdDisponibles = (TextView) findViewById(R.id.textProdDisponibles);
        buttonCarritoProdDetalle = (Button) findViewById(R.id.buttonCarritoProdDetalle);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        buttonMasCantidad = (Button) findViewById(R.id.buttonMasCantidad);
        ButtonMenosCantidad = (Button) findViewById(R.id.ButtonMenosCantidad);
        mswitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        gallery = (Gallery) findViewById(R.id.gallery);
        productoDetalle1 = (LinearLayout) findViewById(R.id.productoDetalle1);
        productoDetalle2 = (LinearLayout) findViewById(R.id.productoDetalle2);
        contenedorDescuentoDetalle = (LinearLayout) findViewById(R.id.contenedorDescuentoDetalle);
        txtDescuentoDetalle = (TextView) findViewById(R.id.txtDescuentoDetalle);
        txtPrecioTachadoDescuento = (TextView) findViewById(R.id.txtPrecioTachadoDescuento);
        contenedorFeatures = (GridLayout) findViewById(R.id.contenedorFeatures);

        // Configuramos los componentes
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        productoDetalle1.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels/ 2, LayoutParams.WRAP_CONTENT));
        productoDetalle2.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels/ 2, LayoutParams.WRAP_CONTENT));
        mswitcher.setFactory(this);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);

        // Animaciones a la galeria
        mswitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        mswitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        Producto producto = new Producto();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            producto = (Producto) extras.getSerializable("producto");

            getSupportActionBar().setTitle(producto.getName());

            textProdDetalleId.setText(producto.getId());
            textCondicioon.setText(producto.getCondition());
            textNombre.setText(producto.getName());
            textReferencia.setText(producto.getReference());
            textDescripcion.setText(Html.fromHtml(producto.getDescription_short()));
            bitmap.add(conversor.StringToBitMap(producto.getImagenes().get(0)));
        }

        //if (producto.getDescuentos() == null) {
            textPrecio.setText("$" + producto.getPrice() + " impuesto inc.");
            PRECIO_NETO = Double.valueOf(producto.getPrice().replace(",", "."));

        //} else {
        //    buscarHoraEcuador(producto);
        //}

        // Buscamos las demas imagenes
        if (producto.getImagenes().size() > 1) {
            buscarImagenes(1, producto.getImagenes());
        }

        // Buscamos la cantidad que hay en el stock
        buscarStock(producto.getStock_availables().get(0));

        if (producto.getFeatures().size() > 0) {
            buscarFeatures(producto.getFeatures(), 0);
        } else {
            contenedorFeatures.setVisibility(View.GONE);
        }


        // OnClick
        buttonCarritoProdDetalle.setOnClickListener(this);
        ButtonMenosCantidad.setOnClickListener(this);
        buttonMasCantidad.setOnClickListener(this);
    }

    /*
        private void buscarHoraEcuador(final Producto producto) {

            client.get(conversor.WEB_API_AUX + "hora.php", null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String res) {
                    try {
                        fechaActual.setTime(conversor.dateFormat.parse(res));
                        descuento(producto);
                    } catch (ParseException e) {}
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    conversor.errorLoading();
                }
            });
        }

        private void descuento(Producto producto) {
            if (producto.getDescuentos().size() > 0) {
                boolean encontro = false;
                Calendar fechaInicio = Calendar.getInstance();
                Calendar fechaFin = Calendar.getInstance();

                Descuento desc = producto.getDescuentos().get(0);

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

                // for (int i = 0; i < producto.getDescuentos().size() && !encontro; i++) {
                // Descuento desc = producto.getDescuentos().get(0);

                if (descuentoVigente && Integer.valueOf(desc.getFrom_quantity()) == 1) {
                    double descuentoS = Double.valueOf(desc.getReduction());
                    double descuento = descuentoS * 100;
                    txtDescuentoDetalle.setText(String.format("%.0f",descuento) + "%");

                    txtPrecioTachadoDescuento.setText("$" + producto.getPrice());
                    txtPrecioTachadoDescuento.setPaintFlags(txtPrecioTachadoDescuento.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    PRECIO_NETO = Double.valueOf(producto.getPrice()) - (Double.valueOf(producto.getPrice()) * descuentoS);
                    textPrecio.setText("$" + String.format("%.2f", PRECIO_NETO) + " impuesto inc.");
                    encontro = true;
                }
                // }

                if (!encontro) {
                    PRECIO_NETO = Double.valueOf(producto.getPrice());
                    textPrecio.setText("$" + producto.getPrice() + " impuesto inc.");
                } else  {
                    contenedorDescuentoDetalle.setVisibility(View.VISIBLE);
                }
            }
        }
    */

    private void buscarFeatures(final ArrayList<ProductFeatures> features, final int hijo) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/product_feature_values/" + features.get(hijo).getId() + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ProductFeatures feature = new ProductFeatures();
                feature.setId(conversor.buscarFeatures(res));
                buscarFeaturesMedidas(features, hijo, feature);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarFeaturesMedidas(final ArrayList<ProductFeatures> features, final int hijo, final ProductFeatures feature) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/product_features/" + features.get(hijo).getId_feature_value() + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                feature.setId_feature_value(conversor.buscarFeaturesMedidas(res));
                seguirBuscandoFeature(features, hijo, feature);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoFeature(ArrayList<ProductFeatures> features, int hijo, ProductFeatures feature) {
        imprimirFeature(feature);

        hijo++;
        if (hijo < features.size()) {
            buscarFeatures(features, hijo);
        }
    }

    private void imprimirFeature(ProductFeatures feature) {
        TextView nombre = new TextView(this);
        nombre.setText(feature.getId_feature_value() + ":");
        styloFeature(nombre);

        TextView valor = new TextView(this);
        valor.setText(feature.getId());
        valor.setPadding(40, 0, 0, 0);
        styloFeature(valor);

        contenedorFeatures.addView(nombre);
        contenedorFeatures.addView(valor);
    }

    private void styloFeature(TextView txt) {
        txt.setTextSize(18);
        txt.setTextColor(Color.BLACK);
        txt.setBackgroundColor(Color.TRANSPARENT);
    }

    private void buscarStock(String stock) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/stock_availables/" + stock + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String cantidad = conversor.buscarStock(res);
                if (!cantidad.contains("No")) {
                    STOCK = Integer.valueOf(cantidad);
                    numberPicker.setMaxValue(STOCK);
                    cantidad = (cantidad.equals("1"))? "1 artículo" : cantidad + " artículos";
                } else {
                    buttonCarritoProdDetalle.setVisibility(View.GONE);
                    numberPicker.setMinValue(0);
                    numberPicker.setEnabled(false);
                }
                textProdDisponibles.setText(cantidad);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    /*"cart_default", "home_default", "large_default", "medium_default", "small_default", "thickbox_default"
    * * * * * * * * * * * * */
    private void buscarImagenes(final int imagen, final ArrayList<String> imagenes) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/images/products/" + textProdDetalleId.getText().toString() + "/" + imagenes.get(imagen) + "/large_default?", parametros, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                bitmap.add(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                seguirBuscandoImagen(imagen, imagenes);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoImagen(int hijo, ArrayList<String> imagenes) {
        hijo++;
        if (hijo < imagenes.size()) {     // ¿Hay mas imagenes?
            buscarImagenes(hijo, imagenes);
        } else {
            imprimriImagenes();
        }
    }

    private void imprimriImagenes() {
        gallery.setAdapter(new ImageAdapter(this, bitmap));

        // On ItemSelected
       gallery.setOnItemSelectedListener(ProductoDetalle.this);
    }

    private void buscarUltimoCarrito() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carts?filter[id_customer]=" + ID_USUARIO + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String existe = conversor.existeCarrito(res);
                if (existe.equals("no")) {
                    buscarEnvios();     // Usuario nuevo
                } else {
                    validarQueCarritoNoSeaUnaOrden(existe);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void validarQueCarritoNoSeaUnaOrden(final String id) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/orders?filter[id_cart]=" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String validar = conversor.validarQueCarritoNoSeaUnaOrden(res);
                if (validar.equals("no")) { // El carrito actual no es una orden
                    buscarCarrito(id);
                } else {
                    buscarEnvios();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });

    }

    private void buscarEnvios() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carriers?filter[deleted]=0&filter[active]=1&filter[is_free]=0", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ArrayList<String> envio_id = conversor.buscarEnvios(res);
                crearCarrito(envio_id.get(0));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void crearCarrito(String idEnvio) {
        RequestParams parametros = new RequestParams();

        int cantidad = numberPicker.getValue();
        String idProducto = textProdDetalleId.getText().toString();
        continuarCompra(cantidad);

        client.post(conversor.WEB_API_AUX + "CCart.php?Create&delivery=" + idEnvio + "&customer=" + ID_USUARIO + "&product=" + idProducto + "&quantity=" + cantidad, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {}

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarCarrito(final String id) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carts/" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito = conversor.buscarCarrito(res);
                String idProducto = textProdDetalleId.getText().toString();
                int cantidadViaja = 0;
                int i = 0;
                boolean encontro = false;

                if (carrito.getCarritoDetalles() != null) {
                    for (i = 0; i < carrito.getCarritoDetalles().size(); i++) {

                        CarritoDetalle producto = carrito.getCarritoDetalles().get(i);
                        if (idProducto.equals(producto.getId_product())) {
                            cantidadViaja = Integer.valueOf(producto.getQuantity());
                            encontro = true;
                            break;
                        }
                    }
                }


                int cantidad = numberPicker.getValue();
                if (encontro) {
                    continuarCompra(cantidadViaja + cantidad);
                    actualizarCantidadProducto(id, i, cantidad, cantidadViaja);
                } else {
                    continuarCompra(cantidad);
                    incluirProductoAlCarrito(id, idProducto, cantidad);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void actualizarCantidadProducto(String id, int row, int cantidad, int cantidadViaja) {
        final int cantidadTotal = cantidadViaja + cantidad;
        if (cantidadTotal > STOCK) {
            conversor.mensajeLargo("No hay esa cantidad de productos en existencia");
            return;
        }

        client.get(conversor.WEB_API_AUX + "UCartItemQuantity.php?id=" + id + "&row=" + row + "&qua=" + cantidadTotal, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                numberPicker.setMaxValue(STOCK - cantidadTotal);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void incluirProductoAlCarrito(String idCarrito, String idProducto, int cantidad) {

        client.get(conversor.WEB_API_AUX + "AddItemToCart.php?idCart=" + idCarrito + "&addItem=true&idPro=" + idProducto + "&proQua=" + cantidad, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {}

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void continuarCompra(int cantidad) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        double precioTotal = cantidad * PRECIO_NETO;

        builder.setTitle("Producto añadido correctamente a su carrito de la compra");

        LinearLayout mensaje = new LinearLayout(this.getApplicationContext());
        mensaje.setOrientation(LinearLayout.VERTICAL);

        TextView nombre = new TextView(this.getApplicationContext());
        nombre.setText(textNombre.getText().toString());
        mensaje.addView(nombre);

        TextView cant = new TextView(this.getApplicationContext());
        cant.setText("Cantidad: " + cantidad);
        mensaje.addView(cant);

        TextView total = new TextView(this.getApplicationContext());
        total.setText("Total: $" +  String.format("%.2f", precioTotal));

        mensaje.addView(total);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            nombre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            total.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cant.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        LinearLayout linear = new LinearLayout(this.getApplicationContext());
        linear.setOrientation(LinearLayout.VERTICAL);

        ImageView img = new ImageView(this.getApplicationContext());
        img.setImageBitmap(bitmap.get(0));

        linear.addView(img);
        linear.addView(mensaje);

        builder.setView(linear);

        builder.setPositiveButton("Ir a caja", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                existeCarrito();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Continuar con la compra", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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

    // On Click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonMenosCantidad:
                pickerCantidad(false);
                break;
            case R.id.buttonMasCantidad:
                pickerCantidad(true);
                break;
            case R.id.buttonCarritoProdDetalle:
                if (ID_USUARIO.equals("")) {
                    irA(Login.class);
                    conversor.mensajeLargo("Para continuar, por favor iniciar sesión");
                } else {
                    buscarUltimoCarrito();
                }
                break;
        }
    }

    private void irA(Class<?> clas) {
        Intent intent = new Intent(this.getApplicationContext(), clas);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /*  False -> menos
        True ->  mas
    * */
    private void pickerCantidad(boolean b) {
        int valor = numberPicker.getValue();
        if (valor == 0 && !b) {
            return;
        } else {
            valor = (b == false) ? valor - 1 : valor + 1;
            numberPicker.setValue(valor);
        }
    }

    /* Controla las imagenes que se muestran
    * * * * * * */
    // ViewFactory
    @Override

    public View makeView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ImageView i = new ImageView(this);
        i.setBackgroundColor(0x00000000);   // color de fondo TRANSPARENTE
        i.setScaleType(ImageView.ScaleType.FIT_CENTER); // Tipo de escala
        i.setLayoutParams(new ImageSwitcher.LayoutParams(displayMetrics.widthPixels, displayMetrics.widthPixels));
        return i;
    }

    // OnItemSelected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Drawable drawable =new BitmapDrawable(bitmap.get(position));
        mswitcher.setImageDrawable(drawable);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
