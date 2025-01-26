import React, { useState, useEffect } from "react";
import styled from "styled-components";
import InputBox from "../components/Share/InputBox";
import {
  requestVerificationCode,
  confirmVerificationCode,
  signup,
} from "../api/signup";

const Background = styled.div`
  width: 100vw;
  min-height: 100vh;
  background: #fdfbf8;

  display: flex;
  justify-content: center;
  align-items: center;
`;

const Box = styled.div`
  width: 350px;
  height: 746px;

  /*border: 1px solid black;*/
`;

const TitleText = styled.div`
  color: #000;
  font-family: Roboto;
  font-size: 32px;
  font-style: normal;
  font-weight: 600;
  line-height: 40px;
  margin-left: 116px;
`;

const InputEmptyContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px; /* Input과 타이머 간격 */
  width: 350px;
  height: 50px;
`;

const InputEmpty = styled.input`
  flex: 1;
  height: 50px;
  border-radius: 10px;
  border: 2px solid #000;
  background: #fdfbf8;
  padding: 10px;
  font-size: 16px;
  box-sizing: border-box;
  margin-top: 20px;

  /* focus 상태에서도 border 색상 유지 */
  &:focus {
    outline: none; /* 기본 outline 제거 */
    border: 2px solid #000; /* 테두리 색상 고정 */
  }
`;

const TimerText = styled.div`
  color: #f04d4c;
  font-family: Roboto;
  font-size: 20px;
  font-style: normal;
  font-weight: 400;
  line-height: 40px; /* 200% */
  margin-top: 20px;
`;

const RowContainer = styled.div`
  //인증번호 부분 가로 배열 위해서
  height: ${(props) => props.height || "100%"};
  width: 540px;
  display: flex; /* Flexbox 활성화 */
  flex-direction: row; /* 가로 배치 */
  align-items: center; /* 세로 정렬 가운데 */
  gap: 50px; /* 요소 사이 간격 */

  /*border: 1px solid black;*/
  box-sizing: border-box;
`;

const VerifyNumber = styled.button`
  display: flex;
  width: 140px;
  height: 50px;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  border: 2px solid #000;
  background: #aabda2;

  color: #fdfbf8;
  font-family: Roboto;
  font-size: 20px;
  font-weight: 400;

  margin-top: 70px;

  cursor: pointer;
  box-sizing: border-box;
`;

const PasswordText = styled.div`
  width: 261px;
  height: 40px;
  flex-shrink: 0;
  color: #000;
  font-family: Roboto;
  font-size: 16px;
  font-style: normal;
  font-weight: 400;
  line-height: 40px; /* 250% */
`;

const VerifyButton = styled.button`
  display: flex;
  width: 140px;
  height: 50px;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  border: 2px solid #000;
  background: #aabda2;
  color: #fdfbf8;
  font-family: Roboto;
  font-size: 20px;
  font-weight: 400;
  margin-top: 70px;
  cursor: pointer;
  box-sizing: border-box;
`;

const SignupButton = styled.button`
  width: 300px;
  height: 50px;
  flex-shrink: 0;
  border-radius: 10px;
  border: 2px solid #000;
  background: #aabda2;

  color: #fdfbf8;
  font-family: Roboto;
  font-size: 24px;
  font-style: normal;
  font-weight: 400;
  line-height: 40px; /* 166.667% */

  margin-top: 50px;
  margin-left: 25px;
`;

export default function Signup() {
  const [email, setEmail] = useState(""); // 이메일 상태 추가
  const [authNumber, setAuthNumber] = useState(""); // 인증번호 상태 추가
  const [password, setPassword] = useState(""); // 비밀번호 상태

  const [timeLeft, setTimeLeft] = useState(0); // 남은 시간을 초 단위로 관리 (초기값 0)

  // 인증번호 요청 처리
  const handleRequestVerificationCode = async () => {
    if (!email) {
      alert("이메일을 입력해주세요.");
      return;
    }

    try {
      await requestVerificationCode(email); // API 호출
      alert("인증번호가 전송되었습니다."); // 성공 메시지
      startTimer(); // 타이머 시작
    } catch (error) {
      alert("인증번호 요청에 실패했습니다. 다시 시도해주세요."); // 실패 메시지
    }
  };

  const handleConfirmVerificationCode = async () => {
    if (!authNumber || !email) {
      alert("이메일과 인증번호를 입력해주세요.");
      return;
    }

    try {
      await confirmVerificationCode(email, authNumber);
      alert("인증번호 확인이 완료되었습니다.");
    } catch (error) {
      alert("인증번호 확인에 실패했습니다. 다시 시도해주세요.");
    }
  };

  const startTimer = () => {
    setTimeLeft(300); // 5분(300초) 설정
  };

  useEffect(() => {
    if (timeLeft > 0) {
      const timerId = setTimeout(() => {
        setTimeLeft((prev) => prev - 1); // 1초씩 감소
      }, 1000);
      return () => clearTimeout(timerId); // 컴포넌트 언마운트 시 타이머 정리
    }
  }, [timeLeft]);

  // 시간 형식 (MM:SS)
  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${minutes}:${secs < 10 ? `0${secs}` : secs}`;
  };

  // 회원가입 요청 처리
  const handleSignup = async () => {
    if (!email || !password) {
      alert("이메일과 비밀번호를 입력해주세요.");
      return;
    }

    try {
      const response = await signup(email, password); // API 호출
      alert("회원가입이 완료되었습니다."); // 성공 메시지
      console.log("회원가입 성공:", response);
    } catch (error) {
      alert("회원가입에 실패했습니다. 다시 시도해주세요."); // 실패 메시지
    }
  };

  return (
    <Background>
      <Box>
        <TitleText>회원가입</TitleText>

        <RowContainer height="120px">
          <InputBox
            label="이메일"
            type="email"
            placeholder="이메일을 입력하세요"
            value={email}
            onChange={(e) => setEmail(e.target.value)} // 이메일 상태 업데이트
          ></InputBox>
          <VerifyNumber onClick={handleRequestVerificationCode}>
            인증번호 요청
          </VerifyNumber>
        </RowContainer>
        <RowContainer height="70px">
          <InputEmptyContainer>
            <InputEmpty
              placeholder="인증번호를 입력하세요"
              value={authNumber}
              onChange={(e) => setAuthNumber(e.target.value)}
            />
          </InputEmptyContainer>
          <VerifyButton onClick={handleConfirmVerificationCode}>
            인증번호 확인
          </VerifyButton>
          {timeLeft > 0 && <TimerText>{formatTime(timeLeft)}</TimerText>}
        </RowContainer>

        <InputBox
          label="비밀번호"
          type="password"
          placeholder="비밀번호는 특수문자 포함 8자리 이상 입력해주세요"></InputBox>
        <PasswordText>비밀번호는 4자리 숫자를 입력해주세요.</PasswordText>
        <SignupButton onClick={handleSignup}>회원가입</SignupButton>
      </Box>
    </Background>
  );
}
