package tn.krh.rentmycar.main.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import tn.krh.rentmycar.Adapters.SignUp.SpinnerSignUpAdapter;
import tn.krh.rentmycar.Fragments.SignUp.AgencyFragmentSignUp;
import tn.krh.rentmycar.Fragments.SignUp.ClientFragmentSignUp;
import tn.krh.rentmycar.R;
import tn.krh.rentmycar.model.SpinnerItem;

public class SpinnerSignUpActivity extends AppCompatActivity {
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    AgencyFragmentSignUp agencyFragmentSignUp;
    ClientFragmentSignUp clientFragmentSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_sign_up);
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select"));
        list.add(new SpinnerItem("Client",R.drawable.rent));
        list.add(new SpinnerItem("Agency",R.drawable.share));
        SpinnerSignUpAdapter adapterS = new SpinnerSignUpAdapter(this,R.layout.spinner_layout,R.id.SpinnerTxt,list);
        Spinner sp = findViewById(R.id.SpinnerSignUp);
        sp.setAdapter(adapterS);
        agencyFragmentSignUp = new AgencyFragmentSignUp();
        clientFragmentSignUp = new ClientFragmentSignUp();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem SelectedtYPE = (SpinnerItem)  parent.getItemAtPosition(position);
                String name = SelectedtYPE.getName();
                if(position > 0)  GetSelectedType(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public  void  GetSelectedType(String type){
      if(type.equals("Agency"))  showFragment(agencyFragmentSignUp);
      else showFragment(clientFragmentSignUp);
    }
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_signup, fragment).commit();
    }
}
