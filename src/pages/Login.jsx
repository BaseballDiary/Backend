import React from "react";
import { useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom"; // React Router의 Link 컴포넌트
import InputBox from "../components/Share/InputBox";
import { login } from "../api/login";

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

  border: 1px solid black;
  box-sizing: border-box;
`;

const TitleText = styled.div`
  width: 90px;
  height: 40px;
  color: #000;
  font-family: Roboto;
  font-size: 32px;
  font-style: normal;
  font-weight: 600;
  line-height: 40px;
  margin-left: 130px;
`;

const LoginButton = styled.button`
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
const SignupText = styled(Link)`
  width: 70px;
  color: #000;
  font-family: Roboto;
  font-size: 18px;
  font-style: normal;
  font-weight: 400;
  line-height: 40px; /* 200% */
  margin-left: 140px;
  margin-top: 10px;
  text-decoration: none; /* 기본 밑줄 제거 */
  &:hover {
    text-decoration: underline; /* 호버 시 밑줄 표시 */
  }
`;
export default function Login() {
  const [email, setEmail] = useState(""); // 이메일 상태
  const [password, setPassword] = useState(""); // 비밀번호 상태

  const handleLogin = async () => {
    if (!email || !password) {
      alert("이메일과 비밀번호를 입력해주세요.");
      return;
    }

    try {
      const response = await login(email, password); // API 호출
      alert("로그인 성공!");
      console.log("로그인 응답 데이터:", response);
      // 로그인 성공 후 필요한 동작 추가 (예: 페이지 이동)
    } catch (error) {
      alert("로그인 실패. 다시 시도해주세요.");
    }
  };

  return (
    <Background>
      <Box>
        <TitleText>로그인</TitleText>
        <InputBox
          label="이메일"
          type="email"
          placeholder="이메일을 입력하세요"
          value={email}
          onChange={(e) => setEmail(e.target.value)} // 이메일 상태 업데이트
        />
        <InputBox
          label="비밀번호"
          type="password"
          placeholder="비밀번호를 입력해주세요"
          value={password}
          onChange={(e) => setPassword(e.target.value)} // 비밀번호 상태 업데이트
        />
        <LoginButton onClick={handleLogin}>로그인</LoginButton>
        <SignupText to="/signup">Sign Up</SignupText>
      </Box>
    </Background>
  );
}
