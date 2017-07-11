package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView scr;
    private String display = "";
    private String curOp = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scr = (TextView) findViewById(R.id.inputField);
        scr.setText(display);
    }

    private void upScreen() {
        scr.setText(display);
    }

    public void onClickNumber(View f) {

        if (result != "") {
            clearField();
            upScreen();
        }
        Button b = (Button) f;
        display += b.getText();
        upScreen();
    }

    private boolean checkOperator(char op) {
        switch (op) {
            case '^':
            case '+':
            case '-':
            case '*':
            case '/':

                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View f) {
        if (display == "") return;
        Button b = (Button) f;
        if (result != "") {
            String newDisplay = result;
            clearField();
            display = newDisplay;
        }
        if (curOp != "") {
            if (checkOperator(display.charAt(display.length() - 1))) {  //Если в поле оператор - символ на последнем месте
                display=display.replace(display.charAt(display.length() - 1), b.getText().charAt(0));   //Меняем местами поседний символ дисплея (операцию) на новую, введенную пользователем
                upScreen();
                return;
            }
            else {
                getResult();
                display = result;
                result = "";
            }
            curOp = b.getText().toString();
        }

        display += b.getText();
        curOp = b.getText().toString();
        upScreen();
    }

    private void clearField() {
        display = "";
        curOp = "";
        result = "";
        upScreen();

    }

    public void onClickClear(View f) {
        clearField();
        upScreen();
    }

    private double calculate(String x, String y, String operation) {
        switch (operation) {
            case "^":
                return (Math.pow(Double.valueOf(x), Double.valueOf(y)));
            case "+":
                return (Double.valueOf(x) + Double.valueOf(y));
            case "-":
                return (Double.valueOf(x) - Double.valueOf(y));
            case "*":
                return (Double.valueOf(x) * Double.valueOf(y));
            case "/":
                return (Double.valueOf(x) / Double.valueOf(y));
            default:
                return 0;

        }
    }

    private boolean getResult() {
        if (curOp == "") return false;
        String[] operation = display.split(Pattern.quote(curOp)); //Преобразование строки в массив символов, исключая с помощью q-e текущую операцию
        if (operation.length < 2) return false; //Если меньше двух операндов, тогда нет результата
        result = String.valueOf(calculate(operation[0], operation[1], curOp));
        return true;
    }

    public void onClickEqual(View f) {
        if (display == "")
        {return;}
        else if (!getResult())
        {return;}
        else
        {scr.setText(display + "\n" + String.valueOf(result));}
    }
}
