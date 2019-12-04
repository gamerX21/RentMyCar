package tn.krh.rentmycar.Retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tn.krh.rentmycar.entity.Agence;
import tn.krh.rentmycar.entity.Car;
import tn.krh.rentmycar.model.Client;

public interface INodeJS {
    @POST("registerClient")
    @FormUrlEncoded
    Observable<String> registerClient(@Field("email") String email,
                                      @Field("fullname") String fullname,
                                      @Field("password") String password,
                                      @Field("username") String username);
    @POST("registerAgency")
    @FormUrlEncoded
    Observable<String> registerAgency(@Field("email") String email,
                                      @Field("name") String name,
                                      @Field("password") String password,
                                      @Field("adresse") String adresse);
    @POST("loginUser")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,@Field("pwd") String password);

    //AddCar Agence
    @POST("AddCar/{id}")
    @FormUrlEncoded
    Observable<String> AddCar(@Field("type") String type,
                                      @Field("model") String model,
                                      @Field("price") int price,
                                      @Field("DispoAg") String DispoAg,
                                      @Path("id") int agence_id );

    @GET("GetClient/{email}")
    Call<Client> getClient(@Path("email") String email);

    @GET("GetAgency/{email}")
    Call<Agence> getAgency(@Path("email") String email);

    @GET("GetCarsAgency/{id}")
    Call<List<Car>> getCarsAgencyByIdList(@Path("id") int id);

    @GET("GetCarsAgency/{id}")
    Call<Car> getCarsAgencyById(@Path("id") int id);

    @PUT("UpdateCar/{id}")
    @FormUrlEncoded
    Call<Car> UpdateCar(@Path("id") int id,
                              @Field("type") String type,
                              @Field("model") String model,
                              @Field("price") int price,
                              @Field("DispoAg") String DispoAg);

    @DELETE("DeleteCar/{id}")
    Call<Car> deleteCar(@Path("id") int id);
}
