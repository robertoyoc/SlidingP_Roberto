package mx.com.talentics.slidingp_roberto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class index extends AppCompatActivity {

    EditText mnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button mplay = (Button) findViewById(R.id.play);

        mnumber = (EditText) findViewById(R.id.number);
        final Context cont = this;




        mplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), game.class);
                String number = mnumber.getText().toString();
                if(Integer.parseInt(number)>2 &&Integer.parseInt(number)<8)
                {
                    i.putExtra("number", number);
                    startActivity(i);
                }
                else{
                    Toast toast = Toast.makeText(cont, "Numero no válido", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }
}
