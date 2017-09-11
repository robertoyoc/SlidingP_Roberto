package mx.com.talentics.slidingp_roberto;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
    public Context cont;
    public RelativeLayout app;
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

        images.add(R.drawable.ncero);
        images.add(R.drawable.nuno); //1
        images.add(R.drawable.ndos); //2
        images.add(R.drawable.ntres); //3
        images.add(R.drawable.ncuatro); //4
        images.add(R.drawable.ncinco); //5
        images.add(R.drawable.nseis); //6
        images.add(R.drawable.nsiete); //7
        images.add(R.drawable.nocho); //8
        images.add(R.drawable.nnueve);
        images.add(R.drawable.ndiez);
        images.add(R.drawable.nonce);
        images.add(R.drawable.ndoce);
        images.add(R.drawable.ntrece);
        images.add(R.drawable.ncatorce);
        images.add(R.drawable.nquince);
        images.add(R.drawable.ndiesseis);
        images.add(R.drawable.ndiezsiete);
        images.add(R.drawable.ndiezocho);
        images.add(R.drawable.ndieznueve);
        images.add(R.drawable.nveinte);
        images.add(R.drawable.nveinteuno);
        images.add(R.drawable.nveintedos);
        images.add(R.drawable.nveintetres);
        images.add(R.drawable.nveintecuatro);
        images.add(R.drawable.nveintecinco);
        images.add(R.drawable.nveinteseis);
        images.add(R.drawable.nveintesiete);
        images.add(R.drawable.nveinteocho);
        images.add(R.drawable.nveintenueve);
        images.add(R.drawable.ntreinta);
        images.add(R.drawable.ntreintauno);
        images.add(R.drawable.ntreintados);
        images.add(R.drawable.ntreintatres);
        images.add(R.drawable.ntreintacuatro);
        images.add(R.drawable.ntreintacinco);
        images.add(R.drawable.ntreintaseis);
        images.add(R.drawable.ntreintasiete);
        images.add(R.drawable.ntreintaocho);
        images.add(R.drawable.ntreintanueve);
        images.add(R.drawable.ncuarenta);
        images.add(R.drawable.ncuarentauno);
        images.add(R.drawable.ncuarentados);
        images.add(R.drawable.ncuarentatres);
        images.add(R.drawable.ncuarentacuatro);
        images.add(R.drawable.ncuarentacinco);
        images.add(R.drawable.ncuarentaseis);
        images.add(R.drawable.ncuarentasiete);
        images.add(R.drawable.ncuarentaocho);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String number  = extras.getString("number");

            given= Integer.parseInt(number);
            pila = random(given);
            render(given);

            AlertDialog diaBox = welcome();
            diaBox.show();
            addListeners(Bots);
            location(given);





        };



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
            ImageButton myImg = new ImageButton(cont);
            LayoutParams layoutParams = new LayoutParams(medida, medida);
            layoutParams.setMargins((medida * column)+(margin*column), (medida * line)+(margin*line), 0, 0);

           // myImg.setLayoutParams(layoutParams);
            val= pila.poll();
            element aux = new element();

            aux.img = myImg;
            aux.column = column;
            aux.line = line;
            aux.params = layoutParams;
            aux.value = val;
            if(val!=0) {
                try{
                    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),images.get(val));
                    myImg.setBackground(drawable);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                };

            }else{
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),images.get(0));
                myImg.setBackground(drawable);
                global_element = aux;
            }
            app.addView(myImg);
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

        for (ArrayList<element> line: bots) {

            for (final element column: line){

                column.img.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(isValidMove(column.column, column.line)) {
                            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),images.get(column.value));
                            global_element.img.setBackground(drawable);
                            global_element.value = column.value;
                            global_element = column;
                            actual_element = (element) Bots.get(column.line).get(column.column);
                            drawable = ContextCompat.getDrawable(getApplicationContext(),images.get(0));
                            column.img.setBackground(drawable);
                            column.value = 0;
                            checkResolved(Bots);
                        }




                    }
                });
            }
        }

    }

    public Deque<Integer> random(int given) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        boolean real = true; //cambiar a false para probar una distribución sencilla
        Random r = new Random();
        if(real) {
            int a;
            while (stack.size() < given * given) {
                do {
                    a = r.nextInt(given * given);
                } while (stack.contains(a));
                stack.push(a);
            }
        }else {
            stack.push(1);
            stack.push(0);
            stack.push(2);
            stack.push(3);
            stack.push(4);
            stack.push(5);
            stack.push(6);
            stack.push(7);
            stack.push(8);
            stack.push(9);
            stack.push(10);
            stack.push(11);
            stack.push(12);
            stack.push(13);
            stack.push(14);
            stack.push(15);
            stack.push(16);
            stack.push(17);
            stack.push(18);
            stack.push(19);
            stack.push(20);
            stack.push(21);
            stack.push(22);
            stack.push(23);
            stack.push(24);
            stack.push(25);
            stack.push(26);
            stack.push(27);
            stack.push(28);
            stack.push(29);
            stack.push(30);
            stack.push(31);
            stack.push(32);
            stack.push(33);
            stack.push(34);
            stack.push(35);
            stack.push(36);
            stack.push(37);
            stack.push(38);
            stack.push(39);
            stack.push(40);
            stack.push(41);
            stack.push(42);
            stack.push(43);
            stack.push(44);
            stack.push(45);
            stack.push(46);
            stack.push(47);
            stack.push(48);
        }


        return stack;
    }
    public boolean isValidMove(int column, int line){
        if(column==global_element.column&&(line==global_element.line+1||line==global_element.line-1)) {
            return true;
        }else if(line==global_element.line&&(column==global_element.column+1||column==global_element.column-1)) {
            return true;
        }else
            return false;
    }

    public boolean checkResolved(final ArrayList<ArrayList> bots){
        boolean result = true, result2 = true, result3 = true;
        int el=1;
        int el2 = given*given-1;
        for (ArrayList<element> line: bots) {
            for (final element column: line){
                if (column.value!=0){
                    if(column.value!=el){
                        result = false;
                    }
                    if(column.value!=el2){
                        result2=false;
                    }
                    if(column.value!=(column.column*given+column.line+1)){
                        result3 = false;
                    }

                }
                el++;
                el2--;
            }
        }

        if(result||result2||result3){
            AlertDialog diaBox = gameover();
            diaBox.show();
        }
        return result;


    }

    private AlertDialog gameover()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Fin del Juego!")
                .setMessage("Felicidades campeón, has ganado!")
                .setIcon(R.drawable.trofeo)

                .setPositiveButton("Nuevo Juego", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), index.class);
                        startActivity(i);
                    }

                })
                .create();
        return myQuittingDialogBox;

    }
    private AlertDialog welcome()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Bienvenido a Sliding Puzzle")
                .setMessage("Presiona las imagenes para mover, presiona el botón de atrás para salir. Solo se permite mover las piezas que son vecinas al número 0.")
                .setIcon(R.drawable.pensando)

                .setPositiveButton("Adelante!", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                    }

                })
                .create();
        return myQuittingDialogBox;

    }

}

