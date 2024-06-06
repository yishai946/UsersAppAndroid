package il.ac.tcb.st.usersapp.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersDao {
    @Query("SELECT * FROM Users")
    List<UserTable> getAll();

    @Query("SELECT * FROM Users WHERE id = :id")
    UserTable findUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserTable userTable);
}
