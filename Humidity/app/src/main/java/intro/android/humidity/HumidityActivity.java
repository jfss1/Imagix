package intro.android.humidity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class HumidityActivity  extends Activity implements SensorEventListener {
    private SensorManager mSensormanager;
    private Sensor mHumiditySensor;
    private Sensor mTemperatureSensor;
    private boolean isHumiditySensorPresent;
    private boolean isTemperatureSensorPrensent;
    private TextView mRelativeHumidityValue;
    private TextView mAbsoluteHumidityValue;
    private TextView mDewPointValue;
    private float mLastKnownRelativeHumidity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.humidity_layout);
        mRelativeHumidityValue = (TextView)
                findViewById(R.id.relativehumiditytext);
        mAbsoluteHumidityValue = (TextView)
                findViewById(R.id.absolutehumiditytext);
        mDewPointValue = (TextView)
                findViewById(R.id.dewpointtext);
        mSensormanager= (SensorManager)
                this.getSystemService(Context.SENSOR_SERVICE);

        if(mSensormanager.getDefaultSensor
                (Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            mHumiditySensor = mSensormanager.getDefaultSensor
                    (Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditySensorPresent = true;
        }else{
            mRelativeHumidityValue.setText("Relative Humidity" +
                    " Sensor is not available!");
            mAbsoluteHumidityValue.setText("Cannot calculate" +
                    " Absolute Humidity, as relative humidity" +
                    " sensor is note available!");
            mDewPointValue.setText("Cannot calculate Dew Point," +
                    " as relative humidity sensor is " +
                    "not available!");
            isHumiditySensorPresent = false;
        }

        if(mSensormanager.getDefaultSensor
                (Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mTemperatureSensor = mSensormanager.getDefaultSensor
                    (Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorPrensent = true;
        }else{
            mAbsoluteHumidityValue.setText("Cannot calculate " +
                    "Absolute Humidity, as temperature sensor " +
                    "is not available!");
            mDewPointValue.setText("Cannot calculate Dew Point, " +
                    "as temperature sensor is not available!");
            isTemperatureSensorPrensent = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            mRelativeHumidityValue.setText("Relative Humidity " +
                    "is % is " + event.values[0]);
            mLastKnownRelativeHumidity = event.values[0];
        }else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            if(mLastKnownRelativeHumidity != 0){
                float temperature = event.values[0];
                float absoluteHumidity = calculateAbsoluteHumidity(temperature,
                        mLastKnownRelativeHumidity);
                mAbsoluteHumidityValue.setText("The absolute humidity" +
                        " at temperature: " + temperature + " is "
                        + absoluteHumidity);
                float dewPoint = calculateDewPoint(temperature,
                        mLastKnownRelativeHumidity);
                mDewPointValue.setText("The dew point at temperature: " +
                        temperature + " is: " + dewPoint);
            }
        }
    }

    private float calculateDewPoint(float temperature, float RelativeHumidity) {
        float Td = 0;
        float m = 17.62f;
        float Tn = 243.12f;
        float Rh = RelativeHumidity;
        float Tc = temperature;

        Td= (float)(Tn * ((Math.log(Rh/100) +
                m*Tc/(Tn+Tc))/(m - (Math.log(Rh/100)+
                m*Tc/(Tn+Tc)))));
        return Td;
    }

    private float calculateAbsoluteHumidity(float temperature, float relativeHumidity) {
        float Dv = 0;
        float m = 17.62f;
        float Tn = 243.12f;
        float Ta = 216.7f;
        float Rh = relativeHumidity;
        float Tc = temperature;
        float A = 6.112f;
        float K = 273.15f;
        Dv = (float)(Ta * (Rh/100) * A *
                Math.exp(m*Tc/(Tn+Tc)) / (K +Tc));
        return Dv;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
