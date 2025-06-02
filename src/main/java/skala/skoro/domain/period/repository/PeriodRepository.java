package skala.skoro.domain.period.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skala.skoro.domain.period.entity.Period;
import skala.skoro.domain.period.entity.Unit;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PeriodRepository extends JpaRepository<Period, Long> {
    // 연도와 Unit으로 가장 최근 orderInYear 조회
    Optional<Period> findTopByYearAndUnitOrderByOrderInYearDesc(Integer year, Unit unit);

    // 현재 진행 중이거나 다가올 평가 기간이 있는지 확인
    @Query("""
        SELECT p FROM Period p
        WHERE p.startDate >= :today
        OR (p.startDate <= :today AND p.endDate >= :today)
        ORDER BY p.startDate ASC
        """)
    List<Period> findUpcomingOrOngoingPeriods(@Param("today") LocalDate today);

}
