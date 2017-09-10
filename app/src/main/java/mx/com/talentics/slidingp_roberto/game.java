package mx.com.talentics.slidingp_roberto;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;
import java.util.Stack;

import mx.com.talentics.slidingp_roberto.element;

public class game extends AppCompatActivity {

    public int medida = 130;
    public int margin = 12;
    public int given;
    public int line=0;
    public int column = 0;
    public TextView text;
    public Context cont;
    public RelativeLayout app;
    public int global_column, global_line;
    public ArrayList<Integer> images = new ArrayList<Integer>();

    public ArrayList<ArrayList> Bots = new ArrayList<ArrayList>();
    public Deque<Integer> pila = new ArrayDeque<Integer>();

    public element global_element, actual_element;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




        app = (RelativeLayout) findViewById(R.id.app);
        cont = this;
        text = (TextView) findViewById(R.id.textView);
        Button myb = (Button) findViewById(R.id.button);

        try {
            images.add(R.drawable.cero);
            images.add(R.drawable.uno); //1
            images.add(R.drawable.beerbotle); //2
            images.add(R.drawable.beerbotle); //3
            images.add(R.drawable.beerbotle); //4
            images.add(R.drawable.beerbotle); //5
            images.add(R.drawable.beerbotle); //6
            images.add(R.drawable.beerbotle); //7
            images.add(R.drawable.beerbotle); //8
            images.add(R.drawable.beerbotle); //9
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        };



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String number  = extras.getString("number");
            given= Integer.parseInt(number);
            pila = random(given);
            render(given);
            addListeners(Bots);
            location(given);

        };


        myb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
            int val;
            text.setText(String.valueOf(column)+ " " +String.valueOf(line));
            ImageButton myImg = new ImageButton(cont);
            LayoutParams layoutParams = new LayoutParams(medida, medida);
            layoutParams.setMargins((medida * column)+(margin*column), (medida * line)+(margin*line), 0, 0);

           // myImg.setLayoutParams(layoutParams);
            val= pila.poll();
            element aux = new element();
            if(val!=0) {
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.beerbotle);
                myImg.setBackground(drawable);
            }else{
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.cero);
                myImg.setBackground(drawable);
                global_column=column;
                global_line=line;
            }
            app.addView(myImg);
            aux.img = myImg;
            aux.column = column;
            aux.line = line;
            aux.params = layoutParams;
            aux.value = val;
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

    public void addListeners(final ArrayList<ArrayList> bots){


        for (final ArrayList<element> line: bots) {

            for (final element column: line){
                column.img.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context contnew = getApplicationContext();

                        Drawable drawable = ContextCompat.getDrawable(contnew,images.get(0));
                        //column.img.setBackground(drawable);
                        if(isValidMove(column.column, column.line)){
                            try {
                                global_element = (element) Bots.get(global_line).get(global_column);
                                actual_element = (element) Bots.get(column.line).get(column.column);
                                ImageButton auximg = actual_element.img;
                                int auxval = actual_element.value;
                                global_line = actual_element.line;
                                global_column = actual_element.column;
                                actual_element.value = global_element.value;
                                actual_element.img = global_element.img;
                                drawable = ContextCompat.getDrawable(contnew,images.get(3));
                                actual_element.img.setBackground(drawable);
                                global_element.img = auximg;
                                global_element.value = auxval;
                                drawable = ContextCompat.getDrawable(contnew,images.get(1));
                                global_element.img.setBackground(drawable);
                                text.setText(String.valueOf(global_column) + " " + String.valueOf(global_line));
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            };

                        }
                        else
                            text.setText("invalid movement");

                    }
                });
            }
        }

    }

    public Deque<Integer> random(int given) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        Random r = new Random();
        int a;
        while(stack.size()<given*given){
            do{
                a = r.nextInt(given*given);
            }while(stack.contains(a));
            stack.push(a);
        }
        return stack;
    }
    public boolean isValidMove(int column, int line){
        if(column==global_column&&(line==global_line+1||line==global_line-1)) {
            return true;
        }else if(line==global_line&&(column==global_column+1||column==global_column-1)) {
            return true;
        }else
            return false;
    }

}
