package com.android.example.sqldatabasebasic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.sqldatabasebasic.database.Child;
import com.android.example.sqldatabasebasic.database.Parent;

import java.util.List;

public class ExampleViewModel extends AndroidViewModel {
    private ExampleRepository repository;
    private LiveData<List<Parent>> allParents;
    private LiveData<List<Child>> allChildren;


    public ExampleViewModel(@NonNull Application application) {
        super(application);
        repository = new ExampleRepository(application);
        allParents = repository.getAllParents();
        allChildren = repository.getAllChildren();
    }

    public void insertParent(Parent parent){
        repository.insertParent(parent);
    }
    public void updateParent(Parent parent){
        repository.updateParent(parent);
    }
    public void deleteParent(Parent parent){
        repository.deleteParent(parent);
    }
    public void deleteAllParents(){
        repository.deleteAllParents();
    }
    public LiveData<List<Parent>> getAllParents() {
        return allParents;
    }

    public void insertChild(Child child){
        repository.insertChild(child);
    }
    public void updateChild(Child child){
        repository.updateChild(child);
    }
    public void deleteChild(Child child){
        repository.deleteChild(child);
    }
    public void deleteAllChildren(){
        repository.deleteAllChildren();
    }
    public LiveData<List<Child>> getAllChildren() {
        return allChildren;
    }
}
