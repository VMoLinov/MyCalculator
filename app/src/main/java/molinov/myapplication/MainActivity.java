package molinov.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    TextView result, calculate, historyText;
    Button nil, one, two, three, four, five, six, seven, eight, nine, percent, split,
            compute, minus, plus, dot, c, equal, square;
    ImageButton historyButton;
    ArrayList<Button> buttons;
    ScrollView historyLayout;
    Fields fields = new Fields();
    String fieldsKey = "key";
    SwitchMaterial themeSwitch;
    Resources.Theme dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();
        setButtonsOnClickListeners();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        fields.setMain(calculate.getText().toString());
        fields.setSlave(result.getText().toString());
        outState.putParcelable(fieldsKey, fields);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fields = savedInstanceState.getParcelable(fieldsKey);
        calculate.setText(fields.getMain());
        result.setText(fields.getSlave());
    }

    private void setButtonsOnClickListeners() {
        String[] numbers = getResources().getStringArray(R.array.numbers);
        for (int i = 0; i < numbers.length; i++) {
            int k = i;
            buttons.get(i).setOnClickListener(v -> {
                calculate.append(numbers[k]);
                result.setText(String.valueOf(proceedOperation(calculate.getText().toString())));
            });
        }
        String[] symbols = getResources().getStringArray(R.array.symbols);
        int index = numbers.length;
        for (int i = index; i < index + symbols.length; i++) {
            int k = i - index;
            buttons.get(i).setOnClickListener(v -> {
                String calcString = calculate.getText().toString();
                if (!calcString.isEmpty() && !calculateFieldEndsWithSymbols(calcString)) {
                    calculate.append(symbols[k]);
                } else if (!calcString.isEmpty() && calculateFieldEndsWithSymbols(calcString)) {
                    StringBuilder s = new StringBuilder(calcString);
                    s.deleteCharAt(s.length() - 1);
                    s.append(symbols[k]);
                    calculate.setText(String.valueOf(s));
                }
            });
        }
        dot.setOnClickListener(v -> {
            String calcString = calculate.getText().toString();
            String[] temp = calcString.split(getString(R.string.not_a_number));
            if (!calcString.isEmpty() && !temp[temp.length - 1].contains(getString(R.string.dot))) {
                calculate.append(getString(R.string.dot));
            }
        });
        c.setOnClickListener(v -> {
            calculate.setText(null);
            result.setText(null);
        });
        equal.setOnClickListener(v -> {
            String resString = result.getText().toString();
            fields.addHistory(calculate.getText().toString() + " = " + resString);
            calculate.setText(resString);
            result.setText(null);
        });
        historyButton.setOnClickListener(v -> {
            historyText.setText(fields.getHistory());
            if (historyLayout.getVisibility() != View.VISIBLE) {
                historyLayout.setVisibility(View.VISIBLE);
            } else {
                historyLayout.setVisibility(View.INVISIBLE);
            }
        });
        square.setOnClickListener(v -> {
            String calcString = calculate.getText().toString();
            if (!calcString.isEmpty() && !calculateFieldEndsWithSymbols(calcString)) {
                String score = String.valueOf(Math.sqrt(proceedOperation(calcString)));
                fields.addHistory(getString(R.string.square) + "(" + calcString + ") = " + score);
                result.setText(score);
            }
        });
        percent.setOnClickListener(v -> {
            String s = calculate.getText().toString();
            if (!s.isEmpty() && (s.contains(getString(R.string.plus)) && s.length() - 1 > s.indexOf(getString(R.string.plus))
                    || (s.contains(getString(R.string.minus)) && s.length() - 1 > s.indexOf(getString(R.string.minus))))) {
                String score = String.valueOf(proceedPercent(s)); //Check
                fields.addHistory(s + getString(R.string.percent) + " = " + score);
                result.setText(score);
            }
        });
        themeSwitch.setOnClickListener(v -> {
            if (themeSwitch.isChecked()) {
            }
        });
    }

    private boolean calculateFieldEndsWithSymbols(String s) {
        return s.endsWith(getString(R.string.split))
                || s.endsWith(getString(R.string.compute))
                || s.endsWith(getString(R.string.plus))
                || s.endsWith(getString(R.string.minus));
    }

    private float proceedPercent(String s) {
        String[] temp = s.split(getString(R.string.not_a_number));
        float[] values = new float[temp.length];
        for (int i = 0; i < temp.length; i++) {
            values[i] = Float.parseFloat(temp[i]);
        }
        temp = s.split(getString(R.string.number));
        switch (temp[1]) { //Hardcoded. Need to fix
            case "+": {
                return values[0] * ((values[1] / 100) + 1);
            }
            case "-": {
                return values[0] * (1 - (values[1] / 100));
            }
            default:
                return 0;
        }
    }

    private float proceedOperation(String operation) {
        String[] temp = operation.split(getString(R.string.not_a_number));
        float[] values = new float[temp.length];
        for (int i = 0; i < temp.length; i++) {
            values[i] = Float.parseFloat(temp[i]);
        }
        temp = operation.split(getString(R.string.number));
        float result = values[0];
        for (int i = 1; i < values.length; i++) {
            switch (temp[i]) {
                case "×": {
                    result *= values[i];
                    continue;
                }
                case "÷": {
                    result /= values[i];
                    continue;
                }
                case "+": {
                    result += values[i];
                    continue;
                }
                case "-": {
                    result -= values[i];
                    continue;
                }
                default:
            }
        }
        return result;
    }

    private void initializeButtons() {
        result = findViewById(R.id.result);
        calculate = findViewById(R.id.calculate);
        historyText = findViewById(R.id.historyText);
        nil = findViewById(R.id.nil);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        c = findViewById(R.id.c);
        square = findViewById(R.id.square);
        percent = findViewById(R.id.percent);
        split = findViewById(R.id.split);
        compute = findViewById(R.id.compute);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        equal = findViewById(R.id.equal);
        dot = findViewById(R.id.dot);
        historyButton = findViewById(R.id.historyButton);
        historyLayout = findViewById(R.id.historyLayout);
        themeSwitch = findViewById(R.id.themeSwitch);
        buttons = new ArrayList<>(Arrays.asList(nil, one, two, three, four, five,
                six, seven, eight, nine, percent, split, compute, minus, plus, dot));
    }
}
