package com.matic.calloliva;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ListView listado;
    private CardView cardView;


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private DataBaseManager dbmanager;
    private Cursor cursor;

    private SimpleCursorAdapter adaptador;

    private EditText editTxt;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();


        cargarListado();

        //cargarCardView();

        editTxt= (EditText) findViewById(R.id.txt_buscar);
        btnBuscar= (Button) findViewById(R.id.btn_buscar);
        btnBuscar.setOnClickListener(this);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



   private void cargarListado(){


       dbmanager=new DataBaseManager(this);
       /*dbmanager.insertar("Bomberos","Bomberos","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva"," Cordoba","Argentina",R.drawable.ic_menu_camera);
       dbmanager.insertar("Bomberos2","Bomberos2","324324",-34.565656,-60.54656,"Bolivia",156,"Oliva"," Cordoba","Argentina",R.drawable.ic_menu_camera);
       dbmanager.insertar("Bomberos3","Bomberos3","132432",-34.565656,-60.54656,"Bolivia",156,"Oliva"," Cordoba","Argentina",R.drawable.ic_menu_camera);
        */

        cursor=dbmanager.cargarCursorEntidades();

        listado = (ListView)findViewById(R.id.listview);

        String[] from= new String[]{dbmanager.CN_LOGO,dbmanager.CN_NAME,dbmanager.CN_DESCRIPCION,dbmanager.CN_TELEFONO,dbmanager.CN_LAT,dbmanager.CN_LON};
        int[] to=new int[]{R.id.imagen,R.id.nombre,R.id.descripcion,R.id.telefono,R.id.lat,R.id.lon};

        adaptador=new SimpleCursorAdapter(this,R.layout.card_view,cursor,from,to);
        listado.setAdapter(adaptador);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), "Item select: " + position, Toast.LENGTH_SHORT).show();

                // getting values from selected ListItem
                String nombre = ((TextView) view.findViewById(R.id.nombre)).getText().toString();
                String descripcion = ((TextView) view.findViewById(R.id.descripcion)).getText().toString();
                String telefono = ((TextView) view.findViewById(R.id.telefono)).getText().toString();
                String lat=((TextView) view.findViewById(R.id.lat)).getText().toString();
                String lon=((TextView) view.findViewById(R.id.lon)).getText().toString();
                String logo=view.findViewById(R.id.imagen).getResources().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(), ShowDataEntity.class);
                // sending pid to next activity
                in.putExtra(dbmanager.CN_NAME, nombre);
                in.putExtra(dbmanager.CN_DESCRIPCION, descripcion);
                in.putExtra(dbmanager.CN_TELEFONO, telefono);
                in.putExtra(dbmanager.CN_LAT,lat);
                in.putExtra(dbmanager.CN_LON,lon);
                in.putExtra(dbmanager.CN_LOGO, logo);


                // starting new activity and expecting some response back
                startActivityForResult(in, 100);



            }

        });


}


    public void cargarCardView(){



        List<Entidad> items = new ArrayList<>();

        items.add(new Entidad(0,"Bomberos","Bomberos","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva", " Cordoba","Argentina",R.drawable.ic_menu_camera));
        items.add(new Entidad(1,"Policia","Policia","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva", " Cordoba","Argentina",R.drawable.ic_menu_manage));
        items.add(new Entidad(0,"Bomberos","Bomberos","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva ", "Cordoba","Argentina",R.drawable.ic_menu_camera));
        items.add(new Entidad(1,"Policia","Policia","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva", "Cordoba","Argentina",R.drawable.ic_menu_manage));
        items.add(new Entidad(0,"Bomberos","Bomberos","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva ", "Cordoba","Argentina",R.drawable.ic_menu_camera));
        items.add(new Entidad(1,"Policia","Policia","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva", "Cordoba","Argentina",R.drawable.ic_menu_manage));


        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new EntidadAdapter(items);
        recycler.setAdapter(adapter);




    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_buscar)
        {

            String txtingresado=editTxt.getText().toString();
            String txthint=editTxt.getHint().toString();

            if (!txtingresado.isEmpty()){


                Cursor c=dbmanager.buscarEntidad(txtingresado);
                adaptador.changeCursor(c);

                if (adaptador.isEmpty())
                {
                    Toast.makeText(this,"No se encontraron resultados",Toast.LENGTH_SHORT).show();
                }


            } else{


               cargarListado();
            }


        }
    }
}
