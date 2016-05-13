package com.matic.calloliva;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    private ListView listado;
    private CardView cardView;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private DataBaseManager dbmanager;
    private Cursor cursor;

    private SimpleCursorAdapter adaptador;

    private EditText editTxtBuscar;
    private Button btnBuscar;

    private ImageView iv;

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

        /*
        editTxtBuscar = (EditText) findViewById(R.id.txt_buscar);
        //investigar porque no funciona
        editTxtBuscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    buscador();
                    return true;
                }

                return false;
            }
        });
        btnBuscar= (Button) findViewById(R.id.btn_buscar);
        btnBuscar.setOnClickListener(this);*/


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
        //getMenuInflater().inflate(R.menu.main, menu);
        //return true;
        /*getMenuInflater().inflate(R.menu.buscador, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_buscador);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);*/

        try {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu, menu);

            RelativeLayout actionView = (RelativeLayout) menu.findItem(R.id.menu_search).getActionView();
            final EditText editText = (EditText) actionView.findViewById(R.id.etSearch);
            //final EditText editText = (EditText) menu.findItem(R.id.menu_search).getActionView();
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    menuBuscador(s);

                }

                @Override
                public void afterTextChanged(Editable s) {



                }


            });

            MenuItem menuItem = menu.findItem(R.id.menu_search);
            MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    editText.requestFocus();

                    //((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    return true; // Return true to expand action view
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed

                    // Borramos el texto que había escrito.
                    editText.setText("");

                    return true; // Return true to collapse action view
                }
            });
        } catch (Exception ex) {

        }

        return super.onCreateOptionsMenu(menu);



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

       //cargo datos a mano para probar
       /*dbmanager.insertar("Bomberos","Bomberos Voluntarios Oliva","(03532)-420019",-32.041898, -63.567356,"Caseros",245,"Oliva"," Cordoba","Argentina",R.drawable.logo_bomberos,"bomberosvoluntariosoliva@hotmail.com");
       dbmanager.insertar("Policia","Policia Oliva","(03532) 42-8921",-32.042117, -63.567657,"Caseros",227,"Oliva"," Cordoba","Argentina",R.drawable.logo_policia,"null");
       dbmanager.insertar("Grido","Grido Helados\nhttp://www.gridohelado.com.ar/","(0351) 15-515-1930",-32.041107, -63.570168,"Av Emilio Olmos", 111,"Oliva"," Cordoba","Argentina",R.drawable.logo_grido,"null");
     */

        cursor=dbmanager.cargarCursorEntidades();

        listado = (ListView)findViewById(R.id.listview);

        String[] from= new String[]{dbmanager.CN_LOGO,dbmanager.CN_NAME,dbmanager.CN_CALLE,dbmanager.CN_NCALLE,dbmanager.CN_DESCRIPCION,dbmanager.CN_TELEFONO,dbmanager.CN_LAT,dbmanager.CN_LON,dbmanager.CN_LOGO,dbmanager.CN_EMAIL};
        int[] to=new int[]{R.id.imagen,R.id.nombre,R.id.calle,R.id.nrocalle,R.id.descripcion,R.id.telefono,R.id.lat,R.id.lon,R.id.logo,R.id.email};

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
                String logo=((TextView) view.findViewById(R.id.logo)).getText().toString();
                String email=((TextView)view.findViewById(R.id.email)).getText().toString();


                // Starting new intent
                Intent in = new Intent(getApplicationContext(), ShowDataEntity.class);
                // sending pid to next activity
                in.putExtra(dbmanager.CN_NAME, nombre);
                in.putExtra(dbmanager.CN_DESCRIPCION, descripcion);
                in.putExtra(dbmanager.CN_TELEFONO, telefono);
                in.putExtra(dbmanager.CN_LAT,lat);
                in.putExtra(dbmanager.CN_LON,lon);
                in.putExtra(dbmanager.CN_LOGO, logo);
                in.putExtra(dbmanager.CN_EMAIL, email);



                // starting new activity and expecting some response back
                startActivityForResult(in, 100);

            }

        });

}


    public void cargarCardView(){



        List<Entidad> items = new ArrayList<>();

        items.add(new Entidad(0,"Bomberos","Bomberos","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva", " Cordoba","Argentina",R.drawable.ic_menu_camera,"aa@a,com"));
        items.add(new Entidad(1,"Policia","Policia","12345",-34.565656,-60.54656,"Bolivia",156,"Oliva", " Cordoba","Argentina",R.drawable.ic_menu_manage,"aa@a,com"));



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
        /*if(view.getId()==R.id.btn_buscar)
        {
            buscador();
        }*/
    }

    /*
    private void buscador() {
        String txtingresado= editTxtBuscar.getText().toString();
        String txthint= editTxtBuscar.getHint().toString();

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

    }*/


    public void menuBuscador(CharSequence s){
        Cursor c=dbmanager.buscarEntidad(s);
        adaptador.changeCursor(c);

        if (adaptador.isEmpty())
        {
            Toast.makeText(this,"No se encontraron resultados",Toast.LENGTH_SHORT).show();
        }
    }


    //metodos de busqueda
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        menuBuscador(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        menuBuscador(newText);
        return false;
    }
}
