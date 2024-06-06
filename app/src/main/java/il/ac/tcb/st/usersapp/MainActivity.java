package il.ac.tcb.st.usersapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import il.ac.tcb.st.usersapp.Http.User;
import il.ac.tcb.st.usersapp.Http.UserAPIClient;
import il.ac.tcb.st.usersapp.Http.UserService;
import il.ac.tcb.st.usersapp.MyObjects.Root;
import il.ac.tcb.st.usersapp.RoomDB.UserTable;
import il.ac.tcb.st.usersapp.RoomDB.UsersDB;
import il.ac.tcb.st.usersapp.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    User user = new User("Error","Error","Error","Error",0,"Error","Error", "Error");
    UsersDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        binding.buttonNextUser.setOnClickListener(v -> getNewUser());

        binding.buttonAdd.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = v -> {
        db = UsersDB.getInstance(this);
        if(user.id.equals("Error")){
            Toast.makeText(this, "Can't make user now", Toast.LENGTH_SHORT).show();
            return;
        }
        if(db.userDao().findUserById(user.id) != null){
            Toast.makeText(this, "User already exist", Toast.LENGTH_SHORT).show();
            return;
        }
        UserTable userTable = new UserTable();
        userTable.id = user.id;
        userTable.age = user.age;
        userTable.city = user.city;
        userTable.country = user.country;
        userTable.email = user.email;
        userTable.firstName = user.firstname;
        userTable.lastName = user.lastname;
        userTable.picture = user.picture;

        db.userDao().insertUser(userTable);
        Toast.makeText(this, "User was created", Toast.LENGTH_SHORT).show();

    };

    private void getNewUser(){
        binding.buttonView.setEnabled(false);
        binding.buttonNextUser.setEnabled(false);
        binding.buttonAdd.setEnabled(false);

        Retrofit retrofit = UserAPIClient.getClient();
        UserService service = retrofit.create(UserService.class);
        Call<Root> callAsync = service.getUsers(null, null, null);

        callAsync.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                Root root = response.body();
                assert root != null;
                binding.textViewFirstName.setText(root.results.get(0).name.first);
                binding.textViewLastName.setText(root.results.get(0).name.last);
                binding.textViewAge.setText(String.valueOf(root.results.get(0).dob.age));
                binding.textViewEmail.setText(root.results.get(0).email);
                binding.textViewCity.setText(root.results.get(0).location.city);
                binding.textViewCountry.setText(root.results.get(0).location.country);

                Glide.with(MainActivity.this).load(root.results.get(0).picture.large).apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error)).into(binding.imageViewProfile);
                user.id = root.results.get(0).login.uuid;
                user.firstname = root.results.get(0).name.first;
                user.lastname = root.results.get(0).name.last;
                user.email = root.results.get(0).email;
                user.age = root.results.get(0).dob.age;
                user.city = root.results.get(0).location.city;
                user.country = root.results.get(0).location.country;
                user.picture = root.results.get(0).picture.large;

                binding.buttonView.setEnabled(true);
                binding.buttonNextUser.setEnabled(true);
                binding.buttonAdd.setEnabled(true);

            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable throwable) {
                String err="Error";
                binding.textViewFirstName.setText(err);
                binding.textViewLastName.setText(err);
                binding.textViewAge.setText(err);
                binding.textViewEmail.setText(err);
                binding.textViewCity.setText(err);
                binding.textViewCountry.setText(err);
                Glide.with(MainActivity.this)
                        .load(R.drawable.error)
                        .apply(new RequestOptions().placeholder(R.drawable.placeholder).error(R.drawable.error)).into(binding.imageViewProfile);

                user.id = "Error";
                user.firstname = "Error";
                user.lastname = "Error";
                user.email = "Error";
                user.age = 0;
                user.city = "Error";
                user.country = "Error";
                user.picture = "Error";

                binding.buttonView.setEnabled(true);
                binding.buttonNextUser.setEnabled(true);
                binding.buttonAdd.setEnabled(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNewUser();
    }
}