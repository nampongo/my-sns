# 📮 My SNS
사진을 포함한 게시글을 올리고 댓글을 달 수 있는 SNS 플랫폼의 백엔드

<br>

# 🛠️ Technology Stack
- Spring boot 3.1.x
- Java 17
- SQLite

<br>

# 📂 API
`  사용자, 피드, 댓글까지 구현했습니다  `

<br>


## 👤 유저 관리
|METHOD|URI|설명|REQUEST DATA|비고|
|--|--|--|--|--|
|POST|/users/login|로그인|username, password|token 반환|
|POST|/users/register|회원 가입|username, password, passwordCheck||
|PUT|/users/image|프로필 이미지 등록|imageFile||
- 이미지 등록 시 "/src/main/resources/static/images/{userId}" 위치에 폴더 생성 후 파일 저장
- 기존 프로필 이미지가 이미 있을 시, 전 사진 삭제 후 재등록 
  
<br>

## 📱 피드 관리
|METHOD|URI|설명|REQUEST DATA|비고|
|--|--|--|--|--|
|POST|/feeds|피드 제목, 내용 등록|title, content||
|PUT|/feeds/image|피드 사진 추가|feedId, imageFile||
|GET|/feeds/user/{userId}|특정 유저의 전체 피드 조회|||
|GET|/feeds/{feedId}|특정 피드 조회|||
|DELETE|/feeds/{feedId}/image|피드 사진 삭제|imageIndex|인덱스 위치 삭제|
|DELETE|/feeds/{feedId}|피드 삭제|||
- 피드 제목과 내용으로 먼저 피드를 생성한 후, PUT을 통해 사진을 한개씩 추가할 수 있다.
- Feed에 사진이 List<String>으로 url리스트로 등록되어 있다. -> 삭제 시 해당 사진의 인덱스를 받아온다.
- Feed 사진은 각 피드 작성자의 "/src/main/resources/static/images/{userId}" 위치 폴더에 저장

<br>

## 📝 댓글 관리
|METHOD|URI|설명|REQUEST DATA|비고|
|--|--|--|--|--|
|POST|/comments|댓글 등록|feedId, content||
|PUT|/comments/{commentId}|댓글 내용 수정|content||
|DELETE|/comments/{commentId}|댓글 삭제|||


