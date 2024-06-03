package com.documentupload.Service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentStorageService {

    public void init();
    public void save(MultipartFile file);
    public Resource load(String fileName);
    public boolean delete(String fileName);
    public Stream<Path>loadAll();


}
