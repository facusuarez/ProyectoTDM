package info.androidhive.tabsswipe.Activities.Dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import info.androidhive.tabsswipe.Activities.Entities.Profesor;

@Database(entities = {Profesor.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProfesorDao getProfesorDao();
}