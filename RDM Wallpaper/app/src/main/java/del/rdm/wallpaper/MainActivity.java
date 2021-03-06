package del.rdm.wallpaper;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private TextView text;
	private final static String URL = "https://ghodelweb.000webhostapp.com/Upload/get_data.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		      Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		      setSupportActionBar(toolbar);
		      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View _v) {
               onBackPressed();
            }
        });
		text = (TextView)findViewById(R.id.text_view1);
	 new JsonUrlTask(MainActivity.this, new AsyncRespone(){
				@Override
				public void finisProses(String output) {
					text.setText(output);
				}
			}).execute(URL);

    }

}
