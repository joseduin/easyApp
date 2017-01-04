package mundo.hola.jose.miprimerholamundo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import mundo.hola.jose.miprimerholamundo.modelo.*;
import mundo.hola.jose.miprimerholamundo.modelo.Direccion;

public class Direcciones extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ViewGroup mContainerView;
    private Button btnAddNuevaDir;

    private Conversor conversor = new Conversor();
    private AsyncHttpClient client = new AsyncHttpClient();

    private SessionManager session;
    private ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mis Direcciones");

        mContainerView = (ViewGroup) findViewById(R.id.container);
        btnAddNuevaDir = (Button) findViewById(R.id.btnAddNuevaDir);

        conversor.setContext(this.getApplicationContext());
        session = new SessionManager(getApplicationContext());
        Customer customer = session.getCustomerCurrent();

        buscarDirecciones(customer.getId());

        btnAddNuevaDir.setOnClickListener(this);
    }

    private void buscarDirecciones(String id) {
        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_PAGE + "/addresses?filter[id_customer]=" + id, parametros, new TextHttpResponseHandler() {
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
                seguirBuscando(ids, hijo, direccion);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void seguirBuscando(ArrayList<String> lista, int hijo, Direccion dir) {
        imprimirDir(dir, hijo);

        hijo++;
        if (hijo < lista.size()) {     // ¿Hay mas direcciones?
            buscarDireccion(hijo);
        }
    }

    private void imprimirDir(final Direccion dir, final int hijo) {
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.direccion_item, mContainerView, false);

        TextView aliasDirItem = (TextView) newView.findViewById(R.id.aliasDirItem);
        TextView nombreDirItem = (TextView) newView.findViewById(R.id.nombreDirItem);
        TextView apellidoDirItem = (TextView) newView.findViewById(R.id.apellidoDirItem);
        TextView rucDirItem = (TextView) newView.findViewById(R.id.rucDirItem);
        TextView empresaDirItem = (TextView) newView.findViewById(R.id.empresaDirItem);
        TextView dir1DirItem = (TextView) newView.findViewById(R.id.dir1DirItem);
        TextView dir2DirItem = (TextView) newView.findViewById(R.id.dir2DirItem);
        TextView ciudadDirItem = (TextView) newView.findViewById(R.id.ciudadDirItem);
        TextView paisDirItem = (TextView) newView.findViewById(R.id.paisDirItem);
        TextView tlfDirItem = (TextView) newView.findViewById(R.id.tlfDirItem);
        TextView tlfmovilDirItem = (TextView) newView.findViewById(R.id.tlfmovilDirItem);

        aliasDirItem.setText(dir.getAlias());
        nombreDirItem.setText(dir.getFirstname());
        apellidoDirItem.setText(dir.getLastname());
        rucDirItem.setText(dir.getDni());
        dir1DirItem.setText(dir.getAddress1());
        ciudadDirItem.setText(dir.getCity());
        paisDirItem.setText("Ecuador");

        validartextView(empresaDirItem, dir.getCompany());
        validartextView(dir2DirItem, dir.getAddress2());
        validartextView(tlfDirItem, dir.getPhone());
        validartextView(tlfmovilDirItem, dir.getPhone_mobile());

        final Context context = this.getApplicationContext();

        newView.findViewById(R.id.btnGuardarDireccionItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, mundo.hola.jose.miprimerholamundo.Direccion.class);
                intent.putExtra("direccion", dir);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
        newView.findViewById(R.id.btnElimininarDireccionItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar(dir, hijo);
            }
        });

        mContainerView.addView(newView, hijo);

        if (btnAddNuevaDir.getVisibility() == View.GONE) {
            btnAddNuevaDir.setVisibility(View.VISIBLE);
        }
    }

    private void confirmar(final Direccion dir, final int hijo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmación");
        builder.setMessage("¿Seguro de borrar \"" + dir.getAlias() + "\" ?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                borrarDireccion(dir, hijo);
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

    private void borrarDireccion(Direccion dir, final int hijo) {
        final Context context = this.getApplicationContext();
        if (ids.size() == 1) {
            conversor.mensajeLargo("No puede borrar todas las direcciones");
            return;
        }

        RequestParams parametros = new RequestParams();
        conversor.parametrosBasicos(parametros);

        client.get(conversor.WEB_API_AUX + "DAddress.php?DeleteID=" + dir.getId(), parametros, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                try {
                    mContainerView.removeViewAt(hijo);
                    ids.remove(hijo);
                    conversor.mensajeLargo("Se elimino correctamente la direccion");
                } catch (Exception e) {
                    conversor.errorLoading();       // Error raro e.e
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                conversor.errorLoading();
            }
        });
    }

    private void validartextView(TextView textView, String valor) {
        if (!valor.equals("")) {
            textView.setText(valor);
        } else {
            textView.setVisibility(View.GONE);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNuevaDir:
                Intent intent = new Intent(this.getApplicationContext(), mundo.hola.jose.miprimerholamundo.Direccion.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                break;
        }
    }
}
