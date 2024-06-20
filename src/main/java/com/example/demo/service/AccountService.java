package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import com.example.demo.model.Account;
import com.example.demo.dto.CreateAccountDto;
import com.example.demo.dto.ModifyAccountDto;
import com.example.demo.exception.InvalidUsernameException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AccountPaginationRepository;
import com.example.demo.repository.AccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountPaginationRepository accountPaginationRepository;

    public AccountService(AccountRepository accountRepository, AccountPaginationRepository accountPaginationRepository) {
        this.accountRepository = accountRepository;
        this.accountPaginationRepository = accountPaginationRepository;
    }

    public Account createAccount(CreateAccountDto createAccountDto) {
        Account account = new Account(createAccountDto.username(), createAccountDto.gender(), createAccountDto.age(), LocalDateTime.now());
        Account createdAccount;
        try {
            createdAccount = accountRepository.saveAndFlush(account);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidUsernameException("Username exists!"); // naive assumption
        }
        return createdAccount;
    }

    public AccountDto getAccounts(Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Account account = accountOpt.get();
        return new AccountDto(account.getUsername(), account.getGender(), account.getAge(), account.getCreationDate());
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
        return new AccountDto(updatedAccount.getUsername(), updatedAccount.getGender(), updatedAccount.getAge(),
                updatedAccount.getCreationDate());
    }

    public Page<AccountDto> getAccounts(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        List<AccountDto> content = accountPaginationRepository.findAll(pageable).stream()
                .map(e -> new AccountDto(e.getUsername(), e.getGender(), e.getAge(), e.getCreationDate()))
                .toList();
        return new PageImpl<>(content, PageRequest.of(pageNumber, pageSize), accountRepository.count());
    }
}
