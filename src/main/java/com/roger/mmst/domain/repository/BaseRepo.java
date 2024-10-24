package com.roger.mmst.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepo<E, ID> extends PagingAndSortingRepository<E, ID>, QuerydslPredicateExecutor<E>, JpaRepository<E, ID> {
}
