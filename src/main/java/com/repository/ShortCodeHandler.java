package com.repository;

import com.dto.ShortCodeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortCodeHandler extends JpaRepository<ShortCodeData,String> {
    @Query("select s from shortcodedata s where s.short_code = ?1")
    ShortCodeData findByShortCode(String shortcode);
}
