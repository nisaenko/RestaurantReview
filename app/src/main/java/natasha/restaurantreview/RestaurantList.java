package natasha.restaurantreview;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;


public class RestaurantList extends ListActivity {

    protected void QueryTextChange() {
       // Log.i("Nomad", "onQueryTextChange");
        search.clear();

        for(Restaurant restaurant:values){
            if(searchView.getQuery().toString().contains(restaurant.getName()) || searchView.getQuery().toString().contains(restaurant.getTags())){
                search.add(restaurant);
            }
        }
        ArrayAdapter<Restaurant> adapter = new ArrayAdapter<Restaurant>(this,
                android.R.layout.simple_list_item_1, search);
        setListAdapter(adapter);
        if (TextUtils.isEmpty(searchView.getQuery())) {
            setListAdapter( new ArrayAdapter<Restaurant>(this,
                    android.R.layout.simple_list_item_1, values));
        }
    }
    SearchView searchView;
    ArrayList<Restaurant> search = new ArrayList<Restaurant>();
    List<Restaurant> values;

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        public boolean onQueryTextChange(String newText) {
            search.clear();

            for(Restaurant restaurant:values){
                if(restaurant.getName().contains(searchView.getQuery().toString()) || restaurant.getTags().contains(searchView.getQuery().toString())){
                    search.add(restaurant);
                }
            }
            ArrayAdapter<Restaurant> adapter = new ArrayAdapter<Restaurant>(RestaurantList.this,
                    android.R.layout.simple_list_item_1, search);
            setListAdapter(adapter);
            if (TextUtils.isEmpty(searchView.getQuery())) {
                setListAdapter( new ArrayAdapter<Restaurant>(RestaurantList.this,
                        android.R.layout.simple_list_item_1, values));
            }
            return true;}
        public boolean onQueryTextSubmit(String query) {return true;}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

         searchView =
                (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(queryTextListener);

        add = (Button)findViewById(R.id.addBtn);

        add.setOnClickListener(onClickListener);
        datasource = new RestaurantDataSource(this);
        datasource.open();

     values = datasource.getAllRestaurants();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Restaurant> adapter = new ArrayAdapter<Restaurant>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
    Button add;
    RestaurantDataSource datasource;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Restaurant item = (Restaurant) getListAdapter().getItem(position);
        Toast.makeText(this, item.toString() + " selected", Toast.LENGTH_LONG).show();
        Intent mainIntent = new Intent(RestaurantList.this, DetailsRestaurant.class);
        mainIntent.putExtra("restaurant", item);
        RestaurantList.this.startActivity(mainIntent);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.addBtn:
                    Intent mainIntent = new Intent(RestaurantList.this, AddRestaurant.class);
                    RestaurantList.this.startActivity(mainIntent);
                    break;
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_list, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
