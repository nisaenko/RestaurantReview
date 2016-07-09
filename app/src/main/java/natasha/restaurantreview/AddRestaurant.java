package natasha.restaurantreview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;


public class AddRestaurant extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant)intent.getSerializableExtra("restaurant");
        this.restaurant = restaurant;
        if(restaurant != null){
            editing = true;
        }
        save = (Button)findViewById(R.id.saveBtn);
        delete = (Button)findViewById(R.id.button);
        save.setOnClickListener(onClickListener);
        delete.setOnClickListener(onClickListener);
        name = (EditText)findViewById(R.id.nameText);
        address = (EditText)findViewById(R.id.addressText);
        phone = (EditText)findViewById(R.id.phoneText);
        desc = (EditText)findViewById(R.id.descText);
        tagspin = (Spinner)findViewById(R.id.tagSpin);
        ratingbar = (RatingBar)findViewById(R.id.ratingBar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.food_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagspin.setAdapter(adapter);
        if(editing){
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());
            phone.setText(restaurant.getPhone());
            desc.setText(restaurant.getDescription());
            this.setSpinText(tagspin, restaurant.getTags());
            ratingbar.setRating(Float.parseFloat(restaurant.getRating()));
        }


    }
    Boolean editing = false;
    Restaurant restaurant;
    EditText name;
    EditText address;
    EditText phone;
    EditText desc;
    Spinner tagspin;
    RatingBar ratingbar;
    Button save;
    Button delete;

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.saveBtn:
                    //here
                  RestaurantDataSource  datasource = new RestaurantDataSource(AddRestaurant.this);
                    datasource.open();

                    if(editing){
                        datasource.updateRestaurant(restaurant.getId(), name.getText().toString(), address.getText().toString(), phone.getText().toString(),
                                desc.getText().toString(), tagspin.getSelectedItem().toString(), String.valueOf(ratingbar.getRating()));
                    }else{
                        datasource.createRestaurant(name.getText().toString(), address.getText().toString(), phone.getText().toString(),
                                desc.getText().toString(), tagspin.getSelectedItem().toString(), String.valueOf(ratingbar.getRating()));
                    }

                    datasource.close();
                    Intent mainIntent = new Intent(AddRestaurant.this, RestaurantList.class);
                    AddRestaurant.this.startActivity(mainIntent);
                    break;
                case R.id.button:
                    RestaurantDataSource  datasource2 = new RestaurantDataSource(AddRestaurant.this);
                    datasource2.open();
                    datasource2.deleteComment(restaurant);
                    Intent mainIntent2 = new Intent(AddRestaurant.this, RestaurantList.class);
                    AddRestaurant.this.startActivity(mainIntent2);
                    datasource2.close();

                    break;

            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_restaurant, menu);
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
