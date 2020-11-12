package com.android.example.sqldatabasebasic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.android.example.sqldatabasebasic.database.Child;
import com.android.example.sqldatabasebasic.database.ChildDao;
import com.android.example.sqldatabasebasic.database.ExampleDatabase;
import com.android.example.sqldatabasebasic.database.Parent;
import com.android.example.sqldatabasebasic.database.ParentDao;

import java.util.List;

public class ExampleRepository {

    private ParentDao parentDao;
    private ChildDao childDao;

    private LiveData<List<Parent>> allParents;
    private LiveData<List<Child>> allChildren;

    public ExampleRepository(Application application) {
        ExampleDatabase database = ExampleDatabase.getInstance(application);
        parentDao = database.parentDao();
        childDao = database.childDao();

        allParents = parentDao.getAllParents();
        allChildren = childDao.getAllChildren();
    }

    public void insertParent(Parent parent){
        new InsertParentAsyncTask(parentDao).execute(parent);

    }

    public void updateParent(Parent parent){
        new UpdateParentAsyncTask(parentDao).execute(parent);
    }

    public void deleteParent(Parent parent){
        new DeleteParentAsyncTask(parentDao).execute(parent);
    }

    public void deleteAllParents(){
        new DeleteAllParentsParentAsyncTask(parentDao).execute();

    }

    public LiveData<List<Parent>> getAllParents() {
        return allParents;
    }

    private static class InsertParentAsyncTask extends AsyncTask<Parent, Void, Void>{
        private ParentDao parentDao;

        private InsertParentAsyncTask(ParentDao parentDao){
            this.parentDao = parentDao;
        }

        @Override
        protected Void doInBackground(Parent... parents) {
            parentDao.insert(parents[0]);
            return null;
        }
    }
    private static class UpdateParentAsyncTask extends AsyncTask<Parent, Void, Void>{
        private ParentDao parentDao;

        private UpdateParentAsyncTask(ParentDao parentDao){
            this.parentDao = parentDao;
        }

        @Override
        protected Void doInBackground(Parent... parents) {
            parentDao.update(parents[0]);
            return null;
        }
    }
    private static class DeleteParentAsyncTask extends AsyncTask<Parent, Void, Void>{
        private ParentDao parentDao;

        private DeleteParentAsyncTask(ParentDao parentDao){
            this.parentDao = parentDao;
        }

        @Override
        protected Void doInBackground(Parent... parents) {
            parentDao.delete(parents[0]);
            return null;
        }
    }
    private static class DeleteAllParentsParentAsyncTask extends AsyncTask<Void, Void, Void>{
        private ParentDao parentDao;

        private DeleteAllParentsParentAsyncTask(ParentDao parentDao){
            this.parentDao = parentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            parentDao.deleteAllParents();
            return null;
        }
    }

    public void insertChild(Child child){
        new InsertChildAsyncTask(childDao).execute(child);

    }

    public void updateChild(Child child){
        new UpdateChildAsyncTask(childDao).execute(child);
    }

    public void deleteChild(Child child){
        new DeleteChildAsyncTask(childDao).execute(child);
    }

    public void deleteAllChildren(){
        new DeleteAllChildrenChildAsyncTask(childDao).execute();

    }

    public LiveData<List<Child>> getAllChildren() {
        return allChildren;
    }

    private static class InsertChildAsyncTask extends AsyncTask<Child, Void, Void>{
        private ChildDao childDao;

        private InsertChildAsyncTask(ChildDao childDao){
            this.childDao = childDao;
        }

        @Override
        protected Void doInBackground(Child... children) {
            childDao.insert(children[0]);
            return null;
        }
    }
    private static class UpdateChildAsyncTask extends AsyncTask<Child, Void, Void>{
        private ChildDao childDao;

        private UpdateChildAsyncTask(ChildDao childDao){
            this.childDao = childDao;
        }

        @Override
        protected Void doInBackground(Child... children) {
            childDao.update(children[0]);
            return null;
        }
    }
    private static class DeleteChildAsyncTask extends AsyncTask<Child, Void, Void>{
        private ChildDao childDao;

        private DeleteChildAsyncTask(ChildDao childDao){
            this.childDao = childDao;
        }

        @Override
        protected Void doInBackground(Child... children) {
            childDao.delete(children[0]);
            return null;
        }
    }
    private static class DeleteAllChildrenChildAsyncTask extends AsyncTask<Void, Void, Void>{
        private ChildDao childDao;

        private DeleteAllChildrenChildAsyncTask(ChildDao childDao){
            this.childDao = childDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            childDao.deleteAllChildren();
            return null;
        }
    }
}
