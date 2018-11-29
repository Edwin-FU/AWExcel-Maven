package com.carrefour.AWExcel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class ConectSSH {

		/**
		 * SFTP帮助类
		*/
	    private static Log log = LogFactory.getLog(ConectSSH.class);
	    
	    /**
	     * 连接sftp服务器
	     * @param host 远程主机ip地址
	     * @param port sftp连接端口，null 时为默认端口
	     * @param user 用户名
	     * @param password 密码
	     * @return
	     * @throws JSchException 
	     */
	    public static Session connect(String host, Integer port, String user, String password) throws JSchException{
	        Session session = null;
	        try {
	            JSch jsch = new JSch();
	            if(port != null){
	                session = jsch.getSession(user, host, port.intValue());
	            }else{
	                session = jsch.getSession(user, host);
	            }
	            session.setPassword(password);
	            //设置第一次登陆的时候提示，可选值:(ask | yes | no)
	            session.setConfig("StrictHostKeyChecking", "no");
	            //15秒连接超时
	            session.connect(15000);
	        } catch (JSchException e) {
	            e.printStackTrace();
	            System.out.println("SFTPUitl 获取连接发生错误");
	            throw e;
	        }
	        return session;
	    }
	    
	    /**
	     * sftp上传文件(夹)
	     * @param directory
	     * @param uploadFile
	     * @param sftp
	     * @throws Exception 
	     */
	    public static void upload(String directory, String uploadFile, ChannelSftp sftp) throws Exception{
	        System.out.println("sftp upload file [directory] : "+directory);
	        System.out.println("sftp upload file [uploadFile] : "+ uploadFile);
	        File file = new File(uploadFile);
	        if(file.exists()){
	            //这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
	            try {
	                Vector content = sftp.ls(directory);
	                if(content == null){
	                    sftp.mkdir(directory);
	                }
	            } catch (SftpException e) {
	                sftp.mkdir(directory);
	            }
	            //进入目标路径
	            sftp.cd(directory);
	            if(file.isFile()){
	                InputStream ins = new FileInputStream(file);
	                //中文名称的
	                sftp.put(ins, new String(file.getName().getBytes(),"UTF-8"));
	                //sftp.setFilenameEncoding("UTF-8");
	            }else{
	                File[] files = file.listFiles();
	                for (File file2 : files) {
	                    String dir = file2.getAbsolutePath();
	                    if(file2.isDirectory()){
	                        String str = dir.substring(dir.lastIndexOf(file2.separator));
	                        directory = directory + str;
	                    }
	                    upload(directory,dir,sftp);
	                }
	            }
	        }
	    }
	    
	    /**
	     * sftp下载文件（夹）
	     * @param directory 下载文件上级目录
	     * @param srcFile 下载文件完全路径
	     * @param saveFile 保存文件路径
	     * @param sftp ChannelSftp
	     * @throws UnsupportedEncodingException
	     */
	    private static LsEntry entry = null;
	    public static void download(String directory,String srcFile, String saveFile, ChannelSftp sftp,LsEntry lsEntry) throws UnsupportedEncodingException {
	    	Vector conts = null;
	        try{
	        	System.out.println(srcFile);
	            conts = sftp.ls(srcFile);
	        } catch (SftpException e) {
	            e.printStackTrace();
	            log.debug("ChannelSftp sftp罗列文件发生错误",e);
	        }
	        File file = new File(saveFile);
	        if(!file.exists()) file.mkdir();
	        //是文件 isFile=true
	        boolean isFile = false;
	        try {
				sftp.cd(srcFile);
			} catch (SftpException e) {
				if (e.id == 4) {
					isFile = true;
				} else {
					isFile = false;
				}
			}
//	        if(srcFile.indexOf("OPT") > -1){
	        if(isFile){
	            try {
	                sftp.get(srcFile, saveFile);
	                writeFile(new File(saveFile + "\\" + new String(lsEntry.getFilename().getBytes(),"UTF-8")),
	                		"arrivalTime:" + lsEntry.getAttrs().getMtimeString());
	            } catch (SftpException e) {
	                e.printStackTrace();
	                log.debug("ChannelSftp sftp下载文件发生错误",e);
	            }
	        }else{
	        //文件夹(路径)
	            for (Iterator iterator = conts.iterator(); iterator.hasNext();) {
	            	entry =  (LsEntry) iterator.next();
	                String filename = new String(entry.getFilename().getBytes(),"UTF-8");
	                System.out.println(isFile);
//	                if(!(filename.indexOf("OPT") > -1 || filename.indexOf(".") > -1)){
	                if(entry.getAttrs().isDir() && !(filename.indexOf(".") > -1)){
	                    directory = directory + "/" + filename;
	                    srcFile = directory;
	                    saveFile = saveFile + "/" + filename;
	                }else{
	                    //扫描到文件名为".."这样的直接跳过
	                    String[] arrs = filename.split("\\.");
	                    if((arrs.length > 0) && (arrs[0].length() > 0)){
	                    	System.out.println("---"+entry.getAttrs());
	                        srcFile = directory + "/" + filename;
	                    }else{
	                        continue;
	                    }
	                }
	                download(directory, srcFile, saveFile, sftp, entry);
	            	/*LsEntry obj =  (LsEntry) iterator.next();
	                String filename = new String(obj.getFilename().getBytes(),"UTF-8");
	            	System.out.println(filename);*/
	            }
	        }
	    }
	    
	    public static void writeFile(File file,String value) {
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
				writer.append(value);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
