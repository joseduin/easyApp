package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.Categoria;
import mundo.hola.jose.miprimerholamundo.modelo.Conversor;
import mundo.hola.jose.miprimerholamundo.modelo.Customer;
import mundo.hola.jose.miprimerholamundo.modelo.SessionManager;

public class Inicio extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ImageButton carritoInicio, misPedidosInicio, direccionesInicio, perfilInicio, productoInicio, comoComprarInicio, contactoInicio, cerrarSesionInicio;
    private TextView txtCorreoNav;
    private DrawerLayout drawer;
    private GridLayout gridInicio;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    private SessionManager session;
    private String ID_USUARIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);   // activity_inicio
        navigationView.setNavigationItemSelectedListener(this);

        carritoInicio = (ImageButton) findViewById(R.id.carritoInicio);
        misPedidosInicio = (ImageButton) findViewById(R.id.misPedidosInicio);
        direccionesInicio = (ImageButton) findViewById(R.id.direccionesInicio);
        perfilInicio = (ImageButton) findViewById(R.id.perfilInicio);
        gridInicio = (GridLayout) findViewById(R.id.gridInicio);
        productoInicio = (ImageButton) findViewById(R.id.productoInicio);
        comoComprarInicio = (ImageButton) findViewById(R.id.comoComprarInicio);
        contactoInicio = (ImageButton) findViewById(R.id.contactoInicio);
        cerrarSesionInicio = (ImageButton) findViewById(R.id.cerrarSesionInicio);

        View hView =  navigationView.getHeaderView(0);
        txtCorreoNav = (TextView) hView.findViewById(R.id.txtCorreoNav);

        if (session.isLoggedIn()) {
            Customer customer = session.getCustomerCurrent();
            ID_USUARIO = customer.getId();
            txtCorreoNav.setText(customer.getEmail());

            // Boton doble rol
            cerrarSesionInicio.setImageDrawable(getResources().getDrawable(R.drawable.cerrar_sesion));

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);         // tollbar en app_bar_inicio
            setSupportActionBar(toolbar);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);          // activity_inicio
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        } else {
            ID_USUARIO = "";
            txtCorreoNav.setText("");

            // Botones que se veran si y solo si el usuario esta logeado
            carritoInicio.setVisibility(View.GONE);
            misPedidosInicio.setVisibility(View.GONE);
            direccionesInicio.setVisibility(View.GONE);
            perfilInicio.setVisibility(View.GONE);

            // Boton doble rol
            cerrarSesionInicio.setImageDrawable(getResources().getDrawable(R.drawable.iniciar_sesion));

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);         // tollbar en app_bar_inicio
            setSupportActionBar(toolbar);
        }

        botonesMedianteLaOrientacionDeLaPantalla();
    }

    private void botonesMedianteLaOrientacionDeLaPantalla() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width;
        int height;

        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if (rotation == 0 || rotation == 180) {
        //    gridInicio.setColumnCount(2);

            width = (int) (displayMetrics.widthPixels * 0.44);
            height = (int) (displayMetrics.widthPixels * 0.6);
        } else {
        //    gridInicio.setColumnCount(4);

            width = (int) (displayMetrics.widthPixels * 0.25);
            height = (int) (displayMetrics.widthPixels * 0.2);//(int) (displayMetrics.heightPixels * 0.4);
        }

        layaoutParams(carritoInicio, width, height);
        layaoutParams(misPedidosInicio, width, height);
        layaoutParams(direccionesInicio, width, height);
        layaoutParams(perfilInicio, width, height);
        layaoutParams(productoInicio, width, height);
        layaoutParams(comoComprarInicio, width, height);
        layaoutParams(contactoInicio, width, height);
        layaoutParams(cerrarSesionInicio, width, height);
    }

    private void layaoutParams(ImageButton button, int width, int height) {
        button.getLayoutParams().width = width;
        button.getLayoutParams().height = height;
        button.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnBuscar) {
            irBusqueda();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void irBusqueda() {
        Intent intent = new Intent(this, Busqueda.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.categorias:
                buscarCategorias();
                break;
            case R.id.misOrdenes:
                irA(Ordenes.class);
                break;
            case R.id.carrito:
                existeCarrito();            // Buscamos si tiene algo en el carrito
                break;
            case R.id.miPerfil:
                irA(DatosPersonales.class);
                break;
            case R.id.miDireccion:
                irA(Direcciones.class);
                break;
            case R.id.cerrarSesion:
                session.logout();
                irA(Login.class);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*
    *       BUSCAR CATEGORIAS
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * ** */
    public void buscarCategorias() {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/categories/22?", parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                Categoria categoria = conversor.buscarCategoria(res);
                buscarImagen(categoria);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    /*  Dos tipos de tamaño para las imagenes
        *       category_default    - mediana
        *       medium_default  - miniatura
        *
        *       Si no se le pone nada, agarra la extra gigante D:
        * * * * * * * * * * * */
    public void buscarImagen(final Categoria categoria) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/images/categories/" + categoria.getId() + "/medium_default?", parametros, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                categoria.setImagen(conversor.BitMapToString(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
                irCategoria(categoria);
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                conversor.errorLoading();
            }
        });
    }

    public void irCategoria(Categoria categoria) {
        Intent intent = new Intent(this.getApplicationContext(), CategoriaXML.class);
        intent.putExtra("categoria", categoria);
        intent.putExtra("titulo", 0);
        intent.putExtra("padre", "");

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    private void irA(Class<?> clas) {
        Intent intent = new Intent(this.getApplicationContext(), clas);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /*
    *       FIN - BUSCAR CATEGORIAS
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * ** */

    /*
    *       BUSCAR CARRITO
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * ** */
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

    /*
    *       FIN - BUSCAR CATEGORIAS
    *
    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ** * * * * * * ** */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.carritoInicio:
                existeCarrito();
                break;
            case R.id.misPedidosInicio:
                irA(Ordenes.class);
                break;
            case R.id.direccionesInicio:
                irA(Direcciones.class);
                break;
            case R.id.perfilInicio:
                irA(DatosPersonales.class);
                break;
            case R.id.productoInicio:
                buscarCategorias();
                break;
            case R.id.comoComprarInicio:
                irA(Como_comprar.class);
                break;
            case R.id.contactoInicio:
                irA(Contacto.class);
                break;
            case R.id.cerrarSesionInicio:
                session.logout();
                irA(Login.class);
                if (!ID_USUARIO.equals("")) {       // Cuando el boton sea de cerrar sesion y no de iniciar sesion
                    finish();
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                break;
        }
    }

}
