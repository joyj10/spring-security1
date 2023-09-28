package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

// CRUD 함수를 JpaRepository 들고 있음
// @Repository 어노테이션 없어도 IoC 됨. 이유는 JpaRepository 상속 했기 때문에
public interface UserRepository extends JpaRepository<User, Integer> {
}
