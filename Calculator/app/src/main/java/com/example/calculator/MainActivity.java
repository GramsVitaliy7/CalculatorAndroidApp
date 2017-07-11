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
        scr = (TextView)findViewById(R.id.inputField);
        scr.setText(display);
    }
    private void upScreen()
    {
        scr.setText(display);
    }
    public void onClickNumber(View f) {
    if (result!= "")
    {
        clearField();
        upScreen();
    }
        Button b = (Button) f;
        display += b.getText();
        upScreen();
    }
    public void onClickOperator(View f)
    {
        if (result != "") {
            display = result;
            result="";
        }
        Button b = (Button) f;
        display += b.getText();
        curOp = b.getText().toString();
        upScreen();
    }
    private void clearField()
    {
        display = "";
        curOp = "";
        result = "";
        upScreen();

    }
    public void onClickClear(View f)
    {
        clearField();
        upScreen();
    }
    private double calculate(String x, String y, String operation)
    {
        switch(operation)
        {
            case "+" : return (Double.valueOf(x)+Double.valueOf(y));
            case "-" : return (Double.valueOf(x)-Double.valueOf(y));
            case "*" : return (Double.valueOf(x)*Double.valueOf(y));
            case "/" : return (Double.valueOf(x)/Double.valueOf(y));
            default: return 0;

        }
    }
    public void onClickEqual(View f)
    {
        String[] operation = display.split(Pattern.quote(curOp)); //Преобразование строки в массив символов
        if (operation.length < 2) return;
        result = String.valueOf(calculate(operation[0], operation[1], curOp ));
        scr.setText(display + "\n" + String.valueOf(result));
    }

}
