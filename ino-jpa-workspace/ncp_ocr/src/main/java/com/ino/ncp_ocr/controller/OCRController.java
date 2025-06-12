package com.ino.ncp_ocr.controller;

import com.ino.ncp_ocr.util.FileUtil;
import com.ino.ncp_ocr.util.OCRUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OCRController {

    private final FileUtil fileUtil;
    private final OCRUtil ocrUtil;

    @PostMapping("/upload")
    public ResponseEntity<?> fileUpload(String type, MultipartFile file){
        Map<String, String> map = fileUtil.fileupload("ocr", file);
        ocrUtil.processOCR(type,map.get("filePath") + "/" + map.get("filesystemName"));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
