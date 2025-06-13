package com.ino.ncp_ocr.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OCRUtil {

    @Value("${ncp.clova-ocr.general.url}")
    private String GENERAL_OCR_URL;

    @Value("${ncp.clova-ocr.general.secretKey}")
    private String GENERAL_OCR_SECRET_KEY;

    @Value("${ncp.clova-ocr.template.url}")
    private String TEMPLATE_OCR_URL;

    @Value("${ncp.clova-ocr.template.secretKey}")
    private String TEMPLATE_OCR_SECRET_KEY;

    public String processOCR(String type, String imageFile) {
        try {
            URL url;
            if ("general".equals(type)) {
                url = new URL(GENERAL_OCR_URL);
            } else if ("template".equals(type)) {
                url = new URL(TEMPLATE_OCR_URL);
            } else {
                throw new IllegalArgumentException("type value is not formatted");
            }
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            if ("general".equals(type)) {
                con.setRequestProperty("X-OCR-SECRET", GENERAL_OCR_SECRET_KEY);
            } else if ("template".equals(type)) {
                con.setRequestProperty("X-OCR-SECRET", TEMPLATE_OCR_SECRET_KEY);
            } else {
                throw new IllegalArgumentException("type value is not formatted");
            }
            // JSON 방식으로 JSON 문자열화 시키는거 => Jackson 방식으로
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("version", "V2");
            jsonMap.put("requestId", UUID.randomUUID().toString());
            jsonMap.put("timestamp", System.currentTimeMillis());

            Map<String, Object> imageMap = new HashMap<>();
            imageMap.put("format", "jpg");
            imageMap.put("name", "demo");

            List<Map<String, Object>> imagesList = new ArrayList<>();
            imagesList.add(imageMap);

            jsonMap.put("images", imagesList); // { vesion:V2, requestId:xxx, timestamp:xxx, .., images:[] }

            //          Jackson(ObjectMapper)
            // Java 객체(Map) =====> JSON 문자열 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String postParams = objectMapper.writeValueAsString(jsonMap); // '{ "vesion":"V2", "requestId":"xxx", timestamp:xxx, .., images:[] }

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            // multipart/form-data 형태의 요청 메세지 작성
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();
            // --------------

            // 2. response
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            // read response(one line)
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
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

            // file -> binary data
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes()); // boundary 3
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
}
