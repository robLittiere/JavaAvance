package com.cergy.javaav.Services;

import com.cergy.javaav.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public  interface CategoryRepository extends CrudRepository<Category, Long> {

    Page<Category> findAll (Pageable pageable);

}