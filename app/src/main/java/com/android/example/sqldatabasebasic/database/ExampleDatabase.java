package com.android.example.sqldatabasebasic.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Parent.class, Child.class}, version = 1)
public abstract class ExampleDatabase extends RoomDatabase {

    private static ExampleDatabase instance;

    public abstract ParentDao parentDao();
    public abstract ChildDao childDao();

    public static synchronized ExampleDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExampleDatabase.class, "example_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ParentDao parentDao;
        private ChildDao childDao;

        private PopulateDbAsyncTask(ExampleDatabase database){
            parentDao = database.parentDao();
            childDao = database.childDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            parentDao.insert(new Parent("Parent 1", "Description 1", 1));
            parentDao.insert(new Parent("Parent 2", "Description 2", 2));
            parentDao.insert(new Parent("Parent 3", "Description 3", 3));
            childDao.insert(new Child("Child 1", 1, 1));
            childDao.insert(new Child("Child 2", 2, 2));
            childDao.insert(new Child("Child 3", 3, 3));
            childDao.insert(new Child("Child 4", 4, 2));
            childDao.insert(new Child("Child 5", 5, 2));
            childDao.insert(new Child("Child 6", 6, 2));
            childDao.insert(new Child("Child 7", 7, 2));
            childDao.insert(new Child("Child 8", 8, 2));
            childDao.insert(new Child("Child 9", 9, 2));
            childDao.insert(new Child("Child 10", 10, 2));
            return null;
        }
    }
}
