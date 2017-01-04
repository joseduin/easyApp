package mundo.hola.jose.miprimerholamundo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Pago extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView importePago, propietaroPago, datosPago, bancoPago, referenciaPago;
    private Button btnHistorialPedido1, btnHistorialPedido2;
    private LinearLayout contenedorPagoTransferencia, contenedorPagoContrareembolso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        contenedorPagoTransferencia = (LinearLayout) findViewById(R.id.contenedorPagoTransferencia);
        contenedorPagoContrareembolso = (LinearLayout) findViewById(R.id.contenedorPagoContrareembolso);

        importePago = (TextView) findViewById(R.id.importePago);
        propietaroPago = (TextView) findViewById(R.id.propietaroPago);
        datosPago = (TextView) findViewById(R.id.datosPago);
        bancoPago = (TextView) findViewById(R.id.bancoPago);
        referenciaPago = (TextView) findViewById(R.id.referenciaPago);
        btnHistorialPedido1 = (Button) findViewById(R.id.btnHistorialPedido1);
        btnHistorialPedido2 = (Button) findViewById(R.id.btnHistorialPedido2);

        contenedorPagoTransferencia.setVisibility(View.GONE);
        contenedorPagoContrareembolso.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("payment").equals("Cash on delivery (COD)")) {
                contenedorPagoContrareembolso.setVisibility(View.VISIBLE);

            } else {
                contenedorPagoTransferencia.setVisibility(View.VISIBLE);

                importePago.setText("- Importe " + extras.getString("importe"));
                propietaroPago.setText("- Propietario de la cuenta " + extras.getString("propietario"));
                datosPago.setText("- Con los siguientes datos " + extras.getString("datos"));
                bancoPago.setText("- Banco " + extras.getString("banco"));
                referenciaPago.setText("- No se olvde de indicar su número de pedido " + extras.getString("referencia") + "  en el concepto de su tranferencia bancaria");
            }
        }

        btnHistorialPedido1.setOnClickListener(this);
        btnHistorialPedido2.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this.getApplicationContext(), Ordenes.class);
        startActivity(i);
    }
}
