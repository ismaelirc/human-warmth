package br.com.humanwarmth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<Doacao> doacoes;
    private RecyclerView rv;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                goToListView();

                return true;


            default:
                //Ação do usuário não encontrada, passa o controle para a Super Class
                return super.onOptionsItemSelected(item);

        }
    }

    private void initializeData(){
        doacoes = new ArrayList<>();

        realm.getDefaultInstance();
        realm = Realm.getDefaultInstance();

        // Iterate over all objects
        for (Doacao d : realm.allObjects(Doacao.class)) {

            doacoes.add(d);

        }
    }

    private void initializeAdapter(){

        RVAdapter adapter = new RVAdapter(doacoes);

        rv.setAdapter(adapter);
    }

    private void goToListView() {

        Intent intent = new Intent(RecyclerViewActivity.this, RecyclerViewActivity.class);

        startActivity(intent);
    }
}
