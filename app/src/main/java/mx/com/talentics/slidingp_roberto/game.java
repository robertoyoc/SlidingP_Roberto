package mx.com.talentics.slidingp_roberto;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.com.talentics.slidingp_roberto.element;

public class game extends AppCompatActivity {

    public int medida = 130;
    public int margin = 12;
    public int given;
    public int line=0;
    public int column = 0;
    public int fline =0, fcolumn =0;
    public element[][] Botones;
    public int butons =0;
    public TextView text;
    public Context cont;
    public RelativeLayout app;

    public ArrayList<ArrayList> Bots = new ArrayList<ArrayList>();
    public ArrayList<ArrayList> Params = new ArrayList<ArrayList>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




        app = (RelativeLayout) findViewById(R.id.app);
        cont = this;
        text = (TextView) findViewById(R.id.textView);
        Button myb = (Button) findViewById(R.id.button);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String number  = extras.getString("number");
            int mnumber = Integer.parseInt(number);
            given= mnumber;
            render(given);
            addListeners(Bots);


        };

        myb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                location(given);
            }
        });




    }

    public void trya(){
        try {
            //element aux = new element();
            //aux.img = myImg;
            //aux.column = column;
            //aux.line = line;
            //Bots.get(line).add(aux);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        };
    }

    public void location(int given){
        line= 0;
        column=0;
        while (line<given) {
            element aux = (element) Bots.get(line).get(column);
            aux.img.setLayoutParams(aux.params);
            if(column==given-1){
                line++;
                column=0;
            }
            else{
                column++;
            }
        }

    }

    public void render(int given) {
        while (line<given) {
            if(column==0){
                Bots.add(new ArrayList<element>());
                Params.add(new ArrayList<LayoutParams>());
            }
            text.setText(String.valueOf(column)+ " " +String.valueOf(line));
            ImageButton myImg = new ImageButton(cont);
            LayoutParams layoutParams = new LayoutParams(medida, medida);
            layoutParams.setMargins((medida * column)+(margin*column), (medida * line)+(margin*line), 0, 0);
            myImg.setImageResource(R.drawable.beerbotle);
           // myImg.setLayoutParams(layoutParams);

            app.addView(myImg);
            element aux = new element();
            aux.img = myImg;
            aux.column = column;
            aux.line = line;
            aux.params = layoutParams;
            Bots.get(line).add(aux);

            if(column==given-1){
                line++;
                column=0;
            }
            else{
                column++;
            }
        }
    }

    public void addListeners(ArrayList<ArrayList> bots){

        for (final ArrayList<element> line: bots) {

            for (final element column: line){
                column.img.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text.setText(String.valueOf(column.column)+ ", " + String.valueOf(column.line));
                    }
                });
            }
        }

    }



}
