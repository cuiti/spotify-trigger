package alfonso.spotifytrigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.enable_switch) Switch optionSwitch;
    private SharedPreferences sharedPref;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        optionSwitch.setChecked(sharedPref.getBoolean("enabled",false));

        if (optionSwitch.isChecked()) startService(new Intent(this,LaunchService.class));
    }

    @OnCheckedChanged(R.id.enable_switch)
    void onSwitchChanged(CompoundButton buttonView, boolean isChecked){
        sharedPref.edit().putBoolean("enabled", isChecked).apply();
        if (isChecked) startService(new Intent(this,LaunchService.class));
        else stopService(new Intent(this,LaunchService.class));
    }
}