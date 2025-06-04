package skala.skoro.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skala.skoro.domain.auth.dto.EmployeeProfileResponse;
import skala.skoro.domain.auth.dto.LoginRequest;
import skala.skoro.domain.auth.dto.LoginResponse;
import skala.skoro.domain.auth.entity.AccessTokenEntry;
import skala.skoro.domain.auth.jwt.JwtProvider;
import skala.skoro.domain.auth.repository.AccessTokenRepository;
import skala.skoro.domain.employee.entity.Employee;
import skala.skoro.domain.employee.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JwtProvider jwtProvider;
    private final AccessTokenRepository accessTokenRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Employee employee = employeeRepository.findById(loginRequest.getEmpNo())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사번입니다."));

        String accessToken = jwtProvider.generateAccessToken(employee.getEmpNo(), employee.getRole());
        accessTokenRepository.save(new AccessTokenEntry(accessToken, employee.getEmpNo()));

        String refreshToken = null;

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
        // RefreshToken 삭제 등 추가 동작도 여기에
    }

}
