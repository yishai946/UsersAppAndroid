package il.ac.tcb.st.usersapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import il.ac.tcb.st.usersapp.Http.User;
import il.ac.tcb.st.usersapp.Recycler.UserRecyclerViewAdapter;
import il.ac.tcb.st.usersapp.RoomDB.UserTable;
import il.ac.tcb.st.usersapp.RoomDB.UsersDB;
import il.ac.tcb.st.usersapp.databinding.ActivityUserBinding;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    UsersDB db;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );

        db = UsersDB.getInstance(this);

        List<UserTable> all = db.userDao().getAll();

        users.clear();
        for (UserTable user : all) {
            User u = new User();
            u.firstname = user.firstName;
            u.lastname = user.lastName;
            u.country = user.country;
            u.city = user.city;
            u.picture = user.picture;

            users.add(u);
        }

        UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(users);

        binding.recyclerView.setAdapter(adapter);
    }
}