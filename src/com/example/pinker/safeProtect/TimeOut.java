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
	/*��ʱ��Ӧ
	 public TimeOut(){
			ExecutorService executor = Executors.newSingleThreadExecutor();  
		    FutureTask<Boolean> future =  (FutureTask<Boolean>) executor.submit(
		    		new Callable<Boolean>() {//ʹ��Callable�ӿ���Ϊ�������  
		             public Boolean call() {  
		            	 //Ҫ������
					 return true;
		           }});  
		    executor.submit(future);
		    //���������������κ�����  
		    try {  
		        future.get(60*3*1000, TimeUnit.MILLISECONDS); //ȡ�ý����ͬʱ���ó�ʱִ��ʱ��Ϊ�����ӡ�ͬ��������future.get()��������ִ�г�ʱʱ��ȡ�ý��  
		    } catch (InterruptedException e) {  
		    	e.printStackTrace();
		    } catch (ExecutionException ee) {  
		    	ee.printStackTrace();
		    } catch (TimeoutException te) {  
		    	SendMessage sendMessage=new SendMessage("13554003132","Σ��!");//��Ӧ��ʱҪ������
		    } finally {  
		        executor.shutdown();  
		    }  
	 }*/
	
	
}