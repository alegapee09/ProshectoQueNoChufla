package com.dam.proyectoa_pmdm_t2_alejandro_garcia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.proyectoa_pmdm_t2_alejandro_garcia.fragmentos.FiltroDialog;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.fragmentos.ListadoFragment;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.fragmentos.MapaFragment;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.listener.OnXListener;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitutils.APIRestService;
import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitutils.RetrofitClient;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnXListener {

    Button btnFiltro;
    TextView tvFiltroLat;
    TextView tvFiltroLon;
    TextView tvFiltroDis;
    Button btnConsulta;

    Double latitud;
    Double longitud;
    Double distancia;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFiltro = findViewById(R.id.btnFiltro);
        btnConsulta = findViewById(R.id.btnConsulta);

        tvFiltroLat = findViewById(R.id.tvFiltroLat);
        tvFiltroLon = findViewById(R.id.tvFiltroLon);
        tvFiltroDis = findViewById(R.id.tvFiltroDis);

        fragmentManager = getSupportFragmentManager();


        btnConsulta.setOnClickListener(view -> {

            ListadoFragment lf = new ListadoFragment();
            cargarFragment(lf);

            APIRestService ars = initRetrofit();
            lf.actualizarLista(ars , latitud, longitud, distancia);

            latitud = null;
            longitud = null;
            distancia = 0.0;

            tvFiltroLat.setText("");
            tvFiltroLon.setText("");
            tvFiltroDis.setText("");

        });
        btnFiltro.setOnClickListener(view -> {
            FiltroDialog filtro = new FiltroDialog();
            filtro.show(getSupportFragmentManager(), "Filtro");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


        private void cargarFragment(Fragment fr) {

            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.frameLay, fr);
            ft.addToBackStack(null);
            ft.commit();
        }

    public APIRestService initRetrofit() {

        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = retrofit.create(APIRestService.class);

        return ars;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuListado:

                btnConsulta.setText(R.string.consulta);

                ListadoFragment lf = new ListadoFragment();
                cargarFragment(lf);

                APIRestService ars = initRetrofit();

                btnConsulta.setOnClickListener(view -> {
                    lf.actualizarLista(ars , latitud, longitud, distancia);
                });
                break;

            case R.id.menuMapa:

                btnConsulta.setText(R.string.consulta);

                Fragment fr = new MapaFragment();
                cargarFragment(fr);

                APIRestService arss = initRetrofit();

                btnConsulta.setOnClickListener(view -> {

                    MapaFragment mf = (MapaFragment) fr;
                    mf.actualizarMapa(arss, latitud, longitud, distancia);

                    latitud = null;
                    longitud = null;
                    distancia = 0.0;

                    tvFiltroLat.setText("");
                    tvFiltroLon.setText("");
                    tvFiltroDis.setText("");

                });
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void OnXListener(Double latitud, Double longitud, Double distancia) {

        this.latitud = latitud;
        this.longitud = longitud;
        this.distancia = distancia;

        String txtlatitud = getString(R.string.hintLatitud);
        String txtlongitud = getString(R.string.hintLongitud);
        String txtdistancia = getString(R.string.hintDistancia);

        tvFiltroLat.setText(getString(R.string.filtro, txtlatitud, String.valueOf(latitud)));
        tvFiltroLon.setText(getString(R.string.filtro, txtlongitud, String.valueOf(longitud)));
        tvFiltroDis.setText(getString(R.string.filtro, txtdistancia, String.valueOf(distancia)));

    }
}