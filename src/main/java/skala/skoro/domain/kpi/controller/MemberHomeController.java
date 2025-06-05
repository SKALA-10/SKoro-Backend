package skala.skoro.domain.kpi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skala.skoro.domain.auth.dto.CustomUserDetails;
import skala.skoro.domain.kpi.dto.MyContributionResponse;
import skala.skoro.domain.kpi.dto.MyFinalScoreResponse;
import skala.skoro.domain.kpi.dto.MyTaskResponse;
import skala.skoro.domain.kpi.service.MemberHomeService;

import java.util.List;

@RestController
@RequestMapping("/member/home")
@RequiredArgsConstructor
public class MemberHomeController {

    private final MemberHomeService memberHomeService;

    @GetMapping("/tasks")
    public List<MyTaskResponse> getMyTasks(@AuthenticationPrincipal CustomUserDetails user) {
        return memberHomeService.getMyTasks(user.getUsername());
    }

    @GetMapping("/contributions")
    public List<MyContributionResponse> getMyContributions(@AuthenticationPrincipal CustomUserDetails user) {
        return memberHomeService.getMyContributions(user.getUsername());
    }

    @GetMapping("/final-scores")
    public List<MyFinalScoreResponse> getMyFinalScores(@AuthenticationPrincipal CustomUserDetails user) {
        return memberHomeService.getMyFinalScores(user.getUsername());
    }

}
