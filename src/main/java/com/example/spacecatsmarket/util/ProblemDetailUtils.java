package com.example.spacecatsmarket.util;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public class ProblemDetailUtils {

    public static ResponseEntity<ProblemDetail> createProblemDetailResponse(
        HttpStatus status, String title, String message, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(title);
        problemDetail.setDetail(message);
        String path = request.getDescription(false).replace("uri=", "");
        problemDetail.setInstance(URI.create(path));
        problemDetail.setProperty("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(problemDetail);
    }
}
