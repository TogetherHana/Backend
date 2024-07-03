package com.togetherhana.event.predict.repository;

import com.togetherhana.event.predict.entity.EventGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventGameRepository extends JpaRepository<EventGame, Long> {
}
