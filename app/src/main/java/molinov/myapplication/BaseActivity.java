package molinov.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    static final int MyTheme1 = 1;
    static final int MyTheme2 = 2;
    static final int MyTheme3 = 3;
    private static final String NameSharedPreference = "LOGIN";
    private static final String AppTheme = "APP_THEME";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyTheme1));
    }

    private int getCodeStyleInt(int codeStyle) {
        switch (codeStyle) {
            case MyTheme2:
                return R.style.MyTheme2;
            case MyTheme3:
                return R.style.MyTheme3;
            default:
                return R.style.MyTheme1;
        }
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    protected void setAppTheme(int codeStyle) {
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
