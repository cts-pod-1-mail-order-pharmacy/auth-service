package com.cts.mailorderpharmacy.authservice.repository;

import com.cts.mailorderpharmacy.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
