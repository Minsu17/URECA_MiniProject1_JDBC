# URECA_MiniProject1_JDBC

### 영화 예매 프로그램 CRUD -> JDBC & Swing (Database MiniProject)

## ERD
![erd](https://github.com/user-attachments/assets/9deb9329-a5c7-481b-97f1-2301f2c510a0)

## SQL
```SQL
CREATE TABLE `User` (
	`user_id`	INT	NOT NULL,
	`username`	VARCHAR(50)	NOT NULL,
	`password`	VARCHAR(50)	NOT NULL,
	`email`	VARCHAR(100)	NOT NULL
);

CREATE TABLE `Movie` (
	`movie_id`	INT	NOT NULL,
	`title`	VARCHAR(100)	NOT NULL,
	`genre`	VARCHAR(50)	NULL,
	`duration`	INT	NULL,
	`description`	TEXT	NULL
);

CREATE TABLE `Booking` (
	`booking_id`	INT	NOT NULL,
	`booking_date`	DATE	NULL,
	`user_id`	int	NOT NULL,
	`movie_id`	int	NOT NULL
);

CREATE TABLE `Review` (
	`review_id`	INT	NOT NULL,
	`rating`	INT	NULL,
	`comment`	TEXT	NULL	COMMENT 'EX',
	`review_date`	DATE	NULL,
	`user_id`	INT	NOT NULL,
	`movie_id`	INT	NOT NULL
);

ALTER TABLE `User` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `Movie` ADD CONSTRAINT `PK_MOVIE` PRIMARY KEY (
	`movie_id`
);

ALTER TABLE `Booking` ADD CONSTRAINT `PK_BOOKING` PRIMARY KEY (
	`booking_id`
);

ALTER TABLE `Review` ADD CONSTRAINT `PK_REVIEW` PRIMARY KEY (
	`review_id`
);
```

## 영화 예매 프로그램 코드 정리

## 기타 클래스

### 1. LoginForm.java
- **역할**: 사용자 로그인 기능을 담당하는 클래스
- **주요 컴포넌트**:
  - `JTextField usernameField`: 사용자 이름을 입력받는 텍스트 필드
  - `JPasswordField passwordField`: 비밀번호를 입력받는 비밀번호 필드
  - `JButton loginButton`: 로그인 버튼
  - `JButton registerButton`: 회원가입 버튼
  - `JLabel statusLabel`: 로그인 상태 메시지를 표시하는 레이블
- **주요 메서드**:
  - `LoginForm()`: UI 컴포넌트를 초기화하고 배치, 로그인 및 회원가입 버튼에 액션 리스너를 추가
  - `performLogin()`: 입력된 사용자 이름과 비밀번호를 검증하여 로그인 처리
  - `performRegistration()`: 새로운 사용자를 등록

### 2. DatabaseConnection.java
- **역할**: 데이터베이스 연결을 관리하는 클래스
- **주요 필드**:
  - `url`: 데이터베이스 URL
  - `username`: 데이터베이스 사용자 이름
  - `password`: 데이터베이스 비밀번호
- **주요 메서드**:
  - `getConnection()`: 데이터베이스에 연결하고 `Connection` 객체를 반환
  - `closeConnection(Connection conn)`: 데이터베이스 연결을 닫음
- **기능**:
  - 애플리케이션에서 데이터베이스와의 연결을 설정하고 관리하여 데이터 액세스를 가능하게 함

## DTO 클래스

### 1. Movie.java
- **역할**: 영화 정보를 담는 클래스
- **주요 필드**:
  - `id`: 영화 ID
  - `title`: 영화 제목
  - `genre`: 영화 장르
  - `duration`: 영화 상영 시간
  - `description`: 영화 설명
- **주요 메서드**:
  - Getter와 Setter 메서드를 통해 각 필드의 값을 가져오거나 설정함
  - `toString()`: 영화 제목을 반환하여 JList와 같은 컴포넌트에서 영화 제목을 표시할 수 있게 함

### 2. Review.java
- **역할**: 리뷰 정보를 담는 클래스
- **주요 필드**:
  - `id`: 리뷰 ID
  - `username`: 리뷰 작성자 이름
  - `rating`: 평점
  - `comment`: 댓글
  - `reviewDate`: 리뷰 작성 날짜
- **주요 메서드**:
  - Getter와 Setter 메서드를 통해 각 필드의 값을 가져오거나 설정함
  - `toString()`: 리뷰 정보를 포맷팅하여 반환함 (평점, 댓글, 작성자 이름 포함)

### 3. User.java
- **역할**: 사용자 정보를 담는 클래스
- **주요 필드**:
  - `id`: 사용자 ID
  - `username`: 사용자 이름
  - `password`: 사용자 비밀번호
  - `email`: 사용자 이메일
- **주요 메서드**:
  - Getter와 Setter 메서드를 통해 각 필드의 값을 가져오거나 설정함

## UI 클래스

### 1. MovieBookingApp.java
- **역할**: 영화 예매 프로그램의 메인 프레임, 영화 목록 표시 및 예매, 예매 목록 조회, 예매 취소, 리뷰 관리를 위한 인터페이스 제공
- **주요 컴포넌트**:
  - `JList<Movie>`: 영화 목록을 표시하는 리스트
  - `JButton`: 영화 예매, 예매 목록 조회, 예매 취소, 리뷰 관리 버튼
  - `JPanel`, `JScrollPane`, `JLabel` 등: 레이아웃 구성 및 컴포넌트 배치
- **주요 메서드**:
  - `loadMovies()`: 영화 목록을 로드하여 리스트에 추가
  - `bookSelectedMovie()`: 선택된 영화를 예매
  - `viewBookings()`: 사용자가 예매한 영화 목록을 조회
  - `cancelBooking()`: 예매를 취소
  - `manageReviews()`: 선택된 영화의 리뷰를 관리

### 2. ReviewManagementFrame.java
- **역할**: 특정 영화의 리뷰를 관리하기 위한 프레임, 리뷰 작성, 수정, 삭제 기능 제공
- **주요 컴포넌트**:
  - `JList<Review>`: 리뷰 목록을 표시하는 리스트
  - `JButton`: 리뷰 작성, 수정, 삭제 버튼
  - `JPanel`, `JScrollPane` 등: 레이아웃 구성 및 컴포넌트 배치
- **주요 메서드**:
  - `loadReviews()`: 영화에 대한 리뷰 목록을 로드하여 리스트에 추가
  - `addReview()`: 새로운 리뷰를 작성
  - `updateReview()`: 선택된 리뷰를 수정
  - `deleteReview()`: 선택된 리뷰를 삭제

## DAO 클래스

### 1. MovieDAO.java
- **역할**: 영화 데이터베이스 연동을 담당
- **주요 메서드**:
  - `getAllMovies()`: 모든 영화 목록을 가져옴

### 2. BookingDAO.java
- **역할**: 예매 데이터베이스 연동을 담당
- **주요 메서드**:
  - `bookMovie(String username, int movieId)`: 사용자가 영화를 예매
  - `getUserBookings(String username)`: 사용자의 예매 목록을 가져옴
  - `cancelBooking(int bookingId)`: 예매를 취소

### 3. ReviewDAO.java
- **역할**: 리뷰 데이터베이스 연동을 담당
- **주요 메서드**:
  - `getReviews(int movieId)`: 특정 영화에 대한 모든 리뷰를 가져옴
  - `addReview(int movieId, String username, int rating, String comment)`: 새로운 리뷰를 추가
  - `updateReview(int reviewId, int rating, String comment)`: 기존 리뷰를 수정
  - `deleteReview(int reviewId)`: 기존 리뷰를 삭제

## 주요 기능 요약

- **영화 목록 로드**: MovieDAO를 통해 데이터베이스에서 영화를 가져와 JList에 표시
- **영화 예매**: 사용자가 선택한 영화를 예매하고 결과를 알림
- **예매 목록 조회**: 사용자의 예매 목록을 테이블로 표시
- **예매 취소**: 사용자가 입력한 예매 ID를 통해 예매를 취소
- **리뷰 관리**: 선택한 영화에 대한 리뷰를 작성, 수정, 삭제 가능


## 실행 화면

### 로그인 및 회원가입 화면
![login](https://github.com/user-attachments/assets/8f5ae8ae-161c-4fa1-bebd-27e18e8e811c)

프로그램 실행시 회원가입,  로그인 창이 뜨며 로그인을 완료해야 영화 예매 프로그램 창으로 넘어갈 수 있음

### 영화 목록

![movie](https://github.com/user-attachments/assets/3aab1f98-df97-44f6-a1ba-6a358cdb5297)
![reservation](https://github.com/user-attachments/assets/c1424141-9240-406b-91f7-6385a04693bf)

현재 상영중인 영화 목록이 보이며 선택 후 영화 예매버튼을 누를 시 예매 완료 창이 뜨며 예매 목록에 예매 한 영화정보가 들어감

![movieList](https://github.com/user-attachments/assets/be251fed-43ae-48fc-b3f4-45602fbe1289)

예매한 영화들 목록을 확인할 수 있음.

![reservationCancel](https://github.com/user-attachments/assets/35c244ff-5b12-4f76-bd7d-fca244065e7a)

영화 예매를 취소할 시 영화목록에 있는 ID를 입력해 예매한 영화 취소 가능

![reviewW](https://github.com/user-attachments/assets/1fc6b986-e19d-442e-bc8a-34e2a2c61972)

리뷰 관리를 들어가면 리뷰 작성을 할 수 있으며 작성한 내용, 아이디가 저장됨

![review2](https://github.com/user-attachments/assets/80659b31-1f59-433f-97c1-c8ca1ce9a5ce)
![reviewDelete](https://github.com/user-attachments/assets/fcc2a1d8-bcdc-4e26-a6fb-2af0a4d53deb)

작성한 리뷰를 선택하고 수정 및 삭제가 가능하다

## 프로젝트를 하며 아쉬웠던 점

sql문의 조인, ui의 배치나, 코드의 효율성을 따지지 못하고 급급하게 CRUD 구현에만 매달렸던 점이 아쉬웠다.
다음 프로젝트에는 제대로 DB설계, 코드 효율성을 따지면서 만들어 볼 계획이다.
