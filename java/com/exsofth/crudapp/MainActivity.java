package com.exsofth.crudapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //The Database instance here
    DatabaseHelper myDb;
    EditText editNames, editEmail, editMarks, editID;
    Button save, show, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SQLite Database instance ivoqued here
        myDb = new DatabaseHelper(this);

        //Text fields
        editNames = (EditText) findViewById(R.id.names);
        editEmail  = (EditText) findViewById(R.id.email);
        editMarks = (EditText) findViewById(R.id.marks);
        editID = (EditText) findViewById(R.id.id);

        //saving button
        save = (Button) findViewById(R.id.save);
        //view all button
        show = (Button) findViewById(R.id.show);
        //update
        update = (Button) findViewById(R.id.update);
        //delete
        delete = (Button) findViewById(R.id.delete);

        //add data
        createData();
        //view data
        showAllData();
        //update data
        updateData();
        //delete
        deleteData();
    }

    public void createData(){
        save.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                  boolean isInserted =   myDb.insertData(editNames.getText().toString(),
                            editEmail.getText().toString(),
                            editMarks.getText().toString());
                  if(isInserted)
                      //Show a confirmation message
                      Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                  else
                      //Or a fail one here
                      Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
        );
    }

    public void showAllData(){
        show.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();
                        if(res.getCount()==0){
                            //Show message
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID : "+res.getString(0)+"\n");
                            buffer.append("Names : "+res.getString(1)+"\n");
                            buffer.append("Email : "+res.getString(2)+"\n");
                            buffer.append("Marks : "+res.getString(3)+"%"+"\n");
                            buffer.append("\n");

                        }
                        //Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateData(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] dataToUpdate = {
                                editNames.getText().toString(),
                                editEmail.getText().toString(),
                                editMarks.getText().toString()
                        };
                        boolean isUpdated = myDb.updateData(editID.getText().toString(), dataToUpdate);

                        if (isUpdated){
                            Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Data no updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void deleteData(){
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRow = myDb.deleteData(editID.getText().toString());

                        if (deletedRow > 0){
                            Toast.makeText(MainActivity.this, "Student deleted", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this, "No row deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }



    //Show Messages
    public void showMessage (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setTitle(title).setMessage(message).setIcon(R.drawable.ic_launcher_background);
        builder.show();
    }
}
