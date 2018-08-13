package com.heath.findmylocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView locationText;
    private LocationManager locationManager;
    private String locationProvider;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView backImg = findViewById(R.id.backImg);
        Bitmap inputBitMap = BitmapFactory.decodeResource(getResources(), R.mipmap.back);
        Bitmap outputBitMap = this.bitmapBlur(MainActivity.this, inputBitMap, 25);
        Drawable backDrawable = new BitmapDrawable(outputBitMap);
        backImg.setImageDrawable(backDrawable);
        locationText = (TextView) findViewById(R.id.locationTitle);
    }


    /**
     * Get Location
     * @param view
     */
    public void getLocationMessage(View view) {
        getLocation(MainActivity.this);
    }

    @SuppressLint("MissingPermission")
    private void getLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }

        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
        } else {
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
            locationManager.requestLocationUpdates(locationProvider, 0, 0, mListener);
        }
    }

    private void showLocation(Location location){
        String addressStr =  getAddress(location.getLatitude(), location.getLongitude());
        locationText.setText(addressStr);
    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };


    /**
     * 根据经纬度获取位置信息
     */
    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String data = address.toString();
                int startCity = data.indexOf("1:\"") + "1:\"".length();
                int endCity = data.indexOf("\"", startCity);
                String city = data.substring(startCity, endCity);

                int startPlace = data.indexOf("feature=") + "feature=".length();
                int endplace = data.indexOf(",", startPlace);
                String place = data.substring(startPlace, endplace);
                return city + place ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "经度：" + latitude + "\n纬度: " + longitude;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap bitmapBlur (Context context, Bitmap source, int radius) {
        Bitmap inputBitmap = source;
        RenderScript renderScript = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBitmap);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBitmap);
        renderScript.destroy();
        return inputBitmap;
    }
}
