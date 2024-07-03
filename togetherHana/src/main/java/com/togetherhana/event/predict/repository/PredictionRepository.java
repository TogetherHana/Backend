package com.togetherhana.event.predict.repository;

import com.togetherhana.event.predict.entity.EventGame;
import com.togetherhana.event.predict.entity.Prediction;
import com.togetherhana.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    boolean existsByMemberAndEventGame(Member member, EventGame eventGame);

    List<Prediction> findByEventGameAndPredictTeamIdx(EventGame eventGame, Long predictTeamIdx);
}
