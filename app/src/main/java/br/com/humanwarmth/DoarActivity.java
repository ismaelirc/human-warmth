package br.com.humanwarmth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by ismael on 05/04/16.
 */
public class DoarActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button btnDoar;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doar);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        setUI();
        setActions();

    }

    public void setUI(){
        btnDoar = (Button) findViewById(R.id.btn_doar);
    }

    private void setActions(){
        btnDoar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.getDefaultInstance();
                realm = Realm.getDefaultInstance();

                realm.beginTransaction();

                Doacao doacao = realm.createObject(Doacao.class);
                doacao.setDescricao("Teste descricao");
                doacao.setName("Ismael");

                realm.commitTransaction();

                RealmResults<Doacao> results = realm.where(Doacao.class).findAll();
                Log.i(TAG, "O nome é: " + results.size());
            }
        });


    }}
