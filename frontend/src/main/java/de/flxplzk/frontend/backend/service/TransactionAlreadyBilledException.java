package de.flxplzk.frontend.backend.service;

class TransactionAlreadyBilledException extends RuntimeException {
    TransactionAlreadyBilledException(String s) {
        super(s);
    }
}
