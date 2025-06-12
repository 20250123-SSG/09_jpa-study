package com.younggalee.ocr.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

// https://api.ncloud-docs.com/docs/ai-application-service-ocr-example01#java
// 서비스를 제공하는 ncp 서버로 요청을 하기 위해서(직접요청은 불가능) api gateway(서버의 손님용 출입문) 로 데이터를 주고 받음.


// 사용신청 후, API Gateway 연동
@Component
public class OCRUtil {
    @Value("${ncp.clova-ocr.general.url}")
    private String GENERAL_OCR_URL;
    @Value("${ncp.clova-ocr.general.secretKey}")
    private String GENERAL_SECRET_KEY;

    /**
     * NCP Clova OCR API 호출 후 응답 결과 반환용 메소드
     *
     * @param type - general|template
     * @param path - OCR할 파일의 경로
     * @return - OCR 응답결과(String)
     */
    public String processOCR(String type, String path) {
        try {
            URL url = new URL(GENERAL_OCR_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", GENERAL_SECRET_KEY);

            // java객체를 json문자열화 시켜야함 (요청 본문 작성하려고)
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("version", "V2");
            jsonMap.put("requestId", UUID.randomUUID().toString());
            jsonMap.put("timestamp", System.currentTimeMillis());

            Map<String, Object> imageMap = new HashMap<>();
            imageMap.put("format", "jpg");
            imageMap.put("name", "demo");

            List<Map<String, Object>> imagesList = new ArrayList<>();
            imagesList.add(imageMap);

            jsonMap.put("images", imagesList); // { version, requestId~, timestamp~ , images[] }

            String postParams = jsonMap.toString();

            /*
                "version": "V2",     * 필수
                "requestId": "1234",      *  필수
                "timestamp": "1722225600000",    *  필수
                "lang": "ko",
                "images": [      * 필수
                        {
                            "format": "jpg",      *   필
                                "name": "demo_2",        *   필
                                "url": "https://www.ncloud.com/file-img/vol02/000/614/****************_0001.jpg"
                        }],
                "enableTableDetection": false
             */

            //java객체 >> JSON 문자열로 변환 (ObjectMapper)
            ObjectMapper objectMapper = new ObjectMapper();
            postParams = objectMapper.writeValueAsString(jsonMap); // json 문자열화시켜줌 >> { "version" : "v2", "requestId" : "~~", ~~ }


            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(path);  // 여기에 이미지 path 넣어줌
            writeMultiPart(wr, postParams, file, boundary);   // writeMultiPart 메소드 호출 :
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response);

            return response.toString();

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }


    private void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
}



