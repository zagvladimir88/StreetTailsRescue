package com.zagvladimir.service.image;

import com.zagvladimir.configuration.GoogleCSConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;



class ImageServiceImplTest {

    @Mock
    private  GoogleCSConfig googleCSConfig;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void uploadFile() {

    }
}