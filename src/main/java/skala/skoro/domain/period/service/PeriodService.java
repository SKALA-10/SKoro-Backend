package skala.skoro.domain.period.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.period.dto.TeamEvaluationPeriodResponse;
import skala.skoro.domain.period.dto.PeriodAvailableResponse;
import skala.skoro.domain.period.dto.PeriodCreateAndUpdateRequest;
import skala.skoro.domain.period.entity.Period;
import skala.skoro.domain.period.repository.PeriodRepository;
import skala.skoro.global.exception.CustomException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static skala.skoro.global.exception.ErrorCode.PERIOD_DOES_NOT_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class PeriodService {

    private final PeriodRepository periodRepository;

    public void createPeriod(PeriodCreateAndUpdateRequest request) {
        int lastOrderInYear = periodRepository.findTopByYearAndUnitOrderByOrderInYearDesc(request.getStartDate().getYear(), request.getUnit())
                .map(Period::getOrderInYear)
                .orElse(0);

        periodRepository.save(Period.of(request, lastOrderInYear + 1));
    }

    @Transactional(readOnly = true)
    public List<PeriodAvailableResponse> findPeriodAvailable() {
        return periodRepository.findUpcomingOrOngoingPeriods(LocalDate.now()).stream()
                .map(PeriodAvailableResponse::from)
                .collect(Collectors.toList());
    }

    public void updatePeriod(Long periodId, PeriodCreateAndUpdateRequest request) {
        Period period = periodRepository.findById(periodId)
                .orElseThrow(() -> new CustomException(PERIOD_DOES_NOT_EXIST));

        period.updatePeriod(request);
    }

    public List<TeamEvaluationPeriodResponse> getTeamEvaluationPeriods() {
        String empNo = "E001"; // TODO

        return periodRepository.findPeriodsByEmpNo(empNo).stream()
                .map(TeamEvaluationPeriodResponse::from)
                .toList();
    }
}
