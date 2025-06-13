package com.ino.ncp_ocr.controller;

import com.ino.ncp_ocr.util.FileUtil;
import com.ino.ncp_ocr.util.OCRUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OCRController {

    private final FileUtil fileUtil;
    private final OCRUtil ocrUtil;

    @PostMapping("/upload")
    public ResponseEntity<?> fileUpload(String type, MultipartFile file){
        Map<String, String> map = fileUtil.fileupload("ocr", file);
        String response = ocrUtil.processOCR(type,map.get("filePath") + "/" + map.get("filesystemName"));

        Map<String, Object> responseMessage = new HashMap<>();

        responseMessage.put("message", file.getOriginalFilename() + "이미지가 정상적으로 처리되었습니다.");

        responseMessage.put("result", response);
        return ResponseEntity
                .ok()
                .body(responseMessage);
    }
}
