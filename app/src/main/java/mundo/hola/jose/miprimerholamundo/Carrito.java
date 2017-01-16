package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.*;
import mundo.hola.jose.miprimerholamundo.modelo.Direccion;

public class Carrito extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // confirmarEliminarProductoCarrito

    private TextView existeCarrito, txtSubTotaProducto, txtPrecioEnvio, txtTOTAL, txtTituloCarrito, txtResumenSub, txtResumenCond1, txtResumenCond2, txtResumenCond3, txtResumenCond4;
    private ViewGroup recycleCarrito, contenedorEnvioItem, contenedorPagoItem;
    private LinearLayout contenedorCarrito, contenedorDirecconesCarrito, contenedorEnvio, contenedorTotalPrecios, contenedorPago, contenedorResumen;
    private Button btnCondicionesDelServicio;
    private Spinner spinerDirecciones, spinerFacturacion;
    private CheckBox checkDireccionFac, checkTerminosDelServicio;
    private ActionProcessButton btnSiguienteCarrito, btnSiguienteCarrito2, btnSiguienteCarrito3, btnConfirmarPedido;
    private MenuItem direcioNueva;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();
    private SessionManager session;
    private String USUARIO_ID;
    private int DIR_ACT = 0;
    private int DIR_FAC = 0;
    private String MODULE;
    private String PAYMENT;

    private List<Producto> productos = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<Direccion> direcciones = new ArrayList<>();
    private ArrayList<String> envio_id = new ArrayList<>();
    //private Calendar fechaActual = Calendar.getInstance();
    private  ArrayList<String> alias = new ArrayList<>();
    private mundo.hola.jose.miprimerholamundo.modelo.Carrito CARRITO_ACTUAL = new mundo.hola.jose.miprimerholamundo.modelo.Carrito();

    // Delivery
    private int EnvioActivo = 0;
    // private int shipping_handling = 2;      // Buscar este valor e.e
    private Carriers primerEnvio;


    private static int columna = 4;
    private static int imageWigth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Carrito");
        getSupportActionBar().setSubtitle("Paso 1 / 4");

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());
        Customer customer = session.getCustomerCurrent();
        USUARIO_ID = customer.getId();

        // Enlazamos controlador con vista
        txtTituloCarrito = (TextView) findViewById(R.id.txtTituloCarrito);
        existeCarrito = (TextView) findViewById(R.id.existeCarrito);
        txtSubTotaProducto = (TextView) findViewById(R.id.txtSubTotaProducto);
        txtPrecioEnvio = (TextView) findViewById(R.id.txtPrecioEnvio);
        txtTOTAL = (TextView) findViewById(R.id.txtTOTAL);
        recycleCarrito = (ViewGroup) findViewById(R.id.recycleCarrito);
        contenedorCarrito = (LinearLayout) findViewById(R.id.contenedorCarrito);
        contenedorDirecconesCarrito = (LinearLayout) findViewById(R.id.contenedorDirecconesCarrito);
        btnSiguienteCarrito = (ActionProcessButton) findViewById(R.id.btnSiguienteCarrito);
        btnSiguienteCarrito2 = (ActionProcessButton) findViewById(R.id.btnSiguienteCarrito2);
        checkDireccionFac = (CheckBox) findViewById(R.id.checkDireccionFac);
        spinerDirecciones = (Spinner) findViewById(R.id.spinerDirecciones);
        spinerFacturacion = (Spinner) findViewById(R.id.spinerFacturacion);
        contenedorEnvio = (LinearLayout) findViewById(R.id.contenedorEnvio);
        contenedorEnvioItem = (ViewGroup) findViewById(R.id.contenedorEnvioItem);
        contenedorTotalPrecios = (LinearLayout) findViewById(R.id.contenedorTotalPrecios);
        btnCondicionesDelServicio = (Button) findViewById(R.id.btnCondicionesDelServicio);
        checkTerminosDelServicio = (CheckBox) findViewById(R.id.checkTerminosDelServicio);
        btnSiguienteCarrito3 = (ActionProcessButton) findViewById(R.id.btnSiguienteCarrito3);
        contenedorPago = (LinearLayout) findViewById(R.id.contenedorPago);
        contenedorPagoItem = (ViewGroup) findViewById(R.id.contenedorPagoItem);
        contenedorResumen = (LinearLayout) findViewById(R.id.contenedorResumen);
        txtResumenSub = (TextView) findViewById(R.id.txtResumenSub);
        txtResumenCond1 = (TextView) findViewById(R.id.txtResumenCond1);
        txtResumenCond2 = (TextView) findViewById(R.id.txtResumenCond2);
        txtResumenCond3 = (TextView) findViewById(R.id.txtResumenCond3);
        txtResumenCond4 = (TextView) findViewById(R.id.txtResumenCond4);
        btnConfirmarPedido = (ActionProcessButton) findViewById(R.id.btnConfirmarPedido);

        // Conf
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        imageWigth = displayMetrics.widthPixels / columna;

        txtSubTotaProducto.setWidth(imageWigth);
        txtPrecioEnvio.setWidth(imageWigth);
        txtTOTAL.setWidth(imageWigth);

        // Buscamos si ahi algo en el carrito por 2 condiciones: aqui y en examinarCarrito()
        String existencia = (String) getIntent().getSerializableExtra("id");

        if (existencia.equals("no")) {
            contenedorCarrito.setVisibility(View.GONE);
            existeCarrito.setVisibility(View.VISIBLE);
            existeCarrito.setText("Su carrito está vacío.");
        } else {
            buscarCarrito(existencia);
        }

        btnSiguienteCarrito.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSiguienteCarrito2.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSiguienteCarrito3.setMode(ActionProcessButton.Mode.ENDLESS);
        btnConfirmarPedido.setMode(ActionProcessButton.Mode.ENDLESS);
        conversor.ActionProcessButtonValidation(btnSiguienteCarrito, false);
        botonesLoading();

        btnCondicionesDelServicio.setOnClickListener(this);
        btnSiguienteCarrito.setOnClickListener(this);
        btnSiguienteCarrito2.setOnClickListener(this);
        btnSiguienteCarrito3.setOnClickListener(this);
        btnConfirmarPedido.setOnClickListener(this);
        checkDireccionFac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    spinerFacturacion.setVisibility(View.VISIBLE);
                } else {
                    spinerFacturacion.setVisibility(View.GONE);
                }
            }
        });
        spinerDirecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DIR_ACT = Integer.valueOf(direcciones.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinerFacturacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DIR_FAC = Integer.valueOf(direcciones.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buscarCarrito(String existencia) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carts/" + existencia + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito = conversor.buscarCarrito(res);
                validarQueCarritoNoSeaUnaOrden(carrito);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void validarQueCarritoNoSeaUnaOrden(final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/orders?filter[id_cart]=" + carrito.getId(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String validar = conversor.validarQueCarritoNoSeaUnaOrden(res);
                if (validar.equals("no")) { // El carrito actual no es una orden
                    examinarCarrito(carrito);
                } else {
                    contenedorCarrito.setVisibility(View.GONE);
                    existeCarrito.setVisibility(View.VISIBLE);
                    existeCarrito.setText("Su carrito está vacío.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });

    }

    private void examinarCarrito(mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito) {
        if (carrito.getCarritoDetalles() == null) {
            contenedorCarrito.setVisibility(View.GONE);
            existeCarrito.setVisibility(View.VISIBLE);
            existeCarrito.setText("Su carrito está vacío.");
            return;
        }

        CARRITO_ACTUAL = carrito;
        contenedorTotalPrecios.setVisibility(View.VISIBLE);
        ArrayList<String> prod = new ArrayList<>();
        for (CarritoDetalle detalle : carrito.getCarritoDetalles()) {
            prod.add(detalle.getId_product());
        }

        buscarProductos(0, prod, carrito);
    }

    private void buscarProductos(final int hijo, final ArrayList<String> prod, final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/products/" + prod.get(hijo) + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Producto producto = conversor.buscarProducto(res);
                buscarImagene(producto, producto.getId() + "/" + producto.getImagenes().get(0), prod, hijo, carrito);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarImagene(final Producto producto, final String imagen, final ArrayList<String> imagenes, final int hijo, final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/images/products/" + imagen + "/small_default?", parametros, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                producto.setId_default_image(conversor.BitMapToString(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                productos.add(producto);

                //buscarSiTieneDescuento(hijo, imagenes, carrito);
                seguirBuscandoProducto(hijo, imagenes, carrito, Double.valueOf(productos.get(hijo).getPrice().replace(",", ".")));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                conversor.errorLoading();
            }
        });
    }

    /*   private void buscarSiTieneDescuento(final int hijo, final ArrayList<String> productosLsta, final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito) {
           RequestParams parametros = new RequestParams();
           conversor.parametrosBasicos(parametros);

           client.get(conversor.WEB_PAGE + "/specific_prices?filter[id_product]=" +  productos.get(hijo).getId(), parametros, new TextHttpResponseHandler() {
               @Override
               public void onSuccess(int statusCode, Header[] headers, String res) {
                   ArrayList<String> descuento = conversor.buscarDescuentos(res);
                   if (descuento.size() > 0) {
                       buscarDescuento(new ArrayList<Descuento>(), descuento, 0, productosLsta, carrito, hijo);
                   } else {
                       seguirBuscandoProducto(hijo, productosLsta, carrito, Double.valueOf(productos.get(hijo).getPrice().replace(",", ".")));
                   }
               }
               @Override
               public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                   conversor.errorLoading();
               }
           });
       }

       private void buscarDescuento(final ArrayList<Descuento> desc, final ArrayList<String> descuentos_ids, final int hijo, final ArrayList<String> productos, final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito, final int hijoProducto ) {
           RequestParams parametros = new RequestParams();
           conversor.parametrosBasicos(parametros);

           client.get(conversor.WEB_PAGE + "/specific_prices/" + descuentos_ids.get(hijo), parametros, new TextHttpResponseHandler() {
               @Override
               public void onSuccess(int statusCode, Header[] headers, String res) {
                   Descuento descuento = conversor.buscarDescuento(res);
                   desc.add(descuento);
                   seguirBuscandoDescuento(desc, descuentos_ids, hijo, productos, carrito, hijoProducto);
               }
               @Override
               public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                   conversor.errorLoading();
               }
           });
       }

       private void seguirBuscandoDescuento(ArrayList<Descuento> desc, ArrayList<String> descuentos_ids, int hijo, ArrayList<String> productoLista, mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito, int hijoProducto) {
           hijo++;
           if (hijo < descuentos_ids.size()) {     // ¿Hay mas descuentos?
               buscarDescuento(desc, descuentos_ids, hijo, productoLista, carrito, hijoProducto);
           } else {
               productos.get(hijoProducto).setDescuentos(desc);
               buscarHoraEcuador(hijoProducto, productoLista, carrito);
           }
       }

       private void buscarHoraEcuador(final int hijo, final ArrayList<String> productos, final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito) {

           client.get(conversor.WEB_API_AUX + "hora.php", null, new TextHttpResponseHandler() {
               @Override
               public void onSuccess(int statusCode, Header[] headers, String res) {
                   try {
                       fechaActual.setTime(conversor.dateFormat.parse(res));
                       double precioNeto = descuento(hijo);
                       seguirBuscandoProducto(hijo, productos, carrito, precioNeto);
                   } catch (ParseException e) {}
               }
               @Override
               public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                   conversor.errorLoading();
               }
           });
       }

       private double descuento(int hijo) {
           Producto producto = productos.get(hijo);
           if (producto.getDescuentos().size() > 0) {
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
                   double descuentoS = Double.valueOf(desc.getReduction().replace(",", "."));
                   return Double.valueOf(producto.getPrice().replace(",", ".")) - (Double.valueOf(producto.getPrice().replace(",", ".")) * descuentoS);
               }
               // }
           }
           return  Double.valueOf(producto.getPrice().replace(",", "."));
       }
   */
    private void seguirBuscandoProducto(int hijo, ArrayList<String> productos, mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito, double precioNeto) {
        imprimirProductos(carrito, hijo, precioNeto);
        hijo++;
        if (hijo < productos.size()) {     // ¿Hay mas productos?
            buscarProductos(hijo, productos, carrito);
        } else {
            buscarEnvios(0, false);
        }
    }

    private void calcularTotales() {
        double subtotal = subtotal();
        txtSubTotaProducto.setText("$" + subtotal);
        String envioString = txtPrecioEnvio.getText().toString().replace("$", "");
        double envio = Double.valueOf(envioString.replace(",", "."));
        double total = subtotal + envio;
        txtTOTAL.setText("$" + total);
    }

    private void imprimirProductos(final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito, final int position, double precioNeto) {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.carrito_item, recycleCarrito, false);

        TextView nombreProductoCarrito = (TextView) newView.findViewById(R.id.nombreProductoCarrito);
        TextView precioProductoCarrito = (TextView) newView.findViewById(R.id.precioProductoCarrito);
        final TextView precioTotalCarrito = (TextView) newView.findViewById(R.id.precioTotalCarrito);

        ImageView imageCarrito = (ImageView) newView.findViewById(R.id.imageCarrito);

        final NumberPicker numberPickerCarrito = (NumberPicker) newView.findViewById(R.id.numberPickerCarrito);

        Button ButtonMenosCantidadCarrito = (Button) newView.findViewById(R.id.ButtonMenosCantidadCarrito);
        Button buttonMasCantidadCarrito = (Button) newView.findViewById(R.id.buttonMasCantidadCarrito);

        ImageButton btnEliminarProductocarrito = (ImageButton) newView.findViewById(R.id.btnEliminarProductocarrito);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWigth, imageWigth);
        imageCarrito.setLayoutParams(params);

        int width = (int) (imageWigth * 0.8);
        nombreProductoCarrito.setWidth(imageWigth);
        precioProductoCarrito.setWidth(imageWigth);
        precioTotalCarrito.setWidth(imageWigth);

        numberPickerCarrito.setLayoutParams(new LinearLayout.LayoutParams(width, width / 2));
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(width / 2, width / 2);
        buttonMasCantidadCarrito.setLayoutParams(para);
        ButtonMenosCantidadCarrito.setLayoutParams(para);

        nombreProductoCarrito.setText(productos.get(position).getName());

        precioProductoCarrito.setText("$" + String.format("%.2f", precioNeto));
        precioTotalCarrito.setText("$" + String.format("%.2f", precioNeto * Integer.valueOf(carrito.getCarritoDetalles().get(position).getQuantity())));


        imageCarrito.setImageBitmap(conversor.StringToBitMap(productos.get(position).getId_default_image()));
        numberPickerCarrito.setMinValue(1);
        numberPickerCarrito.setWrapSelectorWheel(false);
        buscarStock(productos.get(position).getStock_availables().get(0), Integer.valueOf(carrito.getCarritoDetalles().get(position).getQuantity()), numberPickerCarrito);

        numberPickerCarrito.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                actualizarCantidadDeProductosEnCarrito(newVal, position, carrito, precioTotalCarrito, productos.get(position).getPrice());
            }
        });
        ButtonMenosCantidadCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerCantidad(false, position, numberPickerCarrito, carrito, precioTotalCarrito, productos.get(position).getPrice());
            }
        });
        buttonMasCantidadCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerCantidad(true, position, numberPickerCarrito, carrito, precioTotalCarrito, productos.get(position).getPrice());
            }
        });
        btnEliminarProductocarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEliminarProductoCarrito(productos.get(position).getName());
            }
        });

        recycleCarrito.addView(newView);
    }

    private void confirmarEliminarProductoCarrito(String nombre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmación");
        builder.setMessage("¿Seguro de sacar del carrito \"" + nombre + "\" ?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do something
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void pickerCantidad(boolean b, int position, NumberPicker numberPickerCarrito, final mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito, TextView precioTotalCarrito, String precio) {
        int valor = numberPickerCarrito.getValue();
        if (valor == 0 && !b) {
            return;
        } else {
            valor = (b == false) ? valor - 1 : valor + 1;
            numberPickerCarrito.setValue(valor);

            actualizarCantidadDeProductosEnCarrito(valor, position, carrito, precioTotalCarrito, precio);
        }
    }

    private void actualizarCantidadDeProductosEnCarrito(final int cantidad, int position, mundo.hola.jose.miprimerholamundo.modelo.Carrito carrito, final TextView precioTotalCarrito, final String precio) {

        client.get(conversor.WEB_API_AUX + "UCartItemQuantity.php?id=" + carrito.getId() + "&row=" + position + "&qua=" + cantidad, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                precioTotalCarrito.setText("$" + String.format("%.2f", (Double.valueOf(precio.replace(",", ".")) * cantidad)));
                calcularTotales();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarStock(String stock, final int cant, final NumberPicker numberPickerCarrito) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/stock_availables/" + stock + "?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                String cantidad = conversor.buscarStock(res);
                if (!cantidad.equals("Este producto ya no está disponible")) {
                    numberPickerCarrito.setMaxValue(Integer.valueOf(cantidad));
                } else {
                    numberPickerCarrito.setEnabled(false);
                }
                numberPickerCarrito.setValue(cant);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    public double subtotal() {
        double acum = 0.0;
        for (int i = 0; i < recycleCarrito.getChildCount(); i++) {
            View hijo = recycleCarrito.getChildAt(i);
            TextView precioTotalCarrito = (TextView) hijo.findViewById(R.id.precioTotalCarrito);
            String precio = precioTotalCarrito.getText().toString().replace("$", "");
            acum += Double.valueOf(precio.trim().replace(",", "."));
        }
        return acum;
    }


    private void buscarDirecciones() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/addresses?filter[id_customer]=" + USUARIO_ID, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ids = conversor.buscarDirecciones(res);
                buscarDireccion(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarDireccion(final int hijo) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/addresses/" + ids.get(hijo), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                mundo.hola.jose.miprimerholamundo.modelo.Direccion direccion = conversor.buscarDireccion(res);
                direcciones.add(direccion);
                seguirBuscando(ids, hijo);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscando(ArrayList<String> lista, int hijo) {
        hijo++;
        if (hijo < lista.size()) {     // ¿Hay mas direcciones?
            buscarDireccion(hijo);
        } else {
            cargarSpiners();
        }
    }

    private void cargarSpiners() {
        for (Direccion dir : direcciones) {
            alias.add(dir.getAlias());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinerDirecciones.setAdapter(dataAdapter);
        spinerFacturacion.setAdapter(dataAdapter);
        conversor.ActionProcessButtonValidation(btnSiguienteCarrito2, true);
    }

    private void limpiarSpiners() {
        alias.clear();
        spinerDirecciones.setAdapter(null);
        spinerFacturacion.setAdapter(null);
        direcciones.clear();
        buscarDirecciones();
    }


    private void buscarEnvios(final int hijo, final boolean imprimir) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carriers?filter[deleted]=0&filter[active]=1&filter[is_free]=0", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                envio_id = conversor.buscarEnvios(res);

                if (envio_id.size() > 1) {
                    buscarEnvio(hijo, imprimir);
                } else {
                    conversor.mensajeCorto("No hay transportes disponibles");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarEnvio(final int hijo, final boolean imprimir) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/carriers/" + envio_id.get(hijo), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                buscarPrecioId(conversor.buscarEnvio(res), hijo, imprimir);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarPrecioId(final Carriers envio, final int hijo, final boolean imprimir) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/deliveries?filter[id_carrier]=" + envio.getId() + "&filter[id_zone]=6/", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                ArrayList<String> id_delivery = conversor.buscarDeliveries(res);
                buscarPrecio(id_delivery.get(0), hijo, envio, imprimir);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarPrecio(String s, final int hijo, final Carriers envio, final boolean imprimir) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/deliveries/" + s, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Deliveries deliveries = conversor.buscarDelivery(res);
                envio.setDeliveries(deliveries);

                if (imprimir) {
                    seguirBuscandoEnvio(hijo, envio);
                } else {
                    primerEnvio = envio;
                    double precio = calcularPrecioEnvio(envio);
                    txtPrecioEnvio.setText("$" + String.format("%.2f", precio));
                    calcularTotales();
                    conversor.ActionProcessButtonValidation(btnSiguienteCarrito, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscandoEnvio(int hijo, Carriers envio) {
        imprimirEnvios(envio, hijo);

        hijo++;
        if (hijo < envio_id.size()) {     // ¿Hay mas envios?
            buscarEnvio(hijo, true);
        } else {
            conversor.ActionProcessButtonValidation(btnSiguienteCarrito3, true);
        }
    }

    private void imprimirEnvios(final Carriers envio, final int hijo) {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.envio_item, contenedorEnvioItem, false);

        final CheckBox checkEnvio = (CheckBox) newView.findViewById(R.id.checkEnvio);
        TextView txtNombreEnvio = (TextView) newView.findViewById(R.id.txtNombreEnvio);
        TextView txtTiempoEnvio = (TextView) newView.findViewById(R.id.txtTiempoEnvio);
        TextView precio_envio = (TextView) newView.findViewById(R.id.txtPrecioEnvio);

        txtNombreEnvio.setText(envio.getName());
        txtTiempoEnvio.setText("Tiempo de entrega: " + envio.getDelay());
        if (hijo == EnvioActivo) {
            checkEnvio.setChecked(true);
        } else {
            checkEnvio.setChecked(false);
        }

        double precio = calcularPrecioEnvio(envio);
        precio_envio.setText("$" + String.format("%.2f", precio) + " (impuestos inc.)");

        checkEnvio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check(envio, hijo);
                }
            }
        });

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEnvio.isChecked()) {
                    checkEnvio.setChecked(true);
                    check(envio, hijo);
                }
            }
        });

        contenedorEnvioItem.addView(newView);
    }

    private void check(Carriers envio, int hijo) {
        EnvioActivo = hijo;
        Log.d("Envio", EnvioActivo + "");
        checkEnvios(hijo);

        txtPrecioEnvio.setText("$" + String.format("%.2f", calcularPrecioEnvio(envio)));
        calcularTotales();
    }

    private void checkEnvios(int hijoActivo) {
        for (int i = 0; i < contenedorEnvioItem.getChildCount(); i++) {
            if (i != hijoActivo) {
                View hijo = contenedorEnvioItem.getChildAt(i);
                CheckBox checkEnvio = (CheckBox) hijo.findViewById(R.id.checkEnvio);
                checkEnvio.setChecked(false);
            }
        }
    }

    private double calcularPrecioEnvio(Carriers envio) {
        double precio = Double.valueOf(envio.getDeliveries().getPrice().replace(",", "."));

        //   if (envio.getShipping_handling().equals("1")) {
        //      precio += shipping_handling;
        //  }

        if (envio.getId_tax_rules_group().equals("1")) {
            precio = precio + (precio * 0.12);
        }
        return precio;
    }

    private void buscarTerminosDeEnvio() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/content_management_system/3", parametros, new TextHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                mostrarTerminosDelServicio(Html.fromHtml(conversor.buscarTerminos(res)));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void mostrarTerminosDelServicio(Spanned mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("TÉRMINOS Y CONDICIONES");
        builder.setMessage(mensaje);

        builder.setPositiveButton("Acepto Terminos y Condicones", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!checkTerminosDelServicio.isChecked()) {
                    checkTerminosDelServicio.setChecked(true);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Atras", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }














    private void mostrarPagos() {
        final String[][] array = new String[4][4];
        array[0][0] = "Pago por transferencia bancaria";
        array[0][1] = "el procesamiento del pedido tomará más tiempo";
        array[0][2] = "bankwire";
        array[0][3] = "Transferencia bancaria";

        /*array[1][0] = "Pagar por cheque";
        array[1][1] = "el procesamiento del pedido tomará más tiempo";
        array[1][2] = "cheque";
        array[1][3] = "Cheque";
        */
        array[1][0] = "Pagar contra reembolso";
        array[1][1] = "Usted paga la mercancía a la entrega";
        array[1][2] = "cashondelivery";
        array[1][3] = "Cash on delivery (COD)";
        /*
        array[2][0] = "Pagar con HiPay";
        array[2][1] = "Asegure los pagos de Visa Mastercard y soluciones europeas";
        array[2][2] = "";
        array[2][3] = "";
        */
        for (int i = 0; i < 2; i++) {
            final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.botones_pago_item, contenedorPagoItem, false);

            ImageView imagenBoton = (ImageView) newView.findViewById(R.id.imagenBoton);
            TextView textoBoton = (TextView) newView.findViewById(R.id.textoBoton);
            TextView txtModule = (TextView) newView.findViewById(R.id.txtModule);
            TextView txtPayment = (TextView) newView.findViewById(R.id.txtPayment);

            textoBoton.setText(array[i][0]);
            txtModule.setText(array[i][2]);
            txtPayment.setText(array[i][3]);
            switch (i) {
                case 0:
                    imagenBoton.setImageResource(R.drawable.bankwire);
                    break;
                /*case 1:
                    imagenBoton.setImageResource(R.drawable.cheque);
                    break;*/
                case 1:
                    imagenBoton.setImageResource(R.drawable.cash);
                    break;
                /*case 2:
                    imagenBoton.setImageResource(R.drawable.gb);
                    break;*/
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int width = (int) (displayMetrics.widthPixels * 0.4);
            int heigth = (int) (displayMetrics.widthPixels * 0.48);

            imagenBoton.getLayoutParams().width = width;
            imagenBoton.getLayoutParams().height = heigth;

            final int aux = i;
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MODULE = array[aux][2];
                    PAYMENT = array[aux][3];
                    mostrarResumen(array[aux][0], aux);
                    btnSiguiente("Resumen", "Resumen del pedido", contenedorResumen, contenedorPago);
                }
            });
            newView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    conversor.mensajeLargo(array[aux][1]);
                    return false;
                }
            });

            contenedorPagoItem.addView(newView);
        }

    }





    private void mostrarResumen(String tipoPago, int tipo) {
        txtResumenSub.setText("Ha elegido " + tipoPago.toLowerCase() +". Aquí tiene un resumen de su pedido:");
        txtResumenCond1.setText("* El importe total de su pedido es $" + String.format("%.2f", Double.valueOf((txtTOTAL.getText().toString().replace("$", "")).replace(",", ".")))   + " (impuestos inc.)");
        txtResumenCond2.setText("* Aceptamos las siguientes monedas para las transferencias bancarias: Dolar.");

        switch (tipo) {
            case 0:
                txtResumenCond3.setText("* La información para realizar la transferencia bancaria aparecerá en la página siguiente.");
                break;
            case 1:
                txtResumenCond3.setText("* La orden y la dirección del cheque aparecerán en la siguiente página.");
                break;
            case 2:
                txtResumenCond3.setVisibility(View.GONE);
                break;
            case 3:
                txtResumenCond3.setVisibility(View.GONE);
                break;
        }
        txtResumenCond4.setText("* Por favor, confirme su pedido haciendo clic en \"Confirmar Pedido\".");
    }


    private void confirmarPedido() {
        conversor.ActionProcessButtonValidation(btnConfirmarPedido, false);

        RequestParams parametros = new RequestParams();
        parametros.add("id_address_delivery", String.valueOf(DIR_ACT));
        parametros.add("id_address_invoice", String.valueOf(DIR_FAC == 0 ? DIR_ACT : DIR_FAC));
        parametros.add("id_cart", CARRITO_ACTUAL.getId());
        parametros.add("id_currency", "1");
        parametros.add("id_lang", "1");
        parametros.add("id_customer", USUARIO_ID);
        parametros.add("id_carrier", envio_id.get(EnvioActivo));
        parametros.add("module", MODULE);
        parametros.add("payment", PAYMENT);
        parametros.add("total_paid", txtTOTAL.getText().toString().replace("$", ""));
        parametros.add("total_paid_real", "0.000000");
        parametros.add("total_products", String.valueOf(subtotal()));
        parametros.add("total_products_wt", txtSubTotaProducto.getText().toString().replace("$", ""));
        parametros.add("conversion_rate", "1.000000");
        Log.d("Envio id", envio_id.get(EnvioActivo));
        final int DEFAULT_TIMEOUT = 20 * 1000;
        client.setTimeout(DEFAULT_TIMEOUT);

        Log.d("POST CCartOrder", conversor.WEB_API_AUX + "CCartOrder.php?" + parametros.toString());
        client.post(conversor.WEB_API_AUX + "CCartOrder.php?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                buscarOrderRealizada();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarOrderRealizada() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/orders?filter[id_customer]=" + USUARIO_ID + "&sort=[id_DESC]", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                buscarOrden(conversor.existeOrdenes(res).get(0));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarOrden(String id) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/orders/" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Order order = conversor.buscarOrden(res);
                if (PAYMENT.equals("Cash on delivery (COD)")) {
                    irPago("", "", "", "", order.getReference());
                } else {
                    buscarConfBankAddress(order);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarConfBankAddress(final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        Log.d("URL", conversor.WEB_PAGE + "/configurations?filter[name]=BANK_WIRE_ADDRESS");
        client.get(conversor.WEB_PAGE + "/configurations?filter[name]=BANK_WIRE_ADDRESS", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Log.d("Value", res);
                buscarValorConfBankAddress(conversor.configurations(res), order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarValorConfBankAddress(String id, final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        Log.d("URL", conversor.WEB_PAGE + "/configurations/" + id);
        client.get(conversor.WEB_PAGE + "/configurations/" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Log.d("Value", res);
                buscarConfProp(conversor.configuration(res), order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarConfProp(final String banco, final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);
        Log.d("URL", conversor.WEB_PAGE + "/configurations?filter[name]=BANK_WIRE_OWNER");

        client.get(conversor.WEB_PAGE + "/configurations?filter[name]=BANK_WIRE_OWNER", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Log.d("Value", res);
                buscarValorConfProp(conversor.configurations(res), banco, order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarValorConfProp(String id, final String banco, final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);
        Log.d("URL", conversor.WEB_PAGE + "/configurations/" + id);
        client.get(conversor.WEB_PAGE + "/configurations/" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Log.d("Value", res);
                buscarConfDatos(conversor.configuration(res), banco, order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarConfDatos(final String propietario, final String banco, final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);
        Log.d("URL", conversor.WEB_PAGE + "/configurations?filter[name]=BANK_WIRE_DETAILS");

        client.get(conversor.WEB_PAGE + "/configurations?filter[name]=BANK_WIRE_DETAILS", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Log.d("Value", res);
                buscarValorConfDatos(conversor.configurations(res), propietario, banco, order);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void buscarValorConfDatos(String id, final String propietario, final String banco, final Order order) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);
        Log.d("URL", conversor.WEB_PAGE + "/configurations/" + id);
        client.get(conversor.WEB_PAGE + "/configurations/" + id, parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Log.d("Value", res);
                irPago(txtTOTAL.getText().toString(), propietario, conversor.configuration(res), banco, order.getReference());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void irPago(String importe, String propietario, String datos, String banco, String referencia) {
        conversor.ActionProcessButtonValidation(btnConfirmarPedido, true);

        Intent i = new Intent(this.getApplicationContext(), Pago.class);
        i.putExtra("payment", PAYMENT);

        i.putExtra("importe", importe);
        i.putExtra("propietario", propietario);
        i.putExtra("datos", datos);
        i.putExtra("banco", banco);
        i.putExtra("referencia", referencia);
Log.d("Pago", PAYMENT + importe + propietario + datos + banco + referencia);
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
            case android.R.id.home:
                String titulo = txtTituloCarrito.getText().toString();
                if (titulo.equals("Resumen del pedido")) {
                    btnSiguiente("Paso 4 / 4", "Pago", contenedorPago, contenedorResumen);
                } else if (titulo.equals("Pago")) {
                    btnSiguiente("Paso 3 / 4", "Transporte", contenedorEnvio, contenedorPago);
                } else if (titulo.equals("Transporte")) {
                    conversor.ActionProcessButtonValidation(btnSiguienteCarrito2, true);
                    btnSiguiente("Paso 2 / 4", "Dirección", contenedorDirecconesCarrito, contenedorEnvio);
                    direcioNueva.setVisible(true);
                } else if (titulo.equals("Dirección")) {
                    btnSiguiente("Paso 1 / 4", "Carrito", contenedorCarrito, contenedorDirecconesCarrito);
                    direcioNueva.setVisible(false);
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                return true;
            case R.id.btndireccion:
                Intent intent = new Intent(this.getApplicationContext(), Direcciones.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dreccion, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        direcioNueva = menu.findItem(R.id.btndireccion);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSiguienteCarrito:
                botonesLoading();
                limpiarSpiners();
                btnSiguiente("Paso 2 / 4", "Dirección", contenedorDirecconesCarrito, contenedorCarrito);
                direcioNueva.setVisible(true);
                break;
            case R.id.btnSiguienteCarrito2:
                botonesLoading();
                contenedorEnvioItem.removeAllViews();
                seguirBuscandoEnvio(0, primerEnvio);
                btnSiguiente("Paso 3 / 4", "Transporte", contenedorEnvio, contenedorDirecconesCarrito);
                direcioNueva.setVisible(false);
                break;
            case R.id.btnCondicionesDelServicio:
                buscarTerminosDeEnvio();
                break;
            case R.id.btnSiguienteCarrito3:
                if (checkTerminosDelServicio.isChecked()) {
                    mostrarPagos();
                    btnConfirmarPedido.setProgress(0);
                    btnSiguiente("Paso 4 / 4", "Pago", contenedorPago, contenedorEnvio);
                } else {
                    conversor.mensajeCorto("Debe aceptar los términos del servicio");
                }
                break;
            case R.id.btnConfirmarPedido:
                confirmarPedido();
                break;
        }
    }

    private void btnSiguiente(String subtitulo, String titulo, LinearLayout visible, LinearLayout gone) {
        getSupportActionBar().setSubtitle(subtitulo);
        txtTituloCarrito.setText(titulo);
        gone.setVisibility(View.GONE);
        visible.setVisibility(View.VISIBLE);
    }

    private void botonesLoading() {
        conversor.ActionProcessButtonValidation(btnSiguienteCarrito2, false);
        conversor.ActionProcessButtonValidation(btnSiguienteCarrito3, false);
    }

}
