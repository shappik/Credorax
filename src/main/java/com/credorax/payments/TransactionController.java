package com.credorax.payments;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    @Value("${audit.filename}")
    private String auditFileName;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    Iterable<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @PostMapping
    ResponseEntity postTransaction(@Valid @RequestBody Transaction transaction) {
        MaskAndAuditTransaction(transaction);
        EncodeTransaction(transaction);
        transactionRepository.save(transaction);
        return ResponseEntity.ok(new TransactionResult(true));
    }

    private void MaskAndAuditTransaction(Transaction transaction) {
        String cardHolderName = transaction.getCardHolder().getName();
        String maskedCardHolderName = "";
        for (int i=0;i<cardHolderName.length();i++) {
            maskedCardHolderName += "*";
        }

        String cardPan = transaction.getCard().getPan();
        String maskedCardPan =cardPan.replaceAll("\\w(?=\\w{4})", "*");

        String cardExpiry = transaction.getCard().getExpiry();
        String maskedCardExpiry = "";
        for (int i=0;i<cardExpiry.length();i++) {
            maskedCardExpiry += "*";
        }

        String cardCvv = transaction.getCard().getCvv();

        transaction.getCardHolder().setName(maskedCardHolderName);
        transaction.getCard().setPan(maskedCardPan);
        transaction.getCard().setExpiry(maskedCardExpiry);
        transaction.getCard().setCvv("");

        AuditTransaction(transaction);

        transaction.getCardHolder().setName(cardHolderName);
        transaction.getCard().setPan(cardPan);
        transaction.getCard().setExpiry(cardExpiry);
        transaction.getCard().setCvv(cardCvv);
    }

    private void EncodeTransaction(Transaction transaction) {
        String encodedCardHolderName = Base64.getEncoder().encodeToString(transaction.getCardHolder().getName().getBytes());
        String encodedCardPan = Base64.getEncoder().encodeToString(transaction.getCard().getPan().getBytes());
        String encodedExpiryDate = Base64.getEncoder().encodeToString(transaction.getCard().getExpiry().getBytes());

        transaction.getCardHolder().setName(encodedCardHolderName);
        transaction.getCard().setPan(encodedCardPan);
        transaction.getCard().setExpiry(encodedExpiryDate);
        transaction.getCard().setCvv("");
    }

    private void AuditTransaction(Object object) {
        String transactionJson = new Gson().toJson(object);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(auditFileName, true));
            writer.append(transactionJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("approved:", "false");
        errors.put("errors:", "true");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        AuditTransaction(errors);
        return errors;
    }
}
