package com.sadalsuud.push.infrastructure.gatewayImpl.config;

import cn.hutool.core.io.IoUtil;
import com.google.common.base.Throwables;
import com.sadalsuud.push.domain.support.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Description 远程读取文件
 * @Author sadalsuud
 * @Blog www.sadalsuud.cn
 * @Date 11/12/2023
 */
@Slf4j
@Component
public class FileServiceImpl implements FileService {

    public File getRemoteUrl2File(String path, String remoteUrl) {

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(remoteUrl);
            File file = new File(path, url.getPath());
            inputStream = url.openStream();
            fileOutputStream = new FileOutputStream(file);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                IoUtil.copy(inputStream, fileOutputStream);
            }
            return file;
        } catch (Exception e) {
            log.error("FileService#getRemoteUrl2File fail:{},remoteUrl:{}", Throwables.getStackTraceAsString(e), remoteUrl);
        } finally {
            if (Objects.nonNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("close#inputStream fail:{}", Throwables.getStackTraceAsString(e));
                }
            }
            if (Objects.nonNull(fileOutputStream)) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("close#fileOutputStream fail:{}", Throwables.getStackTraceAsString(e));
                }
            }
        }
        return null;
    }

    /**
     * 读取 远程链接集合 返回有效的File对象集合
     *
     * @param path       文件路径
     * @param remoteUrls cdn/oss文件访问链接集合
     * @return
     */
    public List<File> getRemoteUrl2File(String path, Collection<String> remoteUrls) {
        List<File> files = new ArrayList<>();
        remoteUrls.forEach(remoteUrl -> {
            File file = getRemoteUrl2File(path, remoteUrl);
            if (file != null) {
                files.add(file);
            }
        });
        return files;
    }
}
