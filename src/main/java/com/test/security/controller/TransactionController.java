package com.test.security.controller;

import com.test.security.controller.dto.TransactionDTO;
import com.test.security.controller.dto.filter.TransactionFilter;
import com.test.security.controller.dto.response.TransactionResponseDTO;
import com.test.security.domain.entity.Transaction;
import com.test.security.domain.entity.User;
import com.test.security.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> createTransaction(
            @RequestBody TransactionDTO transactionDTO,
            @AuthenticationPrincipal User user) {

        transactionService.createTransaction(transactionDTO, user);
        return ResponseEntity.status(201).body("Transaction successfully created"); // 201 Created
    }

     @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactions( // <-- Change return type
                     @AuthenticationPrincipal User user,
                     TransactionFilter filter,
                     Pageable pageable) {

        Page<TransactionResponseDTO> transactionsDtoPage = transactionService.findTransactionsByUser(user, filter, pageable);
        return ResponseEntity.ok(transactionsDtoPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransactional(
                    @AuthenticationPrincipal User user,
                    @PathVariable Long id,
                    @RequestBody TransactionDTO transactionDTO
    ){
       return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDTO, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@AuthenticationPrincipal User user, @PathVariable Long id){
        transactionService.deleteTransaction(id, user);
        return ResponseEntity.noContent().build();
    }
}
