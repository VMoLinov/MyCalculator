package molinov.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    TextView result, calculate, historyText;
    Button nil, one, two, three, four, five, six, seven, eight, nine, percent, split,
            compute, minus, plus, dot, c, equal, square;
    ImageButton historyButton;
    ArrayList<Button> buttons;
    ArrayList<String> values;
    ScrollView historyLayout;
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
        String[] numbers = getResources().getStringArray(R.array.numbers);
        for (int i = 0; i < numbers.length; i++) {
            int k = i;
            buttons.get(i).setOnClickListener(v -> {
                calculate.append(numbers[k]);
                operation = calculate.getText().toString();
                result.setText(String.valueOf(proceedOperation(operation)));
            });
        }
        String[] symbols = getResources().getStringArray(R.array.symbols);
        int index = numbers.length;
        for (int i = index; i < index + symbols.length; i++) {
            int k = i - index;
            buttons.get(i).setOnClickListener(v -> {
                String calcString = calculate.getText().toString();
                if (!calcString.isEmpty() && !getString(R.string.not_a_number).endsWith(calcString)) {
                    calculate.append(symbols[k]);
                }
            });
        }
        c.setOnClickListener(v -> calculate.setText(null));
        equal.setOnClickListener(v -> {
            fields.addHistory(calculate.getText().toString() + " = " + result.getText().toString());
            calculate.setText(result.getText().toString());
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
            if (!calculate.getText().toString().isEmpty()) {
                operation = calculate.getText().toString();
                String score = String.valueOf(Math.sqrt(proceedOperation(operation)));
                fields.addHistory(getString(R.string.square) + "(" + operation + ") = " + score);
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
                getString(R.string.percent),
                getString(R.string.split),
                getString(R.string.compute),
                getString(R.string.minus),
                getString(R.string.plus),
                getString(R.string.dot)));
        buttons = new ArrayList<>(Arrays.asList(nil, one, two, three, four, five,
                six, seven, eight, nine, percent, split, compute, minus, plus, dot));
    }
}
