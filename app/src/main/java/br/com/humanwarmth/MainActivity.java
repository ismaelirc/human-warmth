package br.com.humanwarmth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Classe principal. Lista os pontos e permite ir para qualquer lugar no app
 */
public class MainActivity extends AppCompatActivity {

    private Button btnDoar;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //seta os botões
        setUI();

        //seta as actions dos botões
        setActions();

    }

    public void setUI(){

        btnDoar = (Button) findViewById(R.id.btn_doar);

    }

    private void setActions(){

        //botão para cadastro de uma nova doação é tocado
        btnDoar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToDoacao();

            }
        });
    }

    private void goToDoacao(){

        Intent intent = new Intent(MainActivity.this, DoarActivity.class);

        startActivity(intent);
    }

}