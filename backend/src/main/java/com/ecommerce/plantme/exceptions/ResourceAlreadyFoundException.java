package com.ecommerce.plantme.exceptions;

import lombok.Data;

public class ResourceAlreadyFoundException extends RuntimeException {

    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceAlreadyFoundException() {
    }

    public ResourceAlreadyFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s Already found with %s: %s", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceAlreadyFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s Already found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
