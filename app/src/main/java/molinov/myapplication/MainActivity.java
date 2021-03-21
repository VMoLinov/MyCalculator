package molinov.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    TextView result, calculate, historyText;
    Button nil, one, two, three, four, five, six, seven, eight, nine, square, percent, split,
            compute, minus, plus, dot, c, equal;
    ImageButton historyButton;
    ArrayList<Button> buttons;
    ArrayList<String> values;
    String operation;
    Fields fields = new Fields();
    String fieldsKey = "key";


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
        for (int i = 0; i < buttons.size(); i++) {
            int k = i;
            buttons.get(i).setOnClickListener(v -> {
                calculate.append(values.get(k));
                operation = calculate.getText().toString();
                result.setText(String.valueOf(proceedOperation(operation)));
            });
        }
        c.setOnClickListener(v -> calculate.setText(""));
        equal.setOnClickListener(v -> {
            fields.addHistory(calculate.getText().toString() + " = " + result.getText().toString());
            calculate.setText(result.getText().toString());
            result.setText("");
        });
        historyButton.setOnClickListener(v -> {
            historyText.setText(fields.getHistory());
            if (historyText.getVisibility() != View.VISIBLE) {
                historyText.setVisibility(View.VISIBLE);
            } else {
                historyText.setVisibility(View.INVISIBLE);
            }
        });
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
        values = new ArrayList<>(Arrays.asList(
                getString(R.string.nil),
                getString(R.string.one),
                getString(R.string.two),
                getString(R.string.three),
                getString(R.string.four),
                getString(R.string.five),
                getString(R.string.six),
                getString(R.string.seven),
                getString(R.string.eight),
                getString(R.string.nine),
                getString(R.string.square),
                getString(R.string.percent),
                getString(R.string.split),
                getString(R.string.compute),
                getString(R.string.minus),
                getString(R.string.plus),
                getString(R.string.dot)));
        buttons = new ArrayList<>(Arrays.asList(nil, one, two, three, four, five,
                six, seven, eight, nine, square, percent, split, compute, minus, plus, dot));
    }
}
