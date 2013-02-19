package suchar.carsecurity;

import android.telephony.SmsManager;
import android.widget.Toast;
import android.app.Activity;

public class SMSActivity extends Activity  {
	
	private String phoneNo = ""; 
	
	public void setPhoneNo( String phoneNo ){
		this.phoneNo = phoneNo;
	}
	
	public static boolean sendSMS( String phoneNo, String smsText )
	{
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, smsText, null, null);
			//Toast.makeText(getApplicationContext(), "SMS Sent!",
			//		Toast.LENGTH_LONG).show();
			return true;
		} catch (Exception e) {
			//Toast.makeText(getApplicationContext(),
			//		"SMS faild, please try again later!",
			//		Toast.LENGTH_LONG).show();
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendSMS( String smsText )
	{
		return sendSMS( this.phoneNo, smsText );
	}
}
