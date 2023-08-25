package com.care.sekki.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDAO extends JpaRepository<ReviewDTO, Long> {
	
}
