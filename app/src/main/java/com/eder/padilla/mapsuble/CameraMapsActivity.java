package com.eder.padilla.mapsuble;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Locale;

public class CameraMapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;


    private MapsFragment mMapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        mMap=googleMap;
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(5));
        //Mover la camara a un lugar en especifico con zoom

        LatLng nicaragua = new LatLng(13, -85);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nicaragua, 7));
        googleMap.moveCamera(CameraUpdateFactory.scrollBy(100,-200));
        //Si deseas cambiar todas las propiedades relacionadas a la posición de la cámara usa la clase CameraPosition.
        LatLng españa = new LatLng(40.416667, -3.75);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(españa)
                .zoom(7)
                .bearing(90)
                .tilt(90)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        LatLng cali = new LatLng(3.444, -76.511);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(cali));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cali));
        PolylineOptions sudamericaRect = new PolylineOptions()
                .add(new LatLng(12.897489, -82.441406)) // P1
                .add(new LatLng(12.897489, -32.167969)) // P2
                .add(new LatLng(-55.37911, -32.167969)) // P3
                .add(new LatLng(-55.37911, -82.441406)) // P4
                .add(new LatLng(12.897489, -82.441406)) // P1
                .color(Color.parseColor("#f44336"));    // Rojo 500
        //
        Polyline polyline = googleMap.addPolyline(sudamericaRect);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(-20.96144, -61.347656)));
        crearPoligono();
        crearCirculos();
        googleMap.setOnMapClickListener(this);
        // Configuración UI
        mMap.getUiSettings().setAllGesturesEnabled(false);

        // Eventos
        mMap.setOnMapLongClickListener(this);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add("Inicio").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();

        if (title != null && title.equals("Inicio")) {
            // Animar cámara
            LatLng zero = new LatLng(80, 80);
            mMap.animateCamera(
                    CameraUpdateFactory.newLatLng(zero), // update
                    2000, // durationMs
                    new GoogleMap.CancelableCallback() { // callback
                        @Override
                        public void onFinish() {
                            Toast.makeText(CameraMapsActivity.this, "Animación finalizada",
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearPoligono(){
        // Ejemplo: Encerrar a Cuba con un polígono de bajo detalle
        LatLng p1 = new LatLng(21.88661065803621, -85.01541511562505);
        LatLng p2 = new LatLng(22.927294359193038, -83.76297370937505);
        LatLng p3 = new LatLng(23.26620799401109, -82.35672370937505);
        LatLng p4 = new LatLng(23.387267854439315, -80.79666511562505);
        LatLng p5 = new LatLng(22.496957602618004, -77.98416511562505);
        LatLng p6 = new LatLng(20.20512046753661, -74.16092292812505);
        LatLng p7 = new LatLng(19.70944706110937, -77.65457527187505);
        Polygon cubaPolygon = mMap.addPolygon(new PolygonOptions()
                .add(p1, p2, p3, p4, p5, p6, p7, p1)
                .strokeColor(Color.parseColor("#AB47BC"))
                .fillColor(Color.parseColor("#7B1FA2")));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(21.5034305704608, -78.95096199062505), 5));
    }

    private void crearCirculos(){
        LatLng center = new LatLng(3.4003755294523828, -76.54801384952702);
        int radius = 40;
        CircleOptions circleOptions = new CircleOptions()
                .center(center)
                .radius(radius)
                .strokeColor(Color.parseColor("#0D47A1"))
                .strokeWidth(4)
                .fillColor(Color.argb(32, 33, 150, 243));
        Circle circle = mMap.addCircle(circleOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 17));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MapsActivity.log("Entra al map click");
        String formatLatLng = String.format(Locale.getDefault(),
                "Lat/Lng = (%f,%f)", latLng.latitude, latLng.longitude);

        Point screentPoint = mMap.getProjection().toScreenLocation(latLng);

        String formatScreenPoint = String.format(Locale.getDefault(),
                "\nPoint = (%d,%d)", screentPoint.x, screentPoint.y);

        Toast.makeText(this, formatLatLng + formatScreenPoint, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
// Añadir marker en la posición
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));

        // Obtener pixeles reales
        Point point = mMap.getProjection().toScreenLocation(latLng);

        // Determinar el ancho total de la pantalla
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        int width = display.widthPixels;

        float hue;

        // ¿La coordenada de pantalla es menor o igual que la mitad del ancho?
        if (point.x <= width / 2) {
            hue = BitmapDescriptorFactory.HUE_YELLOW;

        } else {
            hue = BitmapDescriptorFactory.HUE_ORANGE;
        }

        // Cambiar color del marker según el grupo
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));
    }
}
