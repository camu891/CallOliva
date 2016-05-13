package com.matic.calloliva;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import static android.Manifest.permission.CALL_PHONE;

public class ShowDataEntity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CALL_PHONE =   0;
    private DataBaseManager dbmanager;

    private TextView tvnombre;
    private TextView tvdescripcion;
    private TextView tvtelefono;
    private ImageView tvimagen;
    private LatLng coordenadas_entidad;
    private TextView tvemail;
    private String email;

    private AlertDialog ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_entity);

        //activar boton back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recibirDatos();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney, Australia, and move the camera.
        map.addMarker(new MarkerOptions().position(coordenadas_entidad).title(tvnombre.getText().toString()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas_entidad, 15f));

        //map.setMyLocationEnabled(true);
    }


    public void recibirDatos() {

        dbmanager = new DataBaseManager(this);

        String nombre = getIntent().getStringExtra(dbmanager.CN_NAME);
        String descripcion = getIntent().getStringExtra(dbmanager.CN_DESCRIPCION);
        String telefono = getIntent().getStringExtra(dbmanager.CN_TELEFONO);
        double lat = Double.parseDouble(getIntent().getStringExtra(dbmanager.CN_LAT));
        double lon = Double.parseDouble(getIntent().getStringExtra(dbmanager.CN_LON));
        int logo = Integer.parseInt(getIntent().getStringExtra(dbmanager.CN_LOGO));
        email= getIntent().getStringExtra(dbmanager.CN_EMAIL);

        coordenadas_entidad = new LatLng(lat, lon);

        tvnombre = (TextView) findViewById(R.id.txt_snombre);
        tvdescripcion = (TextView) findViewById(R.id.txt_sdescripcion);
        tvtelefono = (TextView) findViewById(R.id.txt_stelefono);
        tvimagen = (ImageView) findViewById(R.id.simagen);



        tvnombre.setText(nombre);
        tvdescripcion.setText(descripcion);
        tvtelefono.setText(telefono);
        tvimagen.setBackgroundResource(logo);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void callEntity(View view) {
        //para pedir los permisos
        populateAutoComplete();

        String name=tvnombre.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirmacion));
        builder.setMessage(getString(R.string.call_message)+" "+name+"?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String tel = tvtelefono.getText().toString().trim();

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                        if (ActivityCompat.checkSelfPermission(ShowDataEntity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }



    private boolean myRequestCall() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(CALL_PHONE)) {
            Snackbar.make(null, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{CALL_PHONE}, REQUEST_CALL_PHONE);
                        }
                    });
        } else {
            requestPermissions(new String[]{CALL_PHONE}, REQUEST_CALL_PHONE);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }



    private void populateAutoComplete() {
        if (!myRequestCall()) {
            return;
        }

    }


    public void sendEmail(View view) {

        if(!email.equals("null")) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Enviado desde CallOliva");
        startActivity(Intent.createChooser(intent, "Enviar Email"));
        }
        else{
            Toast.makeText(this,getString(R.string.message_error_email),Toast.LENGTH_SHORT).show();
        }

    }



}
