package org.example.api_blog.service;

import lombok.RequiredArgsConstructor;
import org.example.api_blog.model.entity.MultipartInputStreamFileResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class PinataService {
    @Value("${pinata.api-key}")
    private String apiKey;

    @Value("${pinata.secret-api-key}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadFile(MultipartFile file) {
        try {
            // REMOVED the < and > here
//            for past url gateway
            String url = "https://api.pinata.cloud/pinning/pinFileToIPFS";

            HttpHeaders headers = new HttpHeaders();
            headers.set("pinata_api_key", apiKey);
            headers.set("pinata_secret_api_key", secretKey);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new MultipartInputStreamFileResource(
                    file.getInputStream(),
                    file.getOriginalFilename()
            ));

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            String cid = (String) response.getBody().get("IpfsHash");

            // REMOVED the < and > here as well
            return "https://olive-realistic-guppy-451.mypinata.cloud/ipfs/" + cid;

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload to Pinata", e);
        }
    }
}
