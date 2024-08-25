package com.david.mbaimbai.farmcollector.exceptions;

public class ItemAlreadyExistException extends RuntimeException {
    public ItemAlreadyExistException(String simpleName,
                                     String cropName) {
        super("%s with name %s already exist".formatted(simpleName, cropName));
    }
}
