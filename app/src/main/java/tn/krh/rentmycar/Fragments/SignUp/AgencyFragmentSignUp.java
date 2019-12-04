package tn.krh.rentmycar.Fragments.SignUp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.Retrofit.INodeJS;
import tn.krh.rentmycar.Retrofit.RetroClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgencyFragmentSignUp extends Fragment {
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText name,email,adresse,password;
    Button SignupBtnAg;
    RetroClient retroClient;

    public AgencyFragmentSignUp() {
        // Required empty public constructor
    }
    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agency_fragment_sign_up, container, false);
        //init API
        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        //init View Com
        name = view.findViewById(R.id.NameAg);
        email = view.findViewById(R.id.EmailAg);
        password = view.findViewById(R.id.pwdAg);
        adresse = view.findViewById(R.id.AdrAg);
        SignupBtnAg = view.findViewById(R.id.signUpBtnAg);
        SignupBtnAg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || adresse.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"please fill your agency informations",Toast.LENGTH_SHORT).show();
                }
                else
                    RegisterAgency(email.getText().toString(),name.getText().toString(),password.getText().toString(),adresse.getText().toString());
            }
        });
        return view;
    }
    private void RegisterAgency(String email, String name, String password,String adresse) {
        compositeDisposable.add(myApi.registerAgency(email,name,password,adresse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                                Toast.makeText(getActivity(),""+s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

}
