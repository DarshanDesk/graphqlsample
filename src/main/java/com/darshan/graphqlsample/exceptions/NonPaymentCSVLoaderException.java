package com.darshan.graphqlsample.exceptions;

import graphql.GraphQLError;

public class NonPaymentCSVLoaderException extends RuntimeException{
    public NonPaymentCSVLoaderException() {
        super();
    }

    public NonPaymentCSVLoaderException(String message) {
        super(message);
    }

    public NonPaymentCSVLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonPaymentCSVLoaderException(Throwable cause) {
        super(cause);
    }

    protected NonPaymentCSVLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
