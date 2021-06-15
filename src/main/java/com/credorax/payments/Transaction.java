package com.credorax.payments;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.util.UUID;

@Entity
public class Transaction {
    @Id
    private String id;
    @NotBlank(message = "invoice is mandatory")
    private String invoice;
    @NotBlank(message = "amount is mandatory")
    @Positive
    private String amount;
    @OneToOne(cascade = {CascadeType.ALL})
    private CardHolder cardHolder;
    @OneToOne(cascade = {CascadeType.ALL})
    private Card card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Transaction() {
    }

    public Transaction(String invoice, String amount, CardHolder cardHolder, Card card) {
        this.id = UUID.randomUUID().toString();
        this.invoice = invoice;
        this.amount = amount;
        this.cardHolder = cardHolder;
        this.card = card;
    }
}