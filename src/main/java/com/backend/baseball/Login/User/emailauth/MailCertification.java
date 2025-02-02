package com.backend.baseball.Login.User.emailauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class MailCertification {

    private final String PREFIX = "email:";
    private final int LIMIT_TIME = 3 * 60; // 인증번호 만료 시간 (3분)

    // ✅ Redis 대신 메모리 기반 저장소 사용
    private final Map<String, String> certificationStore = new HashMap<>();

    // 이메일 인증번호 저장
    public void createMailCertification(String email, String certificationNumber) {
        certificationStore.put(PREFIX + email, certificationNumber);
    }

    // 이메일에 해당하는 인증번호 가져오기
    public String getMailCertification(String email) {
        return certificationStore.get(PREFIX + email);
    }

    // 인증번호 삭제
    public void deleteMailCertification(String email) {
        certificationStore.remove(PREFIX + email);
    }

    // 이메일로 저장된 인증번호 존재 여부 확인
    public boolean hasKey(String email) {
        return certificationStore.containsKey(PREFIX + email);
    }
}
