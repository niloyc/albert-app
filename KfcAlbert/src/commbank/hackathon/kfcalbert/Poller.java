package commbank.hackathon.kfcalbert;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Poller implements Runnable{
	
	private static String tag = ".Poller";
	
	private Handler mHandler;
	private boolean run;
	
	public Poller(Handler h){
		mHandler = h;
		run = true;
	}
	
	@Override
	public synchronized void run() {
		
		
		while(run){
			String result = RESTApi.GET("http://128.199.161.182:3000/api/customers/");
			
			Message msg = new Message();
			msg.obj = result;
			
			if(result !=null)
				mHandler.dispatchMessage(msg);
			else
				Log.d(tag, "Nothing received");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void stop(){
		run = false;
	}
}
