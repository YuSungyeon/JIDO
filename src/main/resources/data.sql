-- 기본 비밀번호 1234
INSERT INTO `user` (user_login_id, email, nickname, password_hash, age, created_at)
VALUES ('unique_login_001', 'unique001@gmail.com', 'nick001', '$2a$12$nbPaKHHT1tT2GT/fW9G8ceQO3Ik0cNV7QFG00M.RtiJhV5u8/F2z.', 25, NOW());

-- 대분류 --
INSERT INTO category VALUES ('1', '개발·기술', 1, NULL);
INSERT INTO category VALUES ('2', '디자인·콘텐츠 제작', 1, NULL);
INSERT INTO category VALUES ('3', '비즈니스·실무', 1, NULL);
INSERT INTO category VALUES ('4', '커리어·교육', 1, NULL);

------------------ 개발·기술 ------------------
---- 개발·프로그래밍
INSERT INTO category VALUES ('1.1', '개발·프로그래밍', 2, '1');

INSERT INTO category VALUES ('1.1.1', '백엔드', 3, '1.1');
INSERT INTO category VALUES ('1.1.2', '프론트엔드', 3, '1.1');
INSERT INTO category VALUES ('1.1.3', '알고리즘·자료구조', 3, '1.1');
INSERT INTO category VALUES ('1.1.4', '데이터베이스', 3, '1.1');
INSERT INTO category VALUES ('1.1.5', '모바일 앱', 3, '1.1');
INSERT INTO category VALUES ('1.1.6', '프로그래밍 언어', 3, '1.1');
INSERT INTO category VALUES ('1.1.7', '데브옵스·인프라', 3, '1.1');
INSERT INTO category VALUES ('1.1.8', '소프트웨어 테스트', 3, '1.1');
INSERT INTO category VALUES ('1.1.9', '개발 도구', 3, '1.1');
INSERT INTO category VALUES ('1.1.A', '웹 퍼블리싱', 3, '1.1');
INSERT INTO category VALUES ('1.1.B', '데스크톱 앱 개발', 3, '1.1');
INSERT INTO category VALUES ('1.1.C', 'VR/AR', 3, '1.1');
INSERT INTO category VALUES ('1.1.D', '개발·프로그래밍 자격증', 3, '1.1');

---- AI 개발
INSERT INTO category VALUES ('1.2', 'AI 개발', 2, '1');

INSERT INTO category VALUES ('1.2.1', 'AI에이전트 개발', 3, '1.2');
INSERT INTO category VALUES ('1.2.2', '딥러닝·머신러닝', 3, '1.2');
INSERT INTO category VALUES ('1.2.3', '컴퓨터 비전', 3, '1.2');
INSERT INTO category VALUES ('1.2.4', '자연어 처리', 3, '1.2');

---- AI 활용
INSERT INTO category VALUES ('1.3', 'AI 활용', 2, '1');

INSERT INTO category VALUES ('1.3.1', 'AI 업무 활용', 3, '1.3');
INSERT INTO category VALUES ('1.3.2', 'AI 크리에이티브', 3, '1.3');

---- 게임 개발
INSERT INTO category VALUES ('1.4', '게임 개발', 2, '1');

INSERT INTO category VALUES ('1.4.1', '게임 프로그래밍', 3, '1.4');
INSERT INTO category VALUES ('1.4.2', '게임 기획', 3, '1.4');
INSERT INTO category VALUES ('1.4.3', '게임 아트·그래픽', 3, '1.4');

---- 데이터 사이언스
INSERT INTO category VALUES ('1.5', '데이터 사이언스', 2, '1');

INSERT INTO category VALUES ('1.5.1', '데이터 분석', 3, '1.5');
INSERT INTO category VALUES ('1.5.2', '데이터 엔지니어링', 3, '1.5');
INSERT INTO category VALUES ('1.5.3', '데이터 사이언스 자격증', 3, '1.5');

---- 보안·네트워크
INSERT INTO category VALUES ('1.6', '보안·네트워크', 2, '1');

INSERT INTO category VALUES ('1.6.1', '보안', 3, '1.6');
INSERT INTO category VALUES ('1.6.2', '네트워크', 3, '1.6');
INSERT INTO category VALUES ('1.6.3', '시스템·운영체제', 3, '1.6');
INSERT INTO category VALUES ('1.6.4', '클라우드', 3, '1.6');
INSERT INTO category VALUES ('1.6.5', '블록체인', 3, '1.6');
INSERT INTO category VALUES ('1.6.6', '보안·네트워크 자격증', 3, '1.6');

---- 하드웨어
INSERT INTO category VALUES ('1.7', '하드웨어', 2, '1');

INSERT INTO category VALUES ('1.7.1', '컴퓨터 구조', 3, '1.7');
INSERT INTO category VALUES ('1.7.2', '임베디드·IoT', 3, '1.7');
INSERT INTO category VALUES ('1.7.3', '반도체', 3, '1.7');
INSERT INTO category VALUES ('1.7.4', '로봇공학', 3, '1.7');
INSERT INTO category VALUES ('1.7.5', '모빌리티', 3, '1.7');
INSERT INTO category VALUES ('1.7.6', '하드웨어 자격증', 3, '1.7');

------------------ 디자인·콘텐츠 제작 ------------------
---- 디자인·아트
INSERT INTO category VALUES ('2.1', '디자인·아트', 2, '2');

INSERT INTO category VALUES ('2.1.1', 'CAD·3D 모델링', 3, '2.1');
INSERT INTO category VALUES ('2.1.2', 'UI/UX', 3, '2.1');
INSERT INTO category VALUES ('2.1.3', '그래픽 디자인', 3, '2.1');
INSERT INTO category VALUES ('2.1.4', '디자인 자격증', 3, '2.1');

---- 콘텐츠 제작
INSERT INTO category VALUES ('2.2', '콘텐츠 제작', 2, '2');

INSERT INTO category VALUES ('2.2.1', '웹툰·이모티콘', 3, '2.2');
INSERT INTO category VALUES ('2.2.2', '사진·영상', 3, '2.2');
INSERT INTO category VALUES ('2.2.3', '사운드', 3, '2.2');

------------------ 비즈니스·실무 ------------------
---- 기획·경영·마케팅
INSERT INTO category VALUES ('3.1', '기획·경영·마케팅', 2, '3');

INSERT INTO category VALUES ('3.1.1', '기획·PM·PO', 3, '3.1');
INSERT INTO category VALUES ('3.1.2', '마케팅', 3, '3.1');
INSERT INTO category VALUES ('3.1.3', '경영·전략', 3, '3.1');
INSERT INTO category VALUES ('3.1.4', '기획·경영·마케팅 자격증', 3, '3.1');

---- 업무 생산성
INSERT INTO category VALUES ('3.2', '업무 생산성', 2, '3');

INSERT INTO category VALUES ('3.2.1', '업무 자동화', 3, '3.2');
INSERT INTO category VALUES ('3.2.2', '오피스', 3, '3.2');
INSERT INTO category VALUES ('3.2.3', '생산성 도구', 3, '3.2');

------------------ 커리어·교육 ------------------
---- 커리어·자기계발
INSERT INTO category VALUES ('4.1', '커리어·자기계발', 2, '4');

INSERT INTO category VALUES ('4.1.1', '취업·이직', 3, '4.1');
INSERT INTO category VALUES ('4.1.2', '창업·부업', 3, '4.1');
INSERT INTO category VALUES ('4.1.3', '개인 브랜딩', 3, '4.1');
INSERT INTO category VALUES ('4.1.4', '외국어', 3, '4.1');
INSERT INTO category VALUES ('4.1.5', '금융·재테크', 3, '4.1');
INSERT INTO category VALUES ('4.1.6', '교양·예절', 3, '4.1');

---- 대학 교육
INSERT INTO category VALUES ('4.2', '대학 교육', 2, '4');

INSERT INTO category VALUES ('4.2.1', '수학', 3, '4.2');
INSERT INTO category VALUES ('4.2.2', '공학', 3, '4.2');
INSERT INTO category VALUES ('4.2.3', '상경', 3, '4.2');
INSERT INTO category VALUES ('4.2.4', '자연과학', 3, '4.2');

