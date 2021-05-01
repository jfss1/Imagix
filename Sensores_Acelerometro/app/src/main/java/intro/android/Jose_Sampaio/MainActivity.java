package intro.android.Jose_Sampaio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private boolean cor = false;

    private View view;
    private long updateTempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.BLUE);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        updateTempo = System.currentTimeMillis();

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            calculosAcelerometro(event);
        }
    }

    private void calculosAcelerometro(SensorEvent event){
        float[] values = event.values;
        float x = values[0]; float y = values[1]; float z = values[2];

        long tempoActual = System.currentTimeMillis();

        float aceleracao = (x * x + y * y + z * z )
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        if(aceleracao >= 2){
            if(tempoActual - updateTempo < 200){return;}

            updateTempo = tempoActual;
            Toast.makeText(this, "Detectado movimento!", Toast.LENGTH_SHORT).show();

            if(cor){view.setBackgroundColor(Color.BLUE);
            }else{ view.setBackgroundColor(Color.GREEN); }
            cor = !cor;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    protected  void onResume(){
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected  void onPause(){
        super.onPause();

        sensorManager.unregisterListener(this);
    }
}