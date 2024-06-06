package il.ac.tcb.st.usersapp.Http;

import il.ac.tcb.st.usersapp.MyObjects.Root;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("/api")
    public Call<Root> getUsers(
            @Query("limit") Integer limit,
            @Query("skip") Integer skip,
            @Query("select") String select
    );
}
