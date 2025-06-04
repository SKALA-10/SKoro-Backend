package skala.skoro.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skala.skoro.domain.auth.dto.*;
import skala.skoro.domain.auth.entity.AccessTokenEntry;
import skala.skoro.domain.auth.entity.RefreshTokenEntry;
import skala.skoro.domain.auth.jwt.JwtProvider;
import skala.skoro.domain.auth.repository.AccessTokenRepository;
import skala.skoro.domain.auth.repository.RefreshTokenRepository;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JwtProvider jwtProvider;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Employee employee = employeeRepository.findById(loginRequest.getEmpNo())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사번입니다."));

        String accessToken = jwtProvider.generateAccessToken(employee.getEmpNo(), employee.getRole());
        accessTokenRepository.save(new AccessTokenEntry(accessToken, employee.getEmpNo()));

        // 1. RefreshToken 발급
        String refreshToken = jwtProvider.generateRefreshToken(employee.getEmpNo());

        // 2. DB 또는 Redis에 RefreshToken 저장 (upsert)
        refreshTokenRepository.save(new RefreshTokenEntry(employee.getEmpNo(), refreshToken));

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(EmployeeProfileResponse.from(employee))
                .build();
    }

    public void logout(String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        if (token != null) {
            accessTokenRepository.deleteById(token);
        }

        // AccessToken에서 empNo 찾기 (option)
        String empNo = null;
        try { empNo = jwtProvider.getEmpNo(token); } catch (Exception ignore) {}
        if (empNo != null) {
            refreshTokenRepository.deleteById(empNo);
        }
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // 1. 유효성 검사
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("RefreshToken이 유효하지 않습니다.");
        }

        // 2. empNo 추출
        String empNo = jwtProvider.getEmpNo(refreshToken);

        // 3. 저장소에서 refreshToken 값 확인
        RefreshTokenEntry tokenInDb = refreshTokenRepository.findById(empNo)
                .orElseThrow(() -> new RuntimeException("RefreshToken 없음"));
        if (!tokenInDb.getToken().equals(refreshToken)) {
            throw new RuntimeException("RefreshToken이 일치하지 않습니다.");
        }

        // 4. 새 AccessToken 발급
        Employee employee = employeeRepository.findById(empNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사번입니다."));
        String newAccessToken = jwtProvider.generateAccessToken(empNo, employee.getRole());

        // 5. 필요하다면 RefreshToken도 재발급 후 갱신
        // String newRefreshToken = jwtProvider.generateRefreshToken(empNo);
        // tokenInDb.setToken(newRefreshToken);
        // refreshTokenRepository.save(tokenInDb);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // or newRefreshToken
                .build();
    }

}
