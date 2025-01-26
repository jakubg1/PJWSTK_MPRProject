package com.pjatk.s32362Bank;

public class TransactionResult {
    private final TransactionStatus status;
    private final String message;

    public TransactionResult(TransactionStatus status, String message) {
        this.status = status;
        this.message = message;
        System.out.println("message [" + status + "]: " + message);
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccepted() {
        return status == TransactionStatus.ACCEPTED;
    }

    public boolean isDeclined() {
        return status == TransactionStatus.DECLINED;
    }
}
