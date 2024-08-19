package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c")
    List<Category> getCategoryList();
}
