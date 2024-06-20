package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountPaginationRepository extends PagingAndSortingRepository<Account, Long> {

}
