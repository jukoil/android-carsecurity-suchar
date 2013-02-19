package suchar.carsecurity;

import suchar.carsecurity.MailActivity;
import android.app.Activity;
import android.content.Intent;
import android.telephony.gsm.SmsManager;

@SuppressWarnings("deprecation")
public class Communication extends Activity{
		
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
		
	   //TODO
	   public static void sendEmail__(String []emailAddresses, String subject, String message)
	   {
		   Intent i = new Intent(Intent.ACTION_SEND);
		   i.setType("message/rfc822");
		   i.putExtra(Intent.EXTRA_EMAIL  , emailAddresses);
		   i.putExtra(Intent.EXTRA_SUBJECT, subject);
		   i.putExtra(Intent.EXTRA_TEXT   , message);
		   try {
//???		       startActivity(Intent.createChooser(i, "Send mail..."));
		   } catch (android.content.ActivityNotFoundException ex) {
		       //Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		   }	   
	   }
	   
	   
	   public /*static */ void sendEmail(){
		   System.out.println("Trying to send email");
		   	MailActivity m = new MailActivity("juraj.koys@gmail.com", "JUKO881209"); 
		
			String[] toArr = {"jukoil@azet.sk", "juraj.koys@gmail.com"}; 
			m.setTo(toArr); 
			m.setFrom("nehehe@rytmus.com"); 
			m.setSubject("Email from android application"); 
			m.setBody("It works!"); 

		  
			try { 
				//m.addAttachment("/sdcard/filelocation"); 
		
				if(m.send()) { 
				 // Toast.makeText(MailApp.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
					System.out.println("Email was sent successfully.");
				} else { 
				 // Toast.makeText(MailApp.this, "Email was not sent.", Toast.LENGTH_LONG).show();
					System.out.println("Email was not sent.");
				    } 
			  } catch(Exception e) { 
			    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
				//Log.e("MailApp", "Could not send email", e);
				  	System.out.println("There was a problem sending the email.");
		  } 
   
	   }
}
