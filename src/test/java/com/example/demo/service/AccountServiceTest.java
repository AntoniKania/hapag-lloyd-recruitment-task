package com.example.demo.service;

import com.example.demo.dto.CreateAccountDto;
import com.example.demo.dto.ModifyAccountDto;
import com.example.demo.exception.InvalidUsernameException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Gender;
import com.example.demo.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountService accountService;

    @ParameterizedTest
    @MethodSource("provideValidAccountInfo")
    void canCreateNewAccount(CreateAccountDto accountDto) {
        // given
        when(accountRepository.saveAndFlush(any())).thenReturn(new Account(null, null, null, null));
        // when
        accountService.createAccount(accountDto);
        // then
        verify(accountRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void canModifyAge() {
        // given
        final Long accountId = 2L;
        Account account = Mockito.mock(Account.class);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.saveAndFlush(account)).thenReturn(account);
        // when
        accountService.modifyAccount(accountId, new ModifyAccountDto(null, 45));
        // then
        verify(account, times(1)).setAge(45);
        verify(accountRepository, times(1)).saveAndFlush(account);
    }

    @Test
    void canModifyGender() {
        // given
        final Long accountId = 3L;
        Account account = Mockito.mock(Account.class);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.saveAndFlush(account)).thenReturn(account);
        // when
        accountService.modifyAccount(accountId, new ModifyAccountDto(Gender.NOT_SAID, null));
        // then
        verify(account).setGender(Gender.NOT_SAID);
        verify(account, never()).setAge(any());
        verify(accountRepository, times(1)).saveAndFlush(account);
    }

    @Test
    void throwsExceptionWhenDuplicateUsernameProvided() {
        // given
        when(accountRepository.saveAndFlush(any())).thenThrow(new DataIntegrityViolationException("duplicate key username"));
        // when & then
        InvalidUsernameException thrown = assertThrows(InvalidUsernameException.class,
                () -> accountService.createAccount(
                        new CreateAccountDto("existing_username", Gender.NOT_SAID, 43))
        );
        assertEquals("Username exists!", thrown.getMessage());
    }

    @Test
    void canGetAccount() {
        // given
        when(accountRepository.findById(any()))
                .thenReturn(Optional.of(new Account(null, null, null, null)));
        // when
        accountService.getAccount(1L);
        // then
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void throwsNotFoundExceptionWhenAccountNotFound() {
        // given
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        // when & then
        NotFoundException thrown = assertThrows(NotFoundException.class,
                () -> accountService.getAccount(1L)
        );
        assertEquals("Account not found", thrown.getMessage());
    }

    static Stream<CreateAccountDto> provideValidAccountInfo() {
        return Stream.of(
                new CreateAccountDto("valid_username_test_1", Gender.MALE, 22),
                new CreateAccountDto("valid_username_test_2", Gender.FEMALE, 35),
                new CreateAccountDto("valid_username_test_3", Gender.NON_BINARY, 56),
                new CreateAccountDto("valid_username_test_4", Gender.NOT_SAID, 75)
        );
    }
}