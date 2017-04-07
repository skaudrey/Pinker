package com.example.pinker.fileservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.util.Log;

public class FileHelper {
	private Context context;  
    public FileHelper(Context context) throws IOException {
    	super();  
        this.context = context;
        try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    String sep="\r\n";
    String filePath = "/sdcard/Pinker/";
    File userFile;
    String userFilePath;
    File checkFile;
    String checkFilePath;
    File pwdFile;
    String pwdFilePath;
    File routesFile;
    String routesFilePath;
	public static void makeRootDirectory(String filePath) {
	     File file = null;
	     try {
	         file = new File(filePath);
	         if (!file.exists()) {
	             file.mkdir();
	         }
	     } catch (Exception e) {
	         Log.i("error:", e+"");}
    }
	// 生成文件
	public File makeFilePath(String fileName) {
	     File file = null;
	     try {
	         file = new File(filePath + fileName);
	         if (!file.exists()) {
	             file.createNewFile();
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     return file;
	 }
	//初始化本地文件夹文件
	public void init() throws Exception {  
		makeRootDirectory(filePath);
		userFile=makeFilePath("user.txt");
		userFilePath=filePath+"user.txt";
		checkFile=makeFilePath("check.txt");
		checkFilePath=filePath+"check.txt";
		pwdFile=makeFilePath("pwd.txt");
		pwdFilePath=filePath+"pwd.txt";
		routesFile=makeFilePath("routes.txt");
		routesFilePath=filePath+"routes.txt";
		//以私有方式读写数据，创建出来的文件只能被该应用访问
		String info="无"+sep+"无"+sep+"无"+sep+"无"+sep+"无"+sep
					+"无"+sep+"无"+sep+"无"+sep+"无";
        if(readFile(userFilePath).equals("")){
        	writeFile(userFilePath,info);
		}
	}
	//写入txt文件
	public void writeFile(String filePath,String content) throws IOException{
		FileOutputStream fou = new FileOutputStream(filePath); 
        fou.write(content.getBytes());       
        fou.close();
	}
	//读取txt文件
	public String readFile(String filePath) throws Exception {
		String res="";
		try{ 
			FileInputStream fin = new FileInputStream(filePath);
			    int length = fin.available(); 
			    byte [] buffer = new byte[length]; 
			    fin.read(buffer);
			    res = EncodingUtils.getString(buffer, "UTF-8");
			    fin.close();  
			    }catch(Exception e){ 
			           e.printStackTrace();}
		return res;
    }
	//读取user.txt中特定项
    public String getUserItem(int number) throws Exception{
          String buffer=readFile(userFilePath);
		  String[]str;
		  str=buffer.split(sep);
		  return str[number];
    }
    //写入个人信息user.txt
    public void saveUserFile(
    		String c,//ID
    		String c0,//用户名
    		String c1,//密码
    		String c2,//电话
    		String c3,//性别
    		String c4,//生日
    		String c5,//年龄
    		String c6,//联系人
    		String c7,//个性签名
    		String c8//信用等级
    		) throws Exception 
    {
    	  String info=readFile(userFilePath);
		  String[]str;
		  String buffer="";
		  str=info.split(sep);
		  String C[]={c,c0,c1,c2,c3,c4,c5,c6,c7,c8};
		  for(int i=0;i<9;i++){
			  if(!(C[i].equals(""))){
				  str[i]=C[i];
				  buffer=buffer+str[i]+sep;
			  }
		  }
	      writeFile(userFilePath,buffer);
    }

    //写入是否自动登录check.txt
    public void saveCheckCondition(String st) throws IOException{
	      writeFile(checkFilePath,st);
    }
    //返回状态自动登录check.txt
    public boolean getCheckCondition() throws Exception{
		String st=readFile(checkFilePath);
		if(st.equals("1")){
			return true;
		}
    	return false;
    }
    //写入是否记住密码pwd.txt
    public void savePwdCondition(String st) throws IOException{
	      writeFile(pwdFilePath,st);
    }
    //返回状态记住密码pwd.txt
    public boolean getPwdCondition() throws Exception{
		String st=readFile(pwdFilePath);
		if(st.equals("1")){
			return true;
		}
    	return false;
    }
    //写入routes.txt
    public void saveRoutes(){
    	
    }
    //读取routes.txt
    public void getRoutes(){
    	
    }
}
