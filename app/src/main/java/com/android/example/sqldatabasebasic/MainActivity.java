package com.android.example.sqldatabasebasic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.sqldatabasebasic.adapter.ParentAdapter;
import com.android.example.sqldatabasebasic.addItems.AddEditParentActivity;
import com.android.example.sqldatabasebasic.database.Child;
import com.android.example.sqldatabasebasic.database.Parent;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //intent
    public static final int ADD_PARENT_REQUEST = 1;
    public static final int EDIT_PARENT_REQUEST = 2;

    //switch layouts
    private boolean isMenuChecked = false;

    //adapter & viewmodel
    private ParentAdapter parentAdapter;
    private ExampleViewModel exampleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doRecyclerViewStuff();

    }

    private void doRecyclerViewStuff() {
        RecyclerView recyclerViewParent = findViewById(R.id.recycler_view_parent);
        recyclerViewParent.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewParent.setHasFixedSize(true);

        exampleViewModel = ViewModelProviders.of(this).get(ExampleViewModel.class);

        parentAdapter = new ParentAdapter(this, isMenuChecked);
        recyclerViewParent.setAdapter(parentAdapter);

        //update RecyclerView
        exampleViewModel.getAllParents().observe(this, new Observer<List<Parent>>() {
            @Override
            public void onChanged(List<Parent> parents) {
                parentAdapter.setParents(parents);
            }
        });
        exampleViewModel.getAllChildren().observe((LifecycleOwner) this, new Observer<List<Child>>() {
            @Override
            public void onChanged(List<Child> children) {
                parentAdapter.setChildren(children);
            }
        });

        //update item
        parentAdapter.setOnItemClickListener(new ParentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Parent parent) {
                Intent intent = new Intent(MainActivity.this, AddEditParentActivity.class);
                intent.putExtra(AddEditParentActivity.EXTRA_TITLE, parent.getTitle());
                intent.putExtra(AddEditParentActivity.EXTRA_DESCRIPTION, parent.getDescription());
                intent.putExtra(AddEditParentActivity.EXTRA_ID, parent.getId());
                intent.putExtra(AddEditParentActivity.EXTRA_NUMBER, parent.getNumber());
                startActivityForResult(intent, EDIT_PARENT_REQUEST);
            }
        });

        //customized floating action button
        SpeedDialView speedDialView = findViewById(R.id.speedDial);
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.add_parent, R.drawable.ic_person)
                        .setLabel("add items")
                        .create());
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.delete_all, R.drawable.ic_delete)
                        .setFabBackgroundColor(Color.RED)
                        .setLabel("delete all items")
                        .create());

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                Intent intent = new Intent(MainActivity.this, AddEditParentActivity.class);
                switch (speedDialActionItem.getId()) {
                    case R.id.add_parent:
                        Intent intentParent = new Intent(MainActivity.this, AddEditParentActivity.class);
                        startActivityForResult(intentParent, ADD_PARENT_REQUEST);
                        return false; // true to keep the Speed Dial open
                    case R.id.delete_all:
                        exampleViewModel.deleteAllChildren();
                        exampleViewModel.deleteAllParents();
                        Toast.makeText(MainActivity.this, "All items deleted", Toast.LENGTH_SHORT).show();
                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //update db new item
        if (requestCode == ADD_PARENT_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditParentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditParentActivity.EXTRA_DESCRIPTION);
            int  numberOfChildren = data.getIntExtra(AddEditParentActivity.EXTRA_NUMBER_OF_CHILDREN, 0);

            int number = exampleViewModel.getAllParents().getValue().size() + 1;

            //create an write parent item
            Parent parent = new Parent(title, description, number);
            exampleViewModel.insertParent(parent);

            //create an write children items
            for (int i = 1; i <= numberOfChildren; i++) {
                Child child = new Child("Child", i, number);
                exampleViewModel.insertChild(child);
            }

            Toast.makeText(this, "Parent saved", Toast.LENGTH_SHORT).show();
        }
        // update db edit item
        else if (requestCode == EDIT_PARENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditParentActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Parent can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditParentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditParentActivity.EXTRA_DESCRIPTION);
            int number = data.getIntExtra(AddEditParentActivity.EXTRA_NUMBER, -1);
            int  numberOfChildren = data.getIntExtra(AddEditParentActivity.EXTRA_NUMBER_OF_CHILDREN, 0);

            //update parent item
            Parent parent = new Parent(title, description, number);
            parent.setId(id);
            exampleViewModel.updateParent(parent);

            Toast.makeText(this, "Parent updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Parent not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //Menu switch layouts
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.switch_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_layout:
                isMenuChecked = !item.isChecked();
                item.setChecked(isMenuChecked);
                if (isMenuChecked) {
                    item.setIcon(R.drawable.ic_view_grid);
                } else {
                    item.setIcon(R.drawable.ic_view_horizontal);
                }
                doRecyclerViewStuff();
                return true;
            default:
                return false;
        }
    }

}