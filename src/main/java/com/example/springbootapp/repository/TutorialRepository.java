package com.example.springbootapp.repository;
import java.util.List;
import com.example.springbootapp.model.Tutorial;
import com.example.springbootapp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial , Long> {
    List<Tutorial> findByTitleContaining(String title);

}
