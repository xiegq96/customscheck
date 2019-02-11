package cn.bobo.fast.common.utils;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.SocketException;
import java.util.Properties;



public class FtpUtil {

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) throws Exception{
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                System.out.println("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("FTP的IP地址可能错误，请正确配置。");
            throw new Exception("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FTP的端口错误,请正确配置。");
            throw new Exception("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }


    /**
     *  检验指定路径的文件是否存在ftp服务器中
     * @param dir--指定路径
     * @param file--指定的文件
     * @param user--ftp服务器登陆用户名
     * @param passward--ftp服务器登陆密码
     * @param ip--ftp的IP地址
     * @param port--ftp的端口号
     * @return
     */

    public static boolean isFTPFileExist(String dir, String file, String user,String passward,String ip,int port) throws Exception{
        FTPClient ftp = new FTPClient();
        try {
            // 连接ftp服务器
            ftp.connect(ip, port);
            // 登陆
            ftp.login(user, passward);
            // 检验登陆操作的返回码是否正确
            if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
                ftp.disconnect();
                return false;
            }
            ftp.enterLocalActiveMode();
            // 设置文件类型为二进制，与ASCII有区别
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置编码格式
            ftp.setControlEncoding("UTF-8");

            // 提取绝对地址的目录以及文件名
            //filePath = filePath.replace("ftp://"+ip+":"+port+"/", "");
            //String dir = filePath.substring(0, filePath.lastIndexOf("/"));
            //String file = filePath.substring(filePath.lastIndexOf("/")+1);

            // 进入文件所在目录，注意编码格式，以能够正确识别中文目录
            ftp.changeWorkingDirectory(new String(dir.getBytes("UTF-8"),FTP.DEFAULT_CONTROL_ENCODING));

            // 检验文件是否存在
            InputStream is = ftp.retrieveFileStream(new String(file.getBytes("UTF-8"),FTP.DEFAULT_CONTROL_ENCODING));
            if(is == null || ftp.getReplyCode() == FTPReply.FILE_UNAVAILABLE){
                return false;
            }

            if(is != null){
                is.close();
                ftp.completePendingCommand();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("FTP通信异常");
        }finally{
            if(ftp != null && ftp.isConnected()){
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /*
     * 从FTP服务器下载文件
     *
     * @param ftpHost FTP IP地址
     * @param ftpUserName FTP 用户名
     * @param ftpPassword FTP用户名密码
     * @param ftpPort FTP端口
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     * @param localPath 下载到本地的位置 格式：H:/download
     * @param fileName 文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName,
                                       String ftpPassword, int ftpPort, String ftpPath, String localPath,
                                       String fileName) throws Exception {

        FTPClient ftpClient = null;

        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

            File localFile = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(fileName, os);
            os.close();
            ftpClient.logout();

        } catch (FileNotFoundException e) {
            System.out.println("没有找到" + ftpPath + "文件");
            e.printStackTrace();
            throw new Exception("没有找到" + ftpPath + "文件");
        } catch (SocketException e) {
            System.out.println("连接FTP失败.");
            e.printStackTrace();
            throw new Exception("连接FTP失败.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件读取错误。");
            e.printStackTrace();
            throw new Exception("文件读取错误。");
        }

    }

    /**
     * Description: 向FTP服务器上传文件
     * @param ftpHost FTP服务器hostname
     * @param ftpUserName 账号
     * @param ftpPassword 密码
     * @param ftpPort 端口
     * @param ftpPath  FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName ftp文件名称
     * @param input 文件流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName,
                                     String ftpPassword, int ftpPort, String ftpPath,
                                     String fileName,InputStream input) throws Exception {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

            ftpClient.storeFile(fileName, input);

            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("文件上传失败。");
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    throw new Exception("关闭FTP失败");
                }
            }
        }
        return success;
    }

}