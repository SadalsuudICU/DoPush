package com.sadalsuud.push.infrastructure.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @Description
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 2024/3/6
 * @Project DoPush-Server
 */
public class SpringFileUtils {

    private SpringFileUtils() {
    }

    /**
     * multipartFile 转成 File 对象
     *
     * @param multipartFile
     * @return
     */
    public static File getFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        File file = null;
        if (fileName != null) {
            file = new File(fileName);
        }
        OutputStream out = null;
        try {
            if (file != null) {
                out = Files.newOutputStream(file.toPath());
            }
            byte[] ss = multipartFile.getBytes();
            for (byte s : ss) {
                if (out != null) {
                    out.write(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(out)) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
