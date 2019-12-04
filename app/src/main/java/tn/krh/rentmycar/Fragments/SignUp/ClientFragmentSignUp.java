package tn.krh.rentmycar.Fragments.SignUp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class ClientFragmentSignUp extends Fragment {

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText username,fullname,email,password;
    Button SignupBtnUser;
    RetroClient retroClient;
    public ClientFragmentSignUp() {
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
        View view = inflater.inflate(R.layout.fragment_client_fragment_sign_up, container, false);
        //init API
        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        fullname = view.findViewById(R.id.FullnameCl);
        email = view.findViewById(R.id.EmailCl);
        password = view.findViewById(R.id.pwdCl);
        username = view.findViewById(R.id.UserNameCl);
        SignupBtnUser = view.findViewById(R.id.signUpBtnClient);
        SignupBtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") || fullname.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"please fill your informations",Toast.LENGTH_SHORT).show();
                }
                else
                    RegisterUser(email.getText().toString(),fullname.getText().toString(),password.getText().toString(),username.getText().toString());
            }
        });
        return view;
    }
    private void RegisterUser(String email, String fullname, String password,String username) {
        compositeDisposable.add(myApi.registerClient(email,fullname,password,username)
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
