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
	// �����ļ�
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
	//��ʼ�������ļ����ļ�
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
		//��˽�з�ʽ��д���ݣ������������ļ�ֻ�ܱ���Ӧ�÷���
		String info="��"+sep+"��"+sep+"��"+sep+"��"+sep+"��"+sep
					+"��"+sep+"��"+sep+"��"+sep+"��";
        if(readFile(userFilePath).equals("")){
        	writeFile(userFilePath,info);
		}
	}
	//д��txt�ļ�
	public void writeFile(String filePath,String content) throws IOException{
		FileOutputStream fou = new FileOutputStream(filePath); 
        fou.write(content.getBytes());       
        fou.close();
	}
	//��ȡtxt�ļ�
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
	//��ȡuser.txt���ض���
    public String getUserItem(int number) throws Exception{
          String buffer=readFile(userFilePath);
		  String[]str;
		  str=buffer.split(sep);
		  return str[number];
    }
    //д�������Ϣuser.txt
    public void saveUserFile(
    		String c,//ID
    		String c0,//�û���
    		String c1,//����
    		String c2,//�绰
    		String c3,//�Ա�
    		String c4,//����
    		String c5,//����
    		String c6,//��ϵ��
    		String c7,//����ǩ��
    		String c8//���õȼ�
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

    //д���Ƿ��Զ���¼check.txt
    public void saveCheckCondition(String st) throws IOException{
	      writeFile(checkFilePath,st);
    }
    //����״̬�Զ���¼check.txt
    public boolean getCheckCondition() throws Exception{
		String st=readFile(checkFilePath);
		if(st.equals("1")){
			return true;
		}
    	return false;
    }
    //д���Ƿ��ס����pwd.txt
    public void savePwdCondition(String st) throws IOException{
	      writeFile(pwdFilePath,st);
    }
    //����״̬��ס����pwd.txt
    public boolean getPwdCondition() throws Exception{
		String st=readFile(pwdFilePath);
		if(st.equals("1")){
			return true;
		}
    	return false;
    }
    //д��routes.txt
    public void saveRoutes(){
    	
    }
    //��ȡroutes.txt
    public void getRoutes(){
    	
    }
}
