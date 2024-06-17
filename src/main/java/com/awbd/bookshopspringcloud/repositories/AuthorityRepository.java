package com.awbd.bookshopspringcloud.repositories;

import com.awbd.bookshopspringcloud.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByAuthority(String roleUser);
}
