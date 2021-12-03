package com.example.labor_5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivityDataBase extends AppCompatActivity {
    private EditText editFirstName, editName, editComment;
    private Button buttonInsertData, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_database);

        editFirstName = (EditText) findViewById(R.id.id_search_data_in_db);
        editName = (EditText) findViewById(R.id.EditText2);
        editComment = (EditText) findViewById(R.id.EditText3);
        buttonInsertData = (Button) findViewById(R.id.id_button_insert_data);

        if(addData())
            MainActivity.countersRows = true;

        if(addData()) MainActivity.dataBase.countRows();

        buttonCancel = (Button)this.findViewById(R.id.id_button_cancel_insert_data);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                EditActivityDataBase.this.setResult(Activity.RESULT_CANCELED, returnIntent);
                EditActivityDataBase.this.finish();
            }
        });
    }

    public boolean addData() {
        buttonInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = MainActivity.dataBase.insertData(editFirstName.getText().toString(), editName.getText().toString(), editComment.getText().toString());
                if (isInserted)
                    Toast.makeText(EditActivityDataBase.this, "Data inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EditActivityDataBase.this, "Data not inserted", Toast.LENGTH_LONG).show();
            }
        });
        return true;
    }
}
