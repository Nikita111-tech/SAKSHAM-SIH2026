package com.sih.saksham.repository;
import com.sih.saksham.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {}
