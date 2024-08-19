package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Category;
import com.himedia.rentmon_back.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CategoryService {
    private final CategoryRepository cr;

    public List<Category> getCategoryList() {
        return cr.getCategoryList();
    }
}
