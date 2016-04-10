package br.com.humanwarmth;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * Classe principal. Lista os pontos e permite ir para qualquer lugar no app
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,  ConnectionCallbacks, OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private Button btnDoar;
    public Realm realm;
    private RealmList<Doacao> doacoes;

    private GoogleMap maps;
    protected Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    protected boolean gps_enabled,network_enabled;

    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        //zerarBase();

        //popula o array list com os pontos
        setMarkers();

        //seta os botões
        setUI();

        //seta as actions dos botões
        setActions();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildGoogleApiClient();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        maps = googleMap;

        plotMarkers(mMyMarkersArray);

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            Log.i(TAG, "Latlnt = " + mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());

            LatLng latlngUser = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            maps.addMarker(new MarkerOptions()
                            .position(latlngUser)
                    );
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngUser, 13));


            realm.getDefaultInstance();
            realm = Realm.getDefaultInstance();

//            // Iterate over all objects
//            for (Doacao d : realm.allObjects(Doacao.class)) {
//
//                LatLng latlng = new LatLng(d.getLatitude(), d.getLongitude());
//                maps.addMarker(new MarkerOptions()
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
//                        .position(latlng)
//                        .title("Doação de: "+d.getDescricao()));
//
//                Log.i(TAG, "Doação = " + d.getName() + d.getLatitude());
//            }


        } else {

            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
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


    private void setMarkers(){

        realm.getDefaultInstance();
        realm = Realm.getDefaultInstance();

        mMarkersHashMap = new HashMap<Marker, MyMarker>();

        // Iterate over all objects
        for (Doacao d : realm.allObjects(Doacao.class)) {

            mMyMarkersArray.add(new MyMarker("Doação: "+d.getDescricao(),"Endereço: "+d.getEndereco(),"Responsável: "+d.getName()+" - "+d.getEmail(),d.getLatitude(),d.getLongitude()));

        }
    }

    private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarker myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                Marker currentMarker = maps.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                maps.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    private void zerarBase(){

        realm.getDefaultInstance();
        realm = Realm.getDefaultInstance();

        RealmResults<Doacao> results = realm.where(Doacao.class).findAll();

        // All changes to data must happen in a transaction
        realm.beginTransaction();

        // Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        public MarkerInfoWindowAdapter() {}

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            View v  = getLayoutInflater().inflate(R.layout.info_window_marker, null);

            MyMarker myMarker = mMarkersHashMap.get(marker);

            TextView markerLabelDescricao = (TextView)v.findViewById(R.id.marker_label_descricao);

            TextView markerLabelEndereco = (TextView)v.findViewById(R.id.marker_label_endereco);

            TextView markerLabelResponsavel = (TextView)v.findViewById(R.id.marker_label_responsavel);

            markerLabelDescricao.setText(myMarker.getmLabelDescricao());

            markerLabelResponsavel.setText(myMarker.getmLabelResponsavel());

            markerLabelEndereco.setText(myMarker.getmLabelEndereco());

            return v;
        }
    }
}
