package com.example.labor_5;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static DataBase dataBase;
    public static boolean countersRows = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = new DataBase(this);
        int tempCountRowsInDB = countRowsInDB();
        if(countersRows) {
            tempCountRowsInDB++;
            countersRows = false;
        }
        String stringCountOfRows = Integer.toString(tempCountRowsInDB);
        TextView textView = (TextView) findViewById(R.id.id_total);
        textView.setText(stringCountOfRows);

        EditText editText = (EditText) findViewById(R.id.id_search_data_in_db);
        String[] inputDataForSearchInDB = editText.getText().toString().split(" ", 2);



        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String answerString = data.getStringExtra("ANSWER_TEXT");
                    }
                        if(result.getResultCode() == Activity.RESULT_CANCELED) {
                            Intent data = result.getData();
                            String answerString = data.getStringExtra("ANSWER_TEXT");
                        }
                    }
                });

        Button submit = (Button) this.findViewById(R.id.id_button_add_data);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivityDataBase.class);
                someActivityResultLauncher.launch(intent);
            }
        });

        Button buttonSearchInDB = (Button) findViewById(R.id.id_search_data_db);
        buttonSearchInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputDataForSearchInDB[0].equals("") || !inputDataForSearchInDB[1].equals("")) {
                Cursor result = dataBase.getAllData();
                if (result.getCount() == 0) {
                    showMessage("ERROR", "NOTHING FOUND");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();

                while (result.getString(1).equals(inputDataForSearchInDB[0]) && result.getString(2).equals(inputDataForSearchInDB[1])) {
                    stringBuffer.append("ID: " + result.getString(0) + "\n");
                    stringBuffer.append("FIRSTNAME: " + result.getString(1) + "\n");
                    stringBuffer.append("NAME: " + result.getString(2) + "\n");
                    stringBuffer.append("COMMENT: " + result.getString(3) + "\n\n");
                }

                showMessage("Data", stringBuffer.toString());
            }
            }
        });

        Button buttonViewAll = (Button) findViewById(R.id.id_view_all_db);
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = dataBase.getAllData();
                if(result.getCount() == 0) {
                    showMessage("ERROR", "NOTHING FOUND");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (result.moveToNext()) {
                    stringBuffer.append("ID: " + result.getString(0) + "\n");
                    stringBuffer.append("FIRSTNAME: " + result.getString(1) + "\n");
                    stringBuffer.append("NAME: " + result.getString(2) + "\n");
                    stringBuffer.append("COMMENT: " + result.getString(3) + "\n\n");
                }

                showMessage("Data", stringBuffer.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public int countRowsInDB() {
        return dataBase.countRows();
    }
}