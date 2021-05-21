package com.example.matrixproject.Dao.Repository;

import com.example.matrixproject.Dao.Entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long>{


}
