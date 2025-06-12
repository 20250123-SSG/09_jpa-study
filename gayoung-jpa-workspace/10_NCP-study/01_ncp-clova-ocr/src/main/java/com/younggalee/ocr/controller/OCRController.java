package com.younggalee.ocr.controller;

import com.younggalee.ocr.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class OCRController {

    private final FileUtil fileUtil;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(String type, MultipartFile file) {

        //파일 저장
        Map<String, String> map = fileUtil.fileupload("ocr", file);
        // 저장된 파일의 path : map.get("filePath") + "/" + map.get("filesystemName")

        // OCR API 호출 : https://api.ncloud-docs.com/docs/ai-application-service-ocr-example01#java
        // 여기 저기 사용할때마다 작성하기는 그러니까 util 클래스 파일로 만들어서 사용하기
    }


}
