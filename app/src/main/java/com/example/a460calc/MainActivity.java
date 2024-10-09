package com.example.a460calc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8,
            button9;
    MaterialButton buttonMul, buttonPlus, buttonSub, buttonDiv, buttonEquals, buttonAC, buttonDot;

    /**
     * This method is called when the app is started
     * It also initializes various buttons used in the calculator
     * by assigning them their respective ID and setting click listeners.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enables edge-to-edge display
        EdgeToEdge.enable(this);
        // Set the main content view layout
        setContentView(R.layout.activity_main);

        // Handle window insets for system bars (like the status bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize text views for result and solution
        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);

        // Initialize and assign buttons
        assignID(buttonC, R.id.button_c);
        assignID(buttonBrackOpen, R.id.button_open_bracket);
        assignID(buttonBrackClose, R.id.button_close_bracket);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonDot, R.id.button_dot);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonMul, R.id.button_mul);
        assignID(buttonSub, R.id.button_sub);
        assignID(buttonDiv, R.id.button_divide);
        assignID(button0, R.id.button0);
        assignID(button1, R.id.button1);
        assignID(button2, R.id.button2);
        assignID(button3, R.id.button3);
        assignID(button4, R.id.button4);
        assignID(button5, R.id.button5);
        assignID(button6, R.id.button6);
        assignID(button7, R.id.button7);
        assignID(button8, R.id.button8);
        assignID(button9, R.id.button9);
    }

    /**
     * Helper method to assign a button by its ID and set the click listener.
     *
     * @param btn The MaterialButton to be assigned.
     * @param id  The resource ID of the button.
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * Handles the click events for the calculator buttons. Depending on the button clicked,
     *
     * @param view The view (button) that was clicked.
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalc = solutionTV.getText().toString();

        if (buttonText.equals("ac")) {
            // Clear all data
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            // Display result
            solutionTV.setText(resultTV.getText());
            return;
        }
        if (buttonText.equals("C")) {
            // Remove last character
            if (!dataToCalc.isEmpty()) {
                dataToCalc = dataToCalc.substring(0, dataToCalc.length() - 1);
            }
        } else {
            // Append new character to the solution
            dataToCalc = dataToCalc + buttonText;
        }

        // Update solution display
        solutionTV.setText(dataToCalc);

        // Get and display the result of the calculation
        String finalResult = getResults(dataToCalc);
        if (!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    /**
     * Evaluates the mathematical expression passed as a string.
     *
     * @param data The mathematical expression to evaluate.
     * @return The result of the evaluation, or "Err" if there was an error.
     */
    String getResults(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Object ans = context.evaluateString(scriptable, data, "Javascript", 1, null);
            if (ans == Context.getUndefinedValue()) {
                return "0";
            }
            return ans.toString();
        } catch (Exception e) {
            return "Err";
        } finally {
            Context.exit();
        }
    }
}