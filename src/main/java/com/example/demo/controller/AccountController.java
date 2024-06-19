package com.example.demo.controller;

import com.example.demo.dao.Account;
import com.example.demo.dto.AccountDto;
import com.example.demo.dto.ModifyAccountDto;
import com.example.demo.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.createAccount(accountDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<AccountDto> deleteAccount(@PathVariable Long id, @RequestBody ModifyAccountDto accountDto) {
        return ResponseEntity.ok(accountService.modifyAccount(id, accountDto));
    }
}
