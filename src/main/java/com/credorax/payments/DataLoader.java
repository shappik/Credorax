package com.credorax.payments;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader {
    private final TransactionRepository transactionRepository;

    public DataLoader(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostConstruct
    private void loadData() {

        Transaction testTransaction1 = new Transaction("1234567", "1299",
                new CardHolder("John Doe", "john@doe.com"),
                new Card("4200000000000001", "0624", "789"));

        Transaction testTransaction2 = new Transaction("1234568", "1599",
                new CardHolder("Dirk Smith", "dirk@smith.com"),
                new Card("4200000000000002", "0829", "789" ));

        transactionRepository.saveAll(List.of(testTransaction1, testTransaction2));
    }
}
