package com.feng.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

//file类代表硬盘上一个文件或者文件夹
public class FileListAndRead {
	public static void main(String[] args) {
		//listFile("E:\\");
	}
	public static void listFile(String filePath){
		File f=new File(filePath);
		if(f.isFile()){
			System.out.println(f.getName()+"---------"+f.getAbsolutePath());
		}else if(f.isDirectory()){
			File[] fs=f.listFiles();
			for(File ee:fs){
				if(ee.isFile()){
					System.out.println(ee.getName()+"---------"+ee.getAbsolutePath());
				}else{
					 listFile(ee.getAbsolutePath());
	                 System.out.println("\t<DIR>\t"+ee.getName());
				}
			}
		}
	}
	public static void readFile(String filePath){
		File file = new File(filePath);
		InputStreamReader read;
        try {
			read = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader bufferedReader = new BufferedReader(read);
	        String str = null;
	        while((str = bufferedReader.readLine()) != null){
	            String strr = str.trim();
	            //String[] strs=strr.split("@@");
	            System.out.println(strr);
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
