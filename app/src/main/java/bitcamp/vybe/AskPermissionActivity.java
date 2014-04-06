package bitcamp.vybe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class AskPermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permission);

        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        ImageView logo = (ImageView) findViewById(R.id.logo_big);
        logo.startAnimation(slideUp);

        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        ImageView warning_text = (ImageView) findViewById(R.id.warning_text);
        Button get_started = (Button) findViewById(R.id.get_started);
        get_started.startAnimation(fadeIn);
        warning_text.startAnimation(fadeIn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ask_permission, menu);
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

    public void confirmed(View v)
    {
        //Intent firstRun = new Intent(this, MainActivity.class);
        //startActivity(firstRun);
        this.finish();
    }

}
