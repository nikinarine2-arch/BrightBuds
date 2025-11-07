package com.example.brightbuds;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class CongratulationsActivity extends AppCompatActivity {

    private MediaPlayer wellDoneSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        final KonfettiView konfettiView = findViewById(R.id.konfettiView);
        ImageView homeButton = findViewById(R.id.homeButton);

        wellDoneSound = MediaPlayer.create(this, R.raw.well_done_sound);
        if (wellDoneSound != null) {
            wellDoneSound.start();
        }

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wellDoneSound != null && wellDoneSound.isPlaying()) {
                    wellDoneSound.stop();
                }
                Intent intent = new Intent(CongratulationsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        EmitterConfig emitterConfig = new Emitter(300L, TimeUnit.SECONDS).perSecond(50);
        Party party = new PartyFactory(emitterConfig)
                .spread(360)
                .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                .setSpeedBetween(0f, 15f)
                .timeToLive(2000L)
                .sizes(new Size(12, 5f, 10))
                .position(new Position.Relative(0.5, 0.3))
                .build();

        konfettiView.start(party);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wellDoneSound != null) {
            wellDoneSound.release();
            wellDoneSound = null;
        }
    }
}
