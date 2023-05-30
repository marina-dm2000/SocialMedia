package com.example.socialMedia.repository;

import com.example.socialMedia.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
