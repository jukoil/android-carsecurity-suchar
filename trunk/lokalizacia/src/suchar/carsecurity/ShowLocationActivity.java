package suchar.carsecurity;

import suchar.carsecurity.R;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLocationActivity extends Activity implements LocationListener {
  private TextView latituteField;
  private TextView longitudeField;
  private TextView distanceField;
  private TextView speedField;
  private WebView webView;
  private LocationManager locationManager;
  private String provider;

  private Location lastLocation;
  
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    latituteField = (TextView) findViewById(R.id.TextView02);
    longitudeField= (TextView) findViewById(R.id.TextView04);
    distanceField = (TextView) findViewById(R.id.TextView05);
    speedField    = (TextView) findViewById(R.id.TextView08);
    webView    = (WebView) findViewById(R.id.webView1);
    webView.getSettings().setJavaScriptEnabled(true);

    // Get the location manager
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    // Define the criteria how to select the locatioin provider -> use
    // default
    Criteria criteria = new Criteria();
    provider = locationManager.getBestProvider(criteria, false);
    Location location = locationManager.getLastKnownLocation(provider);

    // Initialize the location fields
    if (location != null) {
      System.out.println("Provider " + provider + " has been selected.");
      lastLocation = location;
      onLocationChanged(location);
    } else {
      latituteField.setText("Location not available");
      longitudeField.setText("Location not available");
    }
  }

  /* Request updates at startup */
  @Override
  protected void onResume() {
    super.onResume();
    final long minTimeMS = 500;
    final float minDistanceMeters = 1; 
    // 0 0 to get precise speed
    locationManager.requestLocationUpdates(provider, minTimeMS, minDistanceMeters, this);
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(this);
  }

  public void onLocationChanged(Location location) {
    double lat = location.getLatitude();
    double lng = location.getLongitude();
    double dis = CalcLocation.distance( lastLocation, location );
    double spd = location.getSpeed();
   
    latituteField.setText( String.format("%9.5f", lat));
    longitudeField.setText(String.format("%9.5f", lng));
    distanceField.setText( String.format("%5.1f", dis));
    if( location.hasSpeed() )
    	speedField.setText(String.format("%5.1f", spd));
    else
    	speedField.setText( "not moving" );
    lastLocation = location;
    
    String googlemaps = String.format( "https://maps.google.sk/maps?q=%.5f%+.5f&hl=sk&t=h&z=15", lat, lng );
    webView.loadUrl( googlemaps );    
  }
  
  

  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }


  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "Enabled new provider " + provider,
        Toast.LENGTH_SHORT).show();

  }


  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "Disabled provider " + provider,
        Toast.LENGTH_SHORT).show();
  }
} 