/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.sr.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.duration;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int numberOfCoffees = 1;

    public void addCoffee(View view){
        if(numberOfCoffees > 99) {
            Toast.makeText(this, "U can only order at max 100 cups of coffee!", Toast.LENGTH_SHORT).show();
            return;

        }
        numberOfCoffees++;
        displayQuantity(numberOfCoffees);
    }
    public void subsCoffee(View view){
        if(numberOfCoffees <= 1) {
            Toast.makeText(this, "U cann't order less than 1 cup of coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees--;
        displayQuantity(numberOfCoffees);
    }

    private int calcPrice(int number, boolean hasWhippedCream, boolean hasChocolate){
        int price = 20;
        if(hasWhippedCream)
            price += 5;
        if(hasChocolate)
            price += 10;

        return number * price;

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox isWhippedCream = (CheckBox) findViewById(R.id.isWhippedCream);
        CheckBox isChocolate = (CheckBox) findViewById(R.id.isChocolate);
        EditText nameEditText = (EditText)findViewById(R.id.nameEditText);
        String name = nameEditText.getText().toString();
        boolean hasWhippedCream = isWhippedCream.isChecked();
        boolean hasChocolate = isChocolate.isChecked();
        String order = createMessage(name,hasWhippedCream,hasChocolate);

        String subj = "Bill details for "+ name;
        //sending mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subj);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    private String createMessage(String name, boolean hasWhippedCream, boolean hasChocolate){
        String message  = "NAME : " + name;
        message = message + "\nQuantity : " + numberOfCoffees;
        message = message + "\nAdd Whipped Cream? " + hasWhippedCream;
        message = message + "\nAdd Chocolate? " + hasChocolate;
        message = message + "\nTotal: Rs " + calcPrice(numberOfCoffees, hasWhippedCream, hasChocolate) + "\nThank you!";
        displayMessage(message);
        return message;


    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}