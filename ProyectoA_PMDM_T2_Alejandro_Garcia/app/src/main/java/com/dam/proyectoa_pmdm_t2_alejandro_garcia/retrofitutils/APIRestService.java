package com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitutils;

import com.dam.proyectoa_pmdm_t2_alejandro_garcia.retrofitdata.Result;

import java.io.Serializable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestService extends Serializable {
    String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("203166-0-universidades-educacion.json")
    Call<Result> getDatosConFiltros(@Query("latitud") double latitud,
                                        @Query("longitud") double longitud,
                                        @Query("distancia") double distancia);

    @GET("203166-0-universidades-educacion.json")
    Call<Result> getDatosgrl();

}
