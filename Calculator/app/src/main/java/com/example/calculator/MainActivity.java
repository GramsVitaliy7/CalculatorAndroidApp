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
    private String result = "";
    private String curOp = "";

    private boolean twoArguments=false;

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
        Button btn = (Button) f;
        display += btn.getText().toString();
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
    public void onClickOneArgumentOperator(View f)
    {
        if (display=="") return;
        Button btn = (Button) f;
        if (result != "") {     //Если уже был результат и нужно работать с ним
            String newDisplay = result;
            clearField();
            display = newDisplay;
        }
        curOp=btn.getText().toString();
        getResult();
        display=result;
        upScreen();
    }
    public void onClickOperator(View f) {
    twoArguments=true;
        if (display == "") return;
        Button btn = (Button) f;
        if (result != "") {     //Если уже был результат и нужно работать с ним
            String newDisplay = result;
            clearField();
            display = newDisplay;
        }
        if (curOp != "") {
            if (checkOperator(display.charAt(display.length() - 1))) {  //Если в поле оператор - символ на последнем месте
                display=display.replace(display.charAt(display.length() - 1), btn.getText().charAt(0));   //Меняем местами поседний символ дисплея (операцию) на новую, введенную пользователем
                upScreen();
                return;
            }
            else {
                curOp = btn.getText().toString();
                getResult();    //Считаем результат
                display = result;   //Выводим на дисплей
                result = "";
            }

        }

        display += btn.getText().toString();
        curOp = btn.getText().toString();
        upScreen();


    }

    private void clearField() {         //Функция очистки поля ввода/вывода
        display = "";
        curOp = "";
        result = "";
    }

    public void onClickClear(View f) {
        clearField();
        upScreen();
    }
private double calculateOneArgument(String x, String operation) //Функция вычислений для одноаргументных функций
{
    switch (operation) {
        case "ln(x)":
            return (Math.log(Double.valueOf(x)));
        case "lg(x)":
            return (Math.log10(Double.valueOf(x)));
        case "√":
            return (Math.sqrt(Double.valueOf(x)));
        case "sin(x)":
            return (Math.sin(Double.valueOf(x)));
        case "cos(x)":
            return (Math.cos(Double.valueOf(x)));
        case "tg(x)":
            return (Math.tan(Double.valueOf(x)));
        case "ctg(x)":
            return (1/(Math.tan(Double.valueOf(x))));
        default:
            return 0;

    }
}
    private double calculate(String x, String y, String operation) {    //Функция вычислений для двухаргументных функций
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
        if (twoArguments) {
            twoArguments = false;
            if (curOp == "") return false;
                String[] operation; //Преобразование строки в массив символов, исключая с помощью q-e текущую операцию
                operation = display.split(Pattern.quote(curOp));
                if (operation.length < 2)
                {return false;} //Если меньше двух операндов, тогда нет результата
                result = String.valueOf(calculate(operation[0], operation[1], curOp));
        }
       else{
            result = String.valueOf(calculateOneArgument(display, curOp));
        }
        return true;
    }

    public void onClickEqual(View f) {
       if (display == "")
        {
            return;
        }
       else if (!getResult())
       {
           return;
       }
        else
        {
            scr.setText(display + "\n" + String.valueOf(result));
        }
    }
}
