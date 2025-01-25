package com.pjatk.s32362Bank;

public class TransactionResult {
    private final int status;
    private final String message;

    public TransactionResult(int status, String message) {
        this.status = status;
        this.message = message;
        System.out.println("message: " + message);
    }

    public int getStatus() {
        return status;
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
