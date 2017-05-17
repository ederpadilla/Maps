package com.eder.padilla.mapsuble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MrkerInfoActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrker_info);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Extraer lat. y lng.
        Intent intent = getIntent();
        String latlng = String.format(
                getString(R.string.marker_detail_latlng),
                intent.getDoubleExtra(MapsActivity.EXTRA_LATITUD, 0),
                intent.getDoubleExtra(MapsActivity.EXTRA_LONGITUD, 0));

        // Poblar

        textView.setText(latlng);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}

