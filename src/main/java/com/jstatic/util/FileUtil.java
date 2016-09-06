package com.jstatic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件工具类
 *
 * @author 谢俊权
 * @create 2016/2/16 20:01
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final String EMPTY_STRING = "";

    public static boolean write(String content, File file){
        OutputStream os = null;
        try{
            os = new FileOutputStream(file);
            byte[] sources = content.getBytes();
            os.write(sources);
        }catch (Exception e){
            logger.error("failed to write content to file {}", file.getAbsolutePath());
        }finally {
            if(os != null){
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    logger.error("failed to close output stream", e);
                }
            }
        }
        return false;
    }

    public static String read(File file){
        InputStream is = null;
        try{
            is = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[2048];
            int length = -1;
            while((length = is.read(buffer)) != -1){
                sb.append(buffer);
            }
            return sb.toString();
        }catch (Exception e){
            logger.error("failed to getMD5", e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("failed to close input stream", e);
                }
            }
        }
        return EMPTY_STRING;
    }

    public static File createFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            try {
                String parent = file.getParent();
                File parentFile = new File(parent);
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (Exception e) {
                logger.error("failed to create new file {}", filePath);
            }
        }
        return new File(filePath);
    }
}
