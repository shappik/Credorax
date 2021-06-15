package com.credorax.payments;

public class TransactionResult {
    private Boolean approved;

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public TransactionResult(Boolean approved) {
        this.approved = approved;
    }
}
