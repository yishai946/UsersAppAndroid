package il.ac.tcb.st.usersapp.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import il.ac.tcb.st.usersapp.RoomDB.UserTable;
import il.ac.tcb.st.usersapp.RoomDB.UsersDao;

@Database(entities = {UserTable.class}, version = 4)
public abstract class UsersDB extends RoomDatabase {
    public static UsersDB instance;

    public static synchronized UsersDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            UsersDB.class,
                            "users_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UsersDao userDao();

}
