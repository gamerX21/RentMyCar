package tn.krh.rentmycar.homeApp.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import tn.krh.rentmycar.Fragments.Client.profileEditFragment;
import tn.krh.rentmycar.R;

public class ProfileActivity extends AppCompatActivity {
    private FrameLayout profile_edit_container;
    private LinearLayout ProfileLinearLay;
    private profileEditFragment EditFrag;
    private Button EditBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_edit_container = findViewById(R.id.profile_edit_container);
        EditFrag = new profileEditFragment();
        ProfileLinearLay = findViewById(R.id.ProfileLinearLay);
        EditBtn = findViewById(R.id.EditProBtn);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileLinearLay.setVisibility(8);
                setFragment(EditFrag);
            }
        });
    }
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_edit_container, fragment).commit();
    }
}
