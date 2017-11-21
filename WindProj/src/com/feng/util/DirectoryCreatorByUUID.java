package com.feng.util;
import java.io.*;
public class DirectoryCreatorByUUID {
	public  static String  createPathByUUID(String basePath,String UUID){
		//UUID.randomUUID().toString()
		String uuid=UUID.replaceAll("-","");
		StringBuffer middlePath=new StringBuffer(basePath);
		for(int n=0;n<uuid.length();n++){
			middlePath.append("\\"+uuid.substring(n,n+1));
		}
		System.out.println(middlePath.toString());
		File  dir=new File(middlePath.toString());
		dir.mkdirs();
		return dir.getAbsolutePath();
	}

}
