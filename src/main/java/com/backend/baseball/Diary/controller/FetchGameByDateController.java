package com.backend.baseball.Diary.controller;

import com.backend.baseball.User.service.UserDetailService;
import com.backend.baseball.Diary.dto.DiaryResponseDTO;
import com.backend.baseball.Diary.dto.FetchGameByDateDTO;
import com.backend.baseball.Diary.dto.GameResponseDTO;
import com.backend.baseball.Diary.dto.viewDiary.MyClubResponseDTO;
import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Diary.service.DiaryService;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.service.GameInfoService;
import com.backend.baseball.Diary.dto.SaveDiaryRequestDTO;
import com.backend.baseball.User.repository.UserRepository;
import com.backend.baseball.User.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Diary API", description = "야구 일기 생성 시 야구경기 정보 가져오기")
@RequestMapping("/diary")
//프론트에서 년-월-일 보내면 해당하는 경기 정보 보내주기
public class FetchGameByDateController{
    private final UserRepository userRepository; // ✅ 자동 주입


    private final GameInfoService gameInfoService;
    private final DiaryService diaryService;
    private final GameInfoRepository gameInfoRepository;


    //야구일기 작성버튼 클릭 시 내구단 보내기
    @GetMapping("/fetchMyClub")
    public ResponseEntity<MyClubResponseDTO> fetchMyClub() {
        // ✅ SecurityContext에서 인증 객체 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null || !(principal instanceof String email)) {
            return ResponseEntity.status(401).body(new MyClubResponseDTO("로그인이 필요합니다.")); // 401 Unauthorized 반환
        }

        // ✅ 로그인한 사용자의 certificationId를 기반으로 DB에서 사용자 정보 가져오기
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("사용자 정보를 찾을 수 없습니다."));

        return ResponseEntity.ok(new MyClubResponseDTO(user.getMyClub())); // ✅ DTO 사용
    }


    //로그인한 사용자의 `certificated_id`를 통해 내 구단이 포함된 경기 일정만 조회
    //사용자 식별 id, 내 구단, 날짜에 맞는 경기 하나만! 보내줄거임
    @PostMapping("/create/fetchgame")
    public ResponseEntity<GameResponseDTO> fetchUserTeamGameByDate(
            @RequestBody FetchGameByDateDTO request,
            HttpSession session) {

        // 1. 세션에서 로그인한 사용자의 `certificated_id` 가져오기
        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. 특정 날짜의 경기 중, 사용자의 구단이 포함된 경기만 필터링하여 가져오기
        List<GameResponseDTO> games = gameInfoService.getUserTeamGameInfoByDate(
                request.getGameDate().toString(), certificatedId
        );

        // 3. 경기 리스트가 비어있으면 예외 처리
        if (games.isEmpty()) {
            return ResponseEntity.notFound().build(); // 경기가 없으면 404 반환
        }

        // 4. 첫 번째 경기만 반환
        return ResponseEntity.ok(games.get(0));
    }

    @PostMapping("/create/saveGame")
    public ResponseEntity<DiaryResponseDTO> saveGameDiary(
            @RequestBody GameResponseDTO request, // 경기 정보 저장
            HttpSession session) {

        // 1. 세션에서 로그인한 사용자 가져오기
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 2. 경기 정보만 저장 (내용, 이미지 없이)
        SaveDiaryRequestDTO saveRequest = new SaveDiaryRequestDTO();
        saveRequest.setGameId(request.getGameId());  // 경기 ID 저장
        saveRequest.setContents("");  // 초기값 (내용 없음)
        saveRequest.setImgUrls(List.of());  // 초기값 (이미지 없음)

        // 3. 일기 저장 (경기 정보만)
        DiaryResponseDTO savedDiary = diaryService.saveGameToDiary(saveRequest, session);

        return ResponseEntity.ok(savedDiary);
    }
}