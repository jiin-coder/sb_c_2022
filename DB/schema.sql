# DB 생성
DROP DATABASE IF EXISTS sb_c_2022;
CREATE DATABASE sb_c_2022;
USE sb_c_2022;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `loginId` CHAR(20) NOT NULL,
    `loginPw` CHAR(60) NOT NULL,
    `authlevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨(3=일반, 7=관리자)',
    `name` CHAR(20) NOT NULL,
    `nickname` CHAR(20) NOT NULL,
    `cellphoneNo` CHAR(20) NOT NULL,
    `email` CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전, 1=탈퇴)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 회원 테스트 데이터 생성 (관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
`loginId` = 'admin',
`loginPw` = 'admin',
`authlevel` = 7,
`name` = '관리자',
`nickname` = '관리자',
`cellphoneNo` = '01011117777',
`email` = 'kimjiae0114@gmail.com';

# 회원 테스트 데이터 생성 (일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
`loginId` = 'user1',
`loginPw` = 'user1',
`name` = '사용자1',
`nickname` = '사용자1',
`cellphoneNo` = '01011117777',
`email` = 'kimjiae0114@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
`loginId` = 'user2',
`loginPw` = 'user2',
`name` = '사용자2',
`nickname` = '사용자2',
`cellphoneNo` = '01011117777',
`email` = 'kimjiae0114@gmail.com';


# 게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;
DESC `article`;
SELECT * FROM article;

# 기존 게시물의 작성자를 2번으로 지정
UPDATE article
SET memberId = 2
WHERE memberId = 0;



# 게시판 테이블 생성
CREATE TABLE `board` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항),free1(자유게시판),free2(자유게시판2),...',
    `name` CHAR(50) NOT NULL COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=탈퇴전, 1=탈퇴)',
    delDate DATETIME COMMENT '삭제날짜'
);

# 기본 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유';


# 게시판 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(20) 
UNSIGNED NOT NULL AFTER memberId;


# 1, 2번 게시물을 공지사항 게시물로 지정
UPDATE article
SET boardId = 1
WHERE id IN (1, 2); 

# 3번 게시물을 자유게시판 게시물로 지정
UPDATE article
SET boardId = 2
WHERE id IN (3); 

SELECT * FROM article;

SELECT * FROM board WHERE id = 1;
SELECT * FROM board WHERE id = 2;

# 게시물 개수 늘리기
/*
insert into article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
select now(), now(), FLOOR(RAND() * 2) + 1, FLOOR(RAND() * 2) + 1, concat('제목_', rand()), CONCAT('내용_', RAND())
from article;
*/


# 게시물 테이블 hitCount 칼럼을 추가
ALTER TABLE article
ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;