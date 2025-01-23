import axios from "axios";

// 이메일 인증 번호 요청 API 호출 함수
export const requestVerificationCode = async (authEmail) => {
  try {
    const response = await axios.post("http://localhost:3000/auth", {
      authEmail,
    });
    return response.data; // 필요한 응답 데이터 반환
  } catch (error) {
    console.error("인증번호 요청 실패:", error);
    throw error; // 에러를 호출한 곳으로 전달
  }
};

// 인증번호 확인 API 호출 함수
export const confirmVerificationCode = async (email, authNumber) => {
  try {
    const response = await axios.post("http://localhost:3000/auth/confirm", {
      email,
      authNumber,
    });
    return response.data; // 필요한 응답 데이터 반환
  } catch (error) {
    console.error("인증번호 확인 실패:", error);
    throw error;
  }
};

// 회원가입 API 호출 함수
export const signup = async (email, password) => {
  try {
    const response = await axios.post("http://localhost:3000/member", {
      email,
      password,
    });
    return response.data; // 응답 데이터를 반환
  } catch (error) {
    console.error("회원가입 실패:", error);
    throw error; // 에러를 호출한 곳으로 전달
  }
};
