package natasha.restaurantreview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;


public class DetailsRestaurant extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_restaurant);
        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant)intent.getSerializableExtra("restaurant");
        this.restaurant = restaurant;
        button1 = (Button)findViewById(R.id.button11);
        button2 = (Button)findViewById(R.id.button12);
        button3 = (Button)findViewById(R.id.button13);
        button4 = (Button)findViewById(R.id.button14);
        button5 = (Button)findViewById(R.id.button15);
        button6 = (Button)findViewById(R.id.button16);
        button7 = (Button)findViewById(R.id.button17);
        one = (TextView)findViewById(R.id.textView);
        four = (TextView)findViewById(R.id.textView4);
        three = (TextView)findViewById(R.id.textView3);
        five = (TextView)findViewById(R.id.textView5);
        six = (TextView)findViewById(R.id.textView6);
        seven = (TextView)findViewById(R.id.textView7);

        one.setText("Name:" + restaurant.getName());
        four.setText("Address:" + restaurant.getAddress());
        three.setText("Phone:" + restaurant.getPhone());
        five.setText("Description:" + restaurant.getDescription());
        six.setText("Tags:" + restaurant.getTags());
        seven.setText("Rating:" + restaurant.getRating());

        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
        button5.setOnClickListener(onClickListener);
        button6.setOnClickListener(onClickListener);
        button7.setOnClickListener(onClickListener);
    }

    Restaurant restaurant;
    TextView one;
    TextView four;
    TextView three;
    TextView five;
    TextView six;
    TextView seven;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.button11:
                    Intent intent7 = new Intent(Intent.ACTION_SEND);
                    intent7.setType("text/html");
                    intent7.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                    intent7.putExtra(Intent.EXTRA_SUBJECT, restaurant.getName());
                    intent7.putExtra(Intent.EXTRA_TEXT, "Hey I reviewed this restaraunt: "
                            + "\n" +  restaurant.getName() + "\n" + restaurant.getAddress() + "\n" + restaurant.getDescription()
                            + "\n" +  restaurant.getPhone()  + "\n" +  restaurant.getTags()  + "\n" +  restaurant.getRating()+ " out of 5");
                    startActivity(Intent.createChooser(intent7, "Send Email"));
                    break;
                case R.id.button12:
                    Uri uri = Uri.parse("https://www.google.com/maps/place/" + restaurant.getAddress());
                    Intent intent2 = new Intent(DetailsRestaurant.this, Map.class);
                    intent2.putExtra("Address", restaurant.getAddress());
                    intent2.putExtra("Directions", "0");
                    startActivity(intent2);
                    break;
                case R.id.button13:
                    Intent intent =  new Intent(DetailsRestaurant.this, Map.class);
                    intent.putExtra("Address", restaurant.getAddress());

                    intent.putExtra("Directions", "1");

                    startActivity(intent);
                    break;
                case R.id.button14:
                    Intent intent1 = new Intent(DetailsRestaurant.this, AddRestaurant.class);
                    intent1.putExtra("restaurant", restaurant);
                    startActivity(intent1);
                    break;
                case R.id.button15:
                    Intent tweetIntent = new Intent(Intent.ACTION_SEND);
                    tweetIntent.putExtra(Intent.EXTRA_TEXT,  restaurant.getName() + "\n" + restaurant.getAddress() + "\n" + restaurant.getDescription()
                            + "\n" +  restaurant.getPhone()  + "\n" +  restaurant.getTags()  + "\n" +  restaurant.getRating()+ " out of 5");
                    tweetIntent.setType("application/twitter");
                    startActivity(tweetIntent);
                    break;
                case R.id.button16:
                    Intent intent3 = new Intent(Intent.ACTION_SEND);
                    intent3.setType("text/plain");
                    intent3.putExtra(Intent.EXTRA_TEXT, restaurant.getName() + "\n" + restaurant.getAddress() + "\n" + restaurant.getDescription()
                            + "\n" +  restaurant.getPhone()  + "\n" +  restaurant.getTags()  + "\n" +  restaurant.getRating()+ " out of 5");
                    startActivity(Intent.createChooser(intent3, "Facebook"));
                    break;
                case R.id.button17:
                    Intent intent9 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + restaurant.getPhone()));
                    startActivity(intent9);
                    break;
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_restaurant, menu);
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
