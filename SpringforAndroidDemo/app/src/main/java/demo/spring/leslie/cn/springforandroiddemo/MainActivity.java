package demo.spring.leslie.cn.springforandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.springframework.web.client.RestTemplate;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doClick(View view){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";

// Create a new RestTemplate instance
                        RestTemplate restTemplate = new RestTemplate();

// Make the HTTP GET request, marshaling the response to a String
                        String result = restTemplate.getForObject(url, String.class, "Spring Framework");
                    }
                }
        ).start();
        Toast.makeText(this, "进来了", Toast.LENGTH_SHORT).show();
    }

}
