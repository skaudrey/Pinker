package com.example.pinker.safeProtect;
import java.util.Timer;
import java.util.TimerTask;

import com.example.pinker.Activity.LoginActivity;
import com.example.pinker.fileservice.FileHelper;

public class TimeOut {
	
	Timer timer;
	String contactphoneno;
    public TimeOut(String str) {
    	contactphoneno=str;
    	timer = new Timer();
        timer.schedule(timerTask, 3*60*1000);
    }
    
    TimerTask timerTask=new TimerTask() {		
		@Override
		public void run() {			
			SendMessage sendMessage=new SendMessage(contactphoneno);
			//ConfirmStatus.confirmstatus.finish();
	        cancel();//Terminate the timer thread
		}
	};
	public void cancel(){
		timer.cancel();
	}
	/*超时响应
	 public TimeOut(){
			ExecutorService executor = Executors.newSingleThreadExecutor();  
		    FutureTask<Boolean> future =  (FutureTask<Boolean>) executor.submit(
		    		new Callable<Boolean>() {//使用Callable接口作为构造参数  
		             public Boolean call() {  
		            	 //要做的事
					 return true;
		           }});  
		    executor.submit(future);
		    //在这里可以做别的任何事情  
		    try {  
		        future.get(60*3*1000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为三分钟。同样可以用future.get()，不设置执行超时时间取得结果  
		    } catch (InterruptedException e) {  
		    	e.printStackTrace();
		    } catch (ExecutionException ee) {  
		    	ee.printStackTrace();
		    } catch (TimeoutException te) {  
		    	SendMessage sendMessage=new SendMessage("13554003132","危险!");//响应超时要发短信
		    } finally {  
		        executor.shutdown();  
		    }  
	 }*/
	
	
}