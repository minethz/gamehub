package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Shop extends AppCompatActivity implements GameAdapter.OnGameClickListener {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<Game> gameList;
    private List<Game> filteredGameList; // List to hold filtered games
    private FreeToGameApiService freeToGameApiService;
    private List<Game> cartList; // List to hold items in the cart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize cart list
        cartList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameList = new ArrayList<>();
        filteredGameList = new ArrayList<>();
        gameAdapter = new GameAdapter(filteredGameList, this, this);
        recyclerView.setAdapter(gameAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.freetogame.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        freeToGameApiService = retrofit.create(FreeToGameApiService.class);

        fetchGames();
    }

    private void fetchGames() {
        Call<List<GameResponse>> call = freeToGameApiService.getGames();

        call.enqueue(new Callback<List<GameResponse>>() {
            @Override
            public void onResponse(Call<List<GameResponse>> call, Response<List<GameResponse>> response) {
                if (response.isSuccessful()) {
                    List<GameResponse> games = response.body();
                    if (games != null) {
                        for (GameResponse gameResponse : games) {
                            String title = gameResponse.getTitle();
                            String imageUrl = gameResponse.getThumbnail();
                            Game game = new Game(title, imageUrl);
                            gameList.add(game);
                            filteredGameList.add(game);
                        }
                        gameAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GameResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // Inflate the menu with the search bar and cart icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterGames(newText);
                return true;
            }
        });

        return true;
    }

    private void filterGames(String query) {
        filteredGameList.clear();
        for (Game game : gameList) {
            if (game.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredGameList.add(game);
            }
        }
        gameAdapter.notifyDataSetChanged();
    }

    // Handle cart icon click event
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cartIcon) {
            // Open cart activity or perform any cart-related action here
            Intent intent = new Intent(this, CartActivity.class);
            intent.putParcelableArrayListExtra("cart", new ArrayList<>(cartList));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Handle game item click to add to cart
    @Override
    public void onGameClick(int position) {
        Game game = filteredGameList.get(position);
        addToCart(game);
    }

    // Method to add a game to the cart
    private void addToCart(Game game) {
        cartList.add(game);
        Toast.makeText(this, "Game added to cart", Toast.LENGTH_SHORT).show();
    }
}
