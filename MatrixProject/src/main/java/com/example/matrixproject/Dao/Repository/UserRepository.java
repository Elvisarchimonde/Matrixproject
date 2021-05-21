package com.example.matrixproject.Dao.Repository;


import com.example.matrixproject.Dao.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String username);
}
