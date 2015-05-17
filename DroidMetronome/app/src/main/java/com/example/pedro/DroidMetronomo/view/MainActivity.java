package com.example.pedro.DroidMetronomo.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.pedro.DroidMetronomo.control.Executer;
import com.example.pedro.metronomo.R;
import com.example.pedro.DroidMetronomo.control.FrontConversor;


public class MainActivity extends Activity {
    private Executer executer;
    private boolean inExecution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executer =  new Executer();
        inExecution = false;
    }

    /**
     * Prepara e executa o metronomo.
     * @param view
     */
    public void executar(View view){
        if(!inExecution) {
            // Pegando os valores dos numberPickers
            NumberPicker npFreq = (NumberPicker) findViewById(R.id.bpm);
            //npFreq.setMinValue(10);
            //npFreq.setMaxValue(300);

            NumberPicker npQntBatidas = (NumberPicker) findViewById(R.id.qntBatidas);
            //npQntBatidas.setMinValue(1);
            //npQntBatidas.setMaxValue(16);
            //=====================================

            FrontConversor conversor = new FrontConversor();
            conversor.setSom(this);
            conversor.setFrequenciaBPM(npFreq);
            conversor.setQuantidadeBatidas(npQntBatidas);

            executer.preExecuter(this,conversor.toCompasso()); // preparar
            executer.onExecuter(); // executar

            inExecution = true;

        }else{
            Toast mensagem = Toast.makeText(this, "Metronomo em execução.", Toast.LENGTH_SHORT);
            mensagem.show();
        }
    }

    /**
     * Para a execussão do metronomo
     * @param view
     */
    public void parar(View view){
        if(inExecution){
            executer.stopExecuter();
            inExecution = false;

        }else {
            Toast mensagem = Toast.makeText(this, "Todos os sons foram encerrados.", Toast.LENGTH_SHORT);
            mensagem.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if(inExecution){
            executer.stopExecuter();
            inExecution = false;

        }

        super.onDestroy();
    }
}