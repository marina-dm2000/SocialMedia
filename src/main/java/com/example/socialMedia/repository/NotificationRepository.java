package com.example.socialMedia.repository;

import com.example.socialMedia.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByToUser_Id(Long userId);
}
