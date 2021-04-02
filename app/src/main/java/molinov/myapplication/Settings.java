package molinov.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;

public class Settings extends BaseActivity {
    private ToggleButton theme1, theme2, theme3;
    private MaterialButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initButtons();
        setClickListeners();
    }

    @SuppressLint("ShowToast")
    private void setClickListeners() {
        theme1.setOnClickListener(v -> {
            if (theme1.isChecked()) {
                theme2.setChecked(false);
                theme3.setChecked(false);
                setAppTheme(MyTheme1);
                Toast.makeText(getApplicationContext(), getString(R.string.apply_theme), Toast.LENGTH_SHORT);
            }
        });
        theme2.setOnClickListener(v -> {
            if (theme2.isChecked()) {
                theme1.setChecked(false);
                theme3.setChecked(false);
                setAppTheme(MyTheme2);
                Toast.makeText(getApplicationContext(), getString(R.string.apply_theme), Toast.LENGTH_SHORT);
            }
        });
        theme3.setOnClickListener(v -> {
            if (theme3.isChecked()) {
                theme1.setChecked(false);
                theme2.setChecked(false);
                setAppTheme(MyTheme3);
                Toast.makeText(getApplicationContext(), getString(R.string.apply_theme), Toast.LENGTH_SHORT);
            }
        });
        back.setOnClickListener(v -> {
            Intent result = new Intent();
            setResult(RESULT_OK, result);
            finish();
        });
    }

    private void initButtons() {
        theme1 = findViewById(R.id.theme1);
        theme2 = findViewById(R.id.theme2);
        theme3 = findViewById(R.id.theme3);
        back = findViewById(R.id.back);
    }
}
