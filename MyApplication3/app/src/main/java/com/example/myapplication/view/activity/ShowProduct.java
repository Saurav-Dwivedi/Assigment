package com.example.myapplication.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Model;
import com.example.myapplication.offline.AppDatabase;
import com.example.myapplication.view.adapter.ProductAdapter;

import java.util.List;
import java.util.Objects;

public class ShowProduct extends AppCompatActivity {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getProducts();
    }

    private void getProducts() {
        class GetTasks extends AsyncTask<Void, Void, List<Model>> {

            @Override
            protected List<Model> doInBackground(Void... voids) {
                List<Model> taskList = AppDatabase
                        .getInstance(getApplicationContext())
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Model> tasks) {
                super.onPostExecute(tasks);
                ProductAdapter adapter = new ProductAdapter(ShowProduct.this, tasks);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        getProducts();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
