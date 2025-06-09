package skala.skoro.domain.evaluation.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.evaluation.dto.KeywordResponse;
import skala.skoro.domain.evaluation.dto.PeerEvaluationDetailResponse;
import skala.skoro.domain.evaluation.dto.PeerEvaluationStatusResponse;
import skala.skoro.domain.evaluation.dto.SubmitPeerEvaluationRequest;
import skala.skoro.domain.evaluation.entity.Keyword;
import skala.skoro.domain.evaluation.entity.PeerEvaluation;
import skala.skoro.domain.evaluation.entity.PeerEvaluationKeyword;
import skala.skoro.domain.evaluation.repository.KeywordRepository;
import skala.skoro.domain.evaluation.repository.PeerEvaluationKeywordRepository;
import skala.skoro.domain.evaluation.repository.PeerEvaluationRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PeerEvaluationService {

    private final PeerEvaluationRepository peerEvaluationRepository;
    private final PeerEvaluationKeywordRepository peerEvaluationKeywordRepository;
    private final KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public PeerEvaluationDetailResponse getPeerEvaluationDetail(Long peerEvaluationId) {
        PeerEvaluation peerEvaluation = peerEvaluationRepository.findById(peerEvaluationId)
                .orElseThrow(() -> new EntityNotFoundException("동료 평가 없음"));

        Employee target = peerEvaluation.getTargetEmployee();

        // 전체 시스템 키워드
        List<KeywordResponse> allKeywords = keywordRepository.findAll().stream()
                .map(k -> KeywordResponse.builder()
                        .keywordId(k.getId())
                        .keywordName(k.getKeywordName())
                        .build())
                .toList();

        // 이미 선택된 키워드/커스텀 키워드
        List<PeerEvaluationKeyword> selectedEntities = peerEvaluationKeywordRepository.findByPeerEvaluation(peerEvaluation);
        List<String> selectedKeywords = selectedEntities.stream()
                .filter(k -> k.getKeyword() != null)
                .map(k -> k.getKeyword().getKeywordName())
                .toList();

        List<String> selectedCustomKeywords = selectedEntities.stream()
                .filter(k -> k.getCustomKeyword() != null)
                .map(PeerEvaluationKeyword::getCustomKeyword)
                .toList();

        return PeerEvaluationDetailResponse.builder()
                .peerEvaluationId(peerEvaluation.getId())
                .targetEmpNo(target.getEmpNo())
                .targetEmpName(target.getEmpName())
                .targetEmpProfileImage(target.getProfileImage())
                .targetEmpPosition(target.getPosition())
                .jointTask(peerEvaluation.getJointTask())
                .weight(peerEvaluation.getWeight())
                .systemKeywords(allKeywords)
                .selectedKeywords(selectedKeywords)
                .selectedCustomKeywords(selectedCustomKeywords)
                .build();
    }

    @Transactional
    public void submitPeerEvaluation(Long peerEvaluationId, SubmitPeerEvaluationRequest req) {
        PeerEvaluation peerEvaluation = peerEvaluationRepository.findById(peerEvaluationId)
                .orElseThrow(() -> new EntityNotFoundException("동료 평가 없음"));
        if (Boolean.TRUE.equals(peerEvaluation.getIsCompleted())) {
            throw new IllegalStateException("이미 제출된 동료 평가입니다.");
        }
        peerEvaluation.completeEvaluation(req.getWeight(), req.getJointTask());

        // 시스템 키워드 저장
        if (req.getKeywordIds() != null) {
            for (Long keywordId : req.getKeywordIds()) {
                Keyword keyword = keywordRepository.findById(keywordId)
                        .orElseThrow(() -> new EntityNotFoundException("키워드 없음"));
                peerEvaluationKeywordRepository.save(PeerEvaluationKeyword.builder()
                        .peerEvaluation(peerEvaluation)
                        .keyword(keyword)
                        .build());
            }
        }
        // 커스텀 키워드 저장
        if (req.getCustomKeywords() != null) {
            for (String custom : req.getCustomKeywords()) {
                peerEvaluationKeywordRepository.save(PeerEvaluationKeyword.builder()
                        .peerEvaluation(peerEvaluation)
                        .customKeyword(custom)
                        .build());
            }
        }
    }

    @Transactional(readOnly = true)
    public List<PeerEvaluationStatusResponse> getPeerEvaluationStatusList(String empNo, Long periodId) {
        List<PeerEvaluation> evaluations = peerEvaluationRepository.findByEmployee_EmpNoAndTeamEvaluation_Period_Id(empNo, periodId);

        return evaluations.stream()
                .map(e -> PeerEvaluationStatusResponse.builder()
                        .peerEvaluationId(e.getId())
                        .targetEmpNo(e.getTargetEmployee().getEmpNo())
                        .targetEmpName(e.getTargetEmployee().getEmpName())
                        .targetEmpProfileImage(e.getTargetEmployee().getProfileImage())
                        .completed(Boolean.TRUE.equals(e.getIsCompleted()))
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<KeywordResponse> getAllSystemKeywords() {
        return keywordRepository.findAll().stream()
                .map(k -> KeywordResponse.builder()
                        .keywordId(k.getId())
                        .keywordName(k.getKeywordName())
                        .build())
                .toList();
    }
}
