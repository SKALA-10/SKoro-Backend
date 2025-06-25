package skala.skoro.domain.period.entity;

import skala.skoro.global.exception.CustomException;

import static skala.skoro.global.exception.ErrorCode.INVALID_PHASE_TRANSITION;

public enum PeriodPhase {
    NOT_STARTED,
    PEER_EVALUATION,
    MIDDLE_REPORT,
    MANAGER_EVALUATION,
    REPORT_GENERATION,
    EVALUATION_FEEDBACK,
    COMPLETED;

    public PeriodPhase next() {
        return switch (this) {
            case NOT_STARTED -> PEER_EVALUATION;
            case PEER_EVALUATION -> MIDDLE_REPORT;
            case MIDDLE_REPORT -> MANAGER_EVALUATION;
            case MANAGER_EVALUATION -> REPORT_GENERATION;
            case REPORT_GENERATION -> EVALUATION_FEEDBACK;
            case EVALUATION_FEEDBACK -> COMPLETED;
            case COMPLETED -> throw new CustomException(INVALID_PHASE_TRANSITION);
        };
    }
}
