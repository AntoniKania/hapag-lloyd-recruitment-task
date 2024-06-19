package com.example.demo.service;

import com.example.demo.dao.Account;
import com.example.demo.dto.AccountDto;
import com.example.demo.dto.ModifyAccountDto;
import com.example.demo.exception.InvalidUsernameException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountDto accountDto) {
        Account account = new Account(accountDto.username(), accountDto.gender(), accountDto.age(), LocalDateTime.now());
        Account createdAccount;
        try {
            createdAccount = accountRepository.saveAndFlush(account);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidUsernameException("Invalid username!"); // naive assumption
        }
        return createdAccount;
    }

    public AccountDto getAccount(Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Account account = accountOpt.get();
        return new AccountDto(account.getUsername(), account.getGender(), account.getAge());
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public AccountDto modifyAccount(Long id, ModifyAccountDto accountDto) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Account account = accountOpt.get();
        if (accountDto.gender() != null) {
            account.setGender(accountDto.gender());
        }
        if (accountDto.age() != null) {
            account.setAge(accountDto.age());
        }
        Account updatedAccount = accountRepository.saveAndFlush(account);
        return new AccountDto(updatedAccount.getUsername(), updatedAccount.getGender(), updatedAccount.getAge());
    }
}
