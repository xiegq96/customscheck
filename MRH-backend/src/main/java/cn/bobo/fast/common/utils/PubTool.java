package cn.bobo.fast.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;


public class PubTool {
    /**
     * 默认编码方式UTF-8
     */
    public static String DEFAULT_ENCRYPT = "UTF-8";

    public static SimpleDateFormat sdf8=new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdf14=new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat sdf17=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static SimpleDateFormat sdfHmsS=new SimpleDateFormat("HHmmssSSS");

    /**
     * 生成uuid
     * @return
     */
    public static String getGuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
//    public static String getGuid(){
//        return sdf14.format(new Date())+getRandomCharAndNumr(6);
//    }
    /**
     * 生成uuid,过滤'-'字符
     * @return
     */
    public static String getId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

//    /**
//     * 获取随机数
//     * @return
//     */
//    public static String getId(){
//        return sdf14.format(new Date())+getRandomCharAndNumr(6);
//    }

    /**获取classpath1
     * @return
     */
    public static String getClasspath(){
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if(path.indexOf(":") != 1){
            path = File.separator + path;
        }
        return path;
    }
    /**
     * 获取随机字母数字组合
     *
     * @param length
     *            字符串长度
     * @return
     */
    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                str += (char) (65 + random.nextInt(26));// 取得大写字母
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }
    /**
     * 读取到字节数组2
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPropertyByName(String path, String name) {
        String result = "";

        try {
            // 方法一：通过java.util.ResourceBundle读取资源属性文件
            result = java.util.ResourceBundle.getBundle(path).getString(name);
            result=  new String(result.getBytes("iso-8859-1"),"utf-8");
        } catch (Exception e) {
            System.out.println("getPropertyByName2 error:" + name);
        }
        return result;
    }

    /**
     * 文件下载
     * @param response
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void fileDownload(final HttpServletResponse response,
                                    String filePath, String fileName) throws Exception {
        byte[] data = PubTool.toByteArray2(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(
                response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }


    /**
     * 按编码方式写入字符串到文件中
     * @param str
     * @param fileName
     * @param encrypt
     * @return
     */
    public static boolean writeXml(String str, String fileName, String encrypt) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(fos);
            // pw.write(xml);
            pw.print(new String(str.getBytes(), encrypt));
            pw.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeFile(String fileName, String fileContent)
    {
        try
        {
            File f = new File(fileName);

            File fileParent = f.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }

            if (!f.exists())
            {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), DEFAULT_ENCRYPT);
            BufferedWriter writer=new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
}
