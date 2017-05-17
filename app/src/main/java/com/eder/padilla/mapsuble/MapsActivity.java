package com.eder.padilla.mapsuble;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import butterknife.internal.Utils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,GoogleMap.OnMarkerDragListener,GoogleMap.OnInfoWindowClickListener {

    public static final String EXTRA_LATITUD = "latitud";
    public static final String EXTRA_LONGITUD = "longitud";
    private GoogleMap mMap;

    private MapsFragment mMapsFragment;

    private static final int LOCATION_REQUEST_CODE = 1;

    private Marker markerPais,markerEcuador,markerArgentina,markerColombia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //         .findFragmentById(R.id.map);
        // mapFragment.getMapAsync(this);

        mMapsFragment = MapsFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mMapsFragment)
                .commit();
        mMapsFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        LatLng cali = new LatLng(3.4383, -76.5161);
        googleMap.addMarker(new MarkerOptions()
                .position(cali)
                .title("Cali la sucursal del cielo"));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cali)
                .zoom(10)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LatLng latLng = new LatLng(36.2048, 138.2529);

        MarkerOptions markerOptions =
                new MarkerOptions()
                        .position(latLng)
                        .title("Japón")
                        .snippet("Primer ministro: Shinzō Abe")
                        .draggable(true);

        Marker marker = googleMap.addMarker(markerOptions);
        LatLng japon2 = new LatLng(36.2048, 138.2529);
        googleMap.addMarker(new MarkerOptions()
                .position(japon2)
                .title("Marcador CYAN")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(japon2));
        LatLng position = new LatLng(10, 10);
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title("Marcador con icono personalizado")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        LatLng position3 = new LatLng(15, 15);
        googleMap.addMarker(new MarkerOptions()
                .position(position3)
                .title("Marcador draggable")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position3));

        googleMap.setOnMarkerClickListener(this);

        LatLng colombia = new LatLng(4.6,-74.08);
        markerColombia = googleMap.addMarker(new MarkerOptions()
                .position(colombia)
                .title("Colombia"));
        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(colombia));
        LatLng ecuador = new LatLng(-0.217, -78.51);
        markerEcuador = googleMap.addMarker(new MarkerOptions()
                .position(ecuador)
                .title("Ecuador")
                .draggable(true));
        // Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ecuador));

        // Eventos
        googleMap.setOnMarkerDragListener(this);

        LatLng argentina = new LatLng(-34.6, -58.4);
        markerArgentina = googleMap.addMarker(
                new MarkerOptions()
                        .position(argentina)
                        .title("Argentina")
        );

// Cámara
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(argentina));

// Eventos
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnInfoWindowClickListener(this);




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(markerPais)) {
            Intent intent = new Intent(this, MrkerInfoActivity.class);
            intent.putExtra(EXTRA_LATITUD, marker.getPosition().latitude);
            intent.putExtra(EXTRA_LONGITUD, marker.getPosition().longitude);

            startActivity(intent);
        }
        if (marker.equals(markerColombia)) {

            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    Intent intent = new Intent(MapsActivity.this, MrkerInfoActivity.class);
                    intent.putExtra(EXTRA_LATITUD, marker.getPosition().latitude);
                    intent.putExtra(EXTRA_LONGITUD, marker.getPosition().longitude);
                    startActivity(intent);
                }

                @Override
                public void onCancel() {

                }
            });

            return true;

        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.equals(markerEcuador)) {
            Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.equals(markerEcuador)) {
            String newTitle = String.format(Locale.getDefault(),
                    getString(R.string.marker_detail_latlng),
                    marker.getPosition().latitude,
                    marker.getPosition().longitude);

            setTitle(newTitle);
            log(newTitle);

        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(markerEcuador)) {
            Toast.makeText(this, "END", Toast.LENGTH_SHORT).show();
        }
    }
    public static void log(String message){
        Log.e("::Debug::",message);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(markerArgentina)) {

            ArgentinaDialogFragment.newInstance(marker.getTitle(),
                    getString(R.string.argentina_full_snippet))
                    .show(getSupportFragmentManager(), null);
        }
    }
    //para remover un marker solo se pone marker.remove()
}
