package il.ac.tcb.st.usersapp.Recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import il.ac.tcb.st.usersapp.Http.User;
import il.ac.tcb.st.usersapp.R;
import il.ac.tcb.st.usersapp.databinding.UserItemBinding;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    List<User> users = new ArrayList<>();
    UserItemBinding binding;

    public UserRecyclerViewAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        Glide.with(binding.getRoot())
                .load(user.picture)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error))
                .into(binding.ImageViewProfile);


        binding.textViewFirstName.setText(user.firstname);
        binding.textViewLastName.setText(user.lastname);
        binding.textViewCountry.setText(user.country);
        binding.textViewCity.setText(user.city);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}