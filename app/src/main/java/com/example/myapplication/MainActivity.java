package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText num1;
    private EditText num2;
    private TextView textViewResult;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        textViewResult = findViewById(R.id.textViewResult);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);

        buttonAdd.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("CalculatorPrefs", MODE_PRIVATE);
        String lastResult = sharedPreferences.getString("lastResult", "");
        textViewResult.setText(lastResult);
    }

    @Override
    public void onClick(View v)
    {
        String operator = "";

        // Use if-else statements to determine the operator based on the button clicked
        if (v.getId() == R.id.buttonAdd)
        {
            operator = "+";
        } else if (v.getId() == R.id.buttonSubtract)
        {
            operator = "-";
        } else if (v.getId() == R.id.buttonMultiply)
        {
            operator = "*";
        } else if (v.getId() == R.id.buttonDivide)
        {
            operator = "/";
        }

        calculate(operator);
    }

    private void calculate(String operator)
    {
        String input1 = num1.getText().toString();
        String input2 = num2.getText().toString();

        if (input1.isEmpty() || input2.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Please enter two numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double number1 = Double.parseDouble(input1);
        double number2 = Double.parseDouble(input2);
        double result = 0;

        switch (operator)
        {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "*":
                result = number1 * number2;
                break;
            case "/":
                if (number2 == 0)
                {
                    Toast.makeText(MainActivity.this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    result = number1 / number2;
                }
                break;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastResult", "Result: " + result);
        editor.apply();

        textViewResult.setText("Result: " + result);
    }
}
