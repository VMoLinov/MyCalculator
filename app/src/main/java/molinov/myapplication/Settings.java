package molinov.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;

public class Settings extends AppCompatActivity {
    private ToggleButton theme1, theme2, theme3;
    private MaterialButton back;
    private static final int MyTheme1 = 1;
    private static final int MyTheme2 = 2;
    private static final int MyTheme3 = 3;
    private static final String NameSharedPreference = "LOGIN";
    private static final String AppTheme = "APP_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyTheme1));
        setContentView(R.layout.activity_settings);
        initButtons();
        setClickListeners();
    }

    @SuppressLint("ShowToast")
    private void setClickListeners() {
        theme1.setOnClickListener(v -> {
            theme2.setChecked(false);
            theme3.setChecked(false);
            setAppTheme(MyTheme1);
            Toast.makeText(getApplicationContext(), getString(R.string.apply_theme), Toast.LENGTH_SHORT);
        });
        theme2.setOnClickListener(v -> {
            theme1.setChecked(false);
            theme3.setChecked(false);
            setAppTheme(MyTheme2);
            Toast.makeText(getApplicationContext(), getString(R.string.apply_theme), Toast.LENGTH_SHORT);
        });
        theme3.setOnClickListener(v -> {
            theme1.setChecked(false);
            theme2.setChecked(false);
            setAppTheme(MyTheme3);
            Toast.makeText(getApplicationContext(), getString(R.string.apply_theme), Toast.LENGTH_SHORT);
        });
    }

    private void initButtons() {
        theme1 = findViewById(R.id.theme1);
        theme2 = findViewById(R.id.theme2);
        theme3 = findViewById(R.id.theme3);
        back = findViewById(R.id.back);
    }

    private int getCodeStyleInt(int codeStyle) {
        if (codeStyle == MyTheme1) return R.style.MyTheme1;
        else if (codeStyle == MyTheme2) return R.style.MyTheme2;
        else return R.style.MyTheme3;
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
        recreate();
    }

    protected int getAppTheme(int codeStyle) {
        return getCodeStyleInt(getCodeStyle(codeStyle));
    }
}
