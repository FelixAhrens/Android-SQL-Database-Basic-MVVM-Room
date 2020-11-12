package com.android.example.sqldatabasebasic.addItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.sqldatabasebasic.R;

public class AddEditParentActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.android.example.sqldatabasebasic.addItems.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.android.example.sqldatabasebasic.addItems.EXTRA_DESCRIPTION";
    public static final String EXTRA_ID =
            "com.android.example.sqldatabasebasic.addItems.EXTRA_ID";
    public static final String EXTRA_NUMBER =
            "com.android.example.sqldatabasebasic.addItems.EXTRA_NUMBER";
    public static final String EXTRA_NUMBER_OF_CHILDREN =
            "com.android.example.sqldatabasebasic.addItems.EXTRA_NUMBER_OF_CHILDREN";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPicker;
    private TextView textViewAddChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);

        editTextTitle = findViewById(R.id.edit_text_title_parent);
        editTextDescription = findViewById(R.id.edit_text_description_parent);
        numberPicker = findViewById(R.id.number_picker_children);
        textViewAddChildren = findViewById(R.id.text_view_add_children);

        numberPicker.setVisibility(View.VISIBLE);
        textViewAddChildren.setVisibility(View.VISIBLE);


        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Parent Item");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setVisibility(View.GONE);
            textViewAddChildren.setVisibility(View.GONE);
        } else {
            setTitle("Add Parent Item");
        }

    }

    private void saveItem() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int numberOfChildren = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_NUMBER_OF_CHILDREN, numberOfChildren);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
            data.putExtra(EXTRA_NUMBER, getIntent().getIntExtra(EXTRA_NUMBER, -1));
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}