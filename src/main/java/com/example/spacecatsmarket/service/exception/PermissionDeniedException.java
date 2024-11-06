package com.example.spacecatsmarket.service.exception;

import java.util.UUID;

public class PermissionDeniedException extends RuntimeException {
    private static final String PERMISSION_DENIED_MESSAGE = "User with id %s does not have permission to %s product with id %s";

    public PermissionDeniedException(Long userId, String action, UUID productId) {
        super(String.format(PERMISSION_DENIED_MESSAGE, userId, action, productId));
    }
}
