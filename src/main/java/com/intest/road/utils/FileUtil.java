package com.intest.road.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author PengHang
 * @date 2018/11/20
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String encoding = "UTF-8";
    public static String readTxtFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            File file = new File(filePath);
            // 获取父目录
            File fileParent = file.getParentFile();
            // 判断是否存在
            if (!fileParent.exists()) {
                // 创建父目录文件
                fileParent.mkdirs();
            }
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                boolean firstLine = true;
                while ((lineText = bufferedReader.readLine()) != null) {
                    if (!firstLine) {
                        content.append("\r\n");
                    }
                    firstLine = false;
                    content.append(lineText);
                }
                read.close();
            } else {
                logger.info("文件不存在{}", filePath);
            }
        }
        catch (IOException e) {
            logger.error("读取文件失败", e);
        }
        return content.toString();
    }
    public static boolean writeFile(String filePath, String content) {
        BufferedWriter bw = null;
        try {
            File file = new File(filePath);
            // 获取父目录
            File fileParent = file.getParentFile();
            // 判断是否存在
            if (!fileParent.exists()) {
                // 创建父目录文件
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(file), encoding);
            streamWriter.write(content);
            streamWriter.close();
            return true;
        } catch (IOException e) {
            logger.error("写入文件失败", e);
        }
        return false;
    }
}
