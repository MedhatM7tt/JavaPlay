package com.medhatmhtt.javaplay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {

    private int i = 2;
    private boolean addWhipped,addChocolate;
    private String userName;
    private CheckBox addWhippedCheckBox;
    private CheckBox addChocolateCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addWhippedCheckBox = (CheckBox) findViewById(R.id.addWhipped);
        addChocolateCheckBox = (CheckBox) findViewById(R.id.addChocolate);
        display(i);
        displayPrice(i * 5);
    }

    private String createOrderSummary() {
        if (i > 0) {
            double finalPrice;
            if (addWhipped == true&&addChocolate==false) {
                finalPrice = i * 5.5;
                return "Name : " + userName + "\nQuantity : " + i + "\nWith Whipped Cream \nThe total price = " + finalPrice + " $\nThank you for trust ! ^_^ ";
            }
            else if (addWhipped == false&&addChocolate==true) {
                finalPrice = i * 6.5;
                return "Name : " + userName + "\nQuantity : " + i + "\nWith Chocolate \nThe total price = " + finalPrice + " $\nThank you for trust ! ^_^ ";
            }
            else if (addWhipped == true&&addChocolate==true) {
                finalPrice = i * 7;
                return "Name : " + userName + "\nQuantity : " + i + "\nWith Chocolate and Whipped Cream\nThe total price = " + finalPrice + " $\nThank you for trust ! ^_^ ";
            }

            else {
                finalPrice = i * 5;
                return "Name : " + userName + "\nQuantity : " + i + "\nThe total price = " + finalPrice + " $\nThank you for trust ! ^_^ ";
            }
        } else {
            return "You haven't determine the quantity yet !";
        }
    }

    public void submitOrder(View view) {
        addWhipped = addWhippedCheckBox.isChecked();
        addChocolate = addChocolateCheckBox.isChecked();
        EditText userNameText = (EditText) findViewById(R.id.UserName);
        userName = userNameText.getText().toString();
        displayFinal();
    }
    public void composeMessage(View view){
        addWhipped = addWhippedCheckBox.isChecked();
        addChocolate = addChocolateCheckBox.isChecked();
        EditText userNameText = (EditText) findViewById(R.id.UserName);
        userName = userNameText.getText().toString();
        if(!userName.equals("")&&i!=0) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"medhat0103932@sci.asu.edu.eg"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "JavaCode");
            intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary());
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        else {
            if(userName.equals(""))
                displayFinal();
            else
                displayError();
        }
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.Quantity);
        quantityTextView.setText("" + number);
    }

    private void displayFinal() {
        TextView finalTextView = (TextView) findViewById(R.id.finalMessage);

        if (userName.equals("")) {
            finalTextView.setText("Please Enter Ur Name ! ");
        }
        else{
            if(i>0)
                nullEditText();
            finalTextView.setText(createOrderSummary());
            i = 0;
            display(i);
            displayPrice(i);
        }
    }

    private void nullEditText() {
        EditText userNameText = (EditText) findViewById(R.id.UserName);
        userNameText.setText("");
        addWhippedCheckBox.setChecked(false);
        addChocolateCheckBox.setChecked(false);
    }
    public void changeState(View view){
        displayPrice(i*5);
    }
    private void displayPrice(int number) {
        double Total=number;
        TextView priceTextView = (TextView) findViewById(R.id.price);
        if(addChocolateCheckBox.isChecked())
            Total=Total+i*1.5;
        if(addWhippedCheckBox.isChecked())
            Total=Total+i*.5;
        priceTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(Total));
    }

    private void displayError() {
        TextView priceTextView = (TextView) findViewById(R.id.price);
        priceTextView.setText("Please order a number of coffees");
    }

    public void increament(View view) {
        display(++i);
        displayPrice(i * 5);
    }

    public void decreament(View view) {
        --i;
        if (i >= 0) {
            display(i);
            displayPrice(i * 5);
        } else {
            i = 0;
            displayError();
        }
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price);
        priceTextView.setText(message);
    }
}
