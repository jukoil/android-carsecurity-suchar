package suchar.carsecurity;

import suchar.carsecurity.R;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView; 
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLocationActivity extends Activity implements LocationListener {
  private TextView latituteField;
  private TextView longitudeField;
  private TextView distanceField;
  private TextView altitudeField;
  private TextView speedField;
  private EditText smsField;
  private WebView webView;
  private LocationManager locationManager;
  private Button button;
  private String provider;

  private Location lastLocation;
  

  double lat=0;
  double lng=0;
  double dis=0;
  double spd=0;
  double alt=0;
  
  
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    smsField 	  = (EditText) findViewById(R.id.editTextPhoneNo);
    latituteField = (TextView) findViewById(R.id.TextView02);
    longitudeField= (TextView) findViewById(R.id.TextView04);
    distanceField = (TextView) findViewById(R.id.TextView05);
    altitudeField = (TextView) findViewById(R.id.TextView10);
    speedField    = (TextView) findViewById(R.id.TextView08);
    webView    = (WebView) findViewById(R.id.webView1);
    webView.getSettings().setJavaScriptEnabled(true);
    button    = (Button) findViewById(R.id.button1);

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

    button.setOnClickListener(new View.OnClickListener() { 
        public void onClick(View view) { 
        	//Communication communication = new Communication();
        	//communication.sendEmail();
        	String phoneNo = smsField.getText().toString();
        	Communication.sendSMS(phoneNo, location2string() );
        } 
      }); 
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
    lat = location.getLatitude();
    lng = location.getLongitude();
    dis = CalcLocation.distance( lastLocation, location );
    spd = location.getSpeed();
    alt = location.getAltitude();
   
    latituteField.setText( String.format("%9.5f", lat));
    longitudeField.setText(String.format("%9.5f", lng));
    distanceField.setText( String.format("%5.1f", dis));
    altitudeField.setText( String.format("%5.1f", alt));
    if( location.hasSpeed() )
    	speedField.setText(String.format("%5.1f", spd));
    else
    	speedField.setText( "not moving" );
    lastLocation = location;
    
    String googlemaps = String.format( "https://maps.google.sk/maps?q=%.5f%+.5f&hl=sk&t=h&z=15", lat, lng );
    webView.loadUrl( googlemaps );    
    
    if( dis > 100 ){
		String phoneNo = smsField.getText().toString();
		Communication.sendSMS(phoneNo, location2string() );
    }
  }
  
  private String location2string(){
	  return String.format("lat:%9.5f\nlng:%9.5f\nalt:%5.1f\nspd:%5.1f\nmov:%5.1f\n", lat, lng, alt, spd, dis);
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