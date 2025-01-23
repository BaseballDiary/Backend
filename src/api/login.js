import axios from "axios";

// 로그인 API 호출 함수
export const login = async (email, password) => {
  try {
    const response = await axios.post("/member/login", {
      email,
      password,
    });
    return response.data; // 응답 데이터를 반환
  } catch (error) {
    console.error("로그인 실패:", error);
    throw error; // 에러를 호출한 곳으로 전달
  }
};
