package suchar.carsecurity;


import android.location.Location;

public class CalcLocation {
/*
	private double longitude;
	private double latitude;
	
	public CalcLocation(){
		this.longitude = 0;
		this.latitude  = 0;		
	}
	
	public CalcLocation( double longitude, double latitude ){
		this.longitude = longitude;
		this.latitude  = latitude;		
	}
	
	public void setPosition( double longitude, double latitude ){
		this.longitude = longitude;
		this.latitude  = latitude;		
	}
	
	public double getLatitide(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public String toString(){
		return longitude + "," + latitude;
	}
*/	
	
	public static double toRadians( double angle ){
		return angle/180.0*Math.PI;
	}
	
	public static double toDegrees( double angle ){
		return angle/Math.PI*180.0;
	}
	
	/**
	 * Shortest distance between two GPS points
	 * maybe for short distances is no need to do such precise calculation
	 * aplication may run faster
	 * @param a first point
	 * @param b second point
	 * @return distance in meters
	 */
	public static double distance( Location a, Location b ){
		final double earthR = 6371000; // meters
		double dLat = toRadians(b.getLatitude()-a.getLatitude());
		double dLon = toRadians(b.getLongitude()-a.getLongitude());
		double lat1 = toRadians(a.getLatitude());
		double lat2 = toRadians(b.getLatitude());

		double pom = Math.sin(dLat/2) * Math.sin(dLat/2) +
		             Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double pom2 = 2 * Math.atan2(Math.sqrt(pom), Math.sqrt(1-pom)); 
		double distance = earthR * pom2;
		return distance;
	}
	
	/**
	 * Distance from this position to another
	 * @param a position to calc distance for
	 * @return distance between this and second point in meters
	 */
	/*public double distanceTo( CalcLocation a ){
		return CalcLocation.distance( this, a );
	}*/
}
