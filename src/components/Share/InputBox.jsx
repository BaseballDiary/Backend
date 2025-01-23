import React from "react";
import styled from "styled-components";

const Box = styled.div`
  width: 350px;
  height: 90px;
  margin-top: 30px;
  box-sizing: border-box;

  display: flex; /* Flexbox 활성화 */
  flex-direction: column; /* 세로 정렬 */
  align-items: flex-start;
`;

const Input = styled.input`
  width: 350px;
  height: 50px;
  flex-shrink: 0;
  border-radius: 10px;
  border: 2px solid #000; /* 기본 테두리 */
  background: #fdfbf8;
  padding: 10px; /* 내부 여백 */
  font-size: 16px; /* 입력 글씨 크기 */

  box-sizing: border-box;

  /* focus 상태에서도 border 색상 유지 */
  &:focus {
    outline: none; /* 기본 outline 제거 */
    border: 2px solid #000; /* 테두리 색상 고정 */
  }
`;

const Text = styled.div`
  width: auto;
  height: 40px;
  flex-shrink: 0;
  color: #000;
  font-family: Roboto;
  font-size: 20px;
  font-style: normal;
  font-weight: 400;
  line-height: 40px; /* 200% */
`;

export default function InputBox({
  label,
  type,
  placeholder,
  value,
  onChange,
}) {
  return (
    <Box>
      <Text>{label}</Text>
      <Input
        type={type}
        placeholder={placeholder}
        value={value} // 부모 컴포넌트로부터 받은 value 적용
        onChange={onChange} // 부모 컴포넌트로부터 받은 onChange 적용
      />
    </Box>
  );
}
