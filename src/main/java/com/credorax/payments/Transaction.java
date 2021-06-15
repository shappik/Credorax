package com.credorax.payments;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class Transaction {

//    {
//        invoice: 1234567,
//                amount: 1299,
//            currency: 'EUR',
//            cardholder: {
//                  name: 'First Last',
//                  email: 'email@domain.com'
//    },
//        card: {
//            pan: '4200000000000001',
//                    expiry: '0624',
//                    cvv: '789'
//        }
//    }
    @Id
    private String id;
    private String invoice;
    private int amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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

    public Transaction(String invoice, int amount, CardHolder cardHolder, Card card) {
        this.id = UUID.randomUUID().toString();
        this.invoice = invoice;
        this.amount = amount;
        this.cardHolder = cardHolder;
        this.card = card;
    }
}
