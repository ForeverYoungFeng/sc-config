package com.feng.test;

import java.io.File;




public class CopyOfIncrementPublish {
	
	public static void main(String[] args) {
		/**
		 * 1. 通过SVN客户端（TortoiseSVN ）同步项目到本地；
		 * 2. 通过 TortoiseSVN 的“show log”功能查看版本库；
		 * 3. 选择要做增量的版本号，然后右键选择“Compare revisions”会显示“Changed Files”列表；
		 * 注意：更新的开始版本要是上一次更新的结束版本到需要的版本
		 * 4. 全选或部分选择，然后右键选择“Export selection to...”，然后导出到指定的文件夹;
		 * 
		 * 至此，java版本（src）及资源文件（WebContent）的增量成功导出到指定文件夹；
		 * 
		 * 5. 通过 createIncrement 方法把增量java文件对应的class文件及资源文件拷贝到新的增量中
		 */
		String classesPath = "D:/apache-tomcat-run/apache-tomcat-zxflow/webapps/TTS2/WEB-INF/classes/";
		String javaIncrement = "E:/2017/";
//		String classesPath = "D:/apache-tomcat-run/apache-tomcat-zxflow/webapps/HarleyCorp/WEB-INF/classes/";
//		String javaIncrement = "E:/0217/";
		createIncrement(classesPath, javaIncrement);
		System.out.println("finish.");
	}
	
	/**
	 * 生成增量 classes 到 WebContent/WEB-INF/classes 中
	 * @param classesPath  -- 项目的 classes 路径
	 * @param javaIncrement  -- SVN导出的增量文件路径，文件一般包括src和WebContent两个文件夹
	 */
	public static void createIncrement(String classesPath, String javaIncrement){
		String svnClasses = javaIncrement +"WebContent/WEB-INF/classes/";
		FileUtil.createDir(svnClasses);//创建 SVN classes 目录
		String srcPath = javaIncrement +"src/";
		createPackageAndFiles(classesPath, srcPath, svnClasses, "");
	}
	/** 创建 package 及其文件 **/
	private static void createPackageAndFiles(String classesPath, String srcPath,
			String svnClasses, String packages){
		String packagePath = srcPath + packages;
		String fromPath = classesPath + packages;
		String toPath = svnClasses + packages;
		//
		File packageFile = new File(packagePath);
		File[] children = packageFile.listFiles();
		String newPackages = null;
		String fileName = null;
		for(File child : children){
			fileName = child.getName();
			if( child.isDirectory() ){
				//文件夹（包）
				newPackages = packages + fileName + File.separator;
				createPackageAndFiles(classesPath, srcPath, svnClasses, newPackages);
			} else {
				//文件
				fileName = FileUtil.changeExtTo(fileName, "class");
				FileUtil.copyFile(fromPath+fileName, toPath+fileName);
			}
		}
	}
}
