package com.zagvladimir.service.image;

import java.net.URL;
import java.util.List;

public interface ImageService {

    public String uploadFile(byte[] imageBytes, Integer itemId, String fileExt);

    public List<URL> getUrls(Integer itemId);
}
