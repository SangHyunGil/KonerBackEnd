# Koner BackEnd

프로젝트에서 겪은 경험을 [기술 블로그](https://gilssang97.tistory.com/)에 정리했습니다.

> 사용한 기술스택은 다음과 같습니다.

- Spring Boot (API Server)
- Spring Security (Security)
- MySQL (RDB)
- JPA & QueryDSL (ORM)
- Redis (Cache)
- JUnit (Test)
- AWS (Infra)
- Jenkins (CI/CD)
- Janus (Media Server)
- Swagger (Documentation)

         
> 데모 영상은 다음과 같습니다.

|                         스터디 생성                          |                       스터디 사용 방법                       |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src = "https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%8A%A4%ED%84%B0%EB%94%94%20%EC%83%9D%EC%84%B1.gif?raw=true" width=100%> | <img src = "https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%8A%A4%ED%84%B0%EB%94%94%20%EC%82%AC%EC%9A%A9%EB%B0%A9%EB%B2%95.gif?raw=true"> |
|                    **스터디 수정, 삭제**                     |                       **스터디 승인**                        |
| <img src = "https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%8A%A4%ED%84%B0%EB%94%94%20%EC%88%98%EC%A0%95%2C%EC%82%AD%EC%A0%9C.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%8A%A4%ED%84%B0%EB%94%94%20%EC%8A%B9%EC%9D%B8.gif?raw=true"> |
|                       **스터디 거절**                        |                        **권한 변경**                         |
| <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%8A%A4%ED%84%B0%EB%94%94%20%EA%B1%B0%EC%A0%88.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EA%B6%8C%ED%95%9C%20%EB%B3%80%EA%B2%BD.gif?raw=true"> |
|                        **게시글 CR**                         |                        **게시글 UD**                         |
| <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EC%9E%91%EC%84%B1.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EC%88%98%EC%A0%95%2C%EC%82%AD%EC%A0%9C.gif?raw=true"> |
|                       **게시판 CRUD**                        |                       **게시글 댓글**                        |
| <p><br/>	<img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EA%B2%8C%EC%8B%9C%ED%8C%90%20crud.gif?raw=true"><br/></p> | <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EA%B2%8C%EC%8B%9C%EA%B8%80%20%EB%8C%93%EA%B8%80.gif?raw=true"> |
|                      **스터디 스케줄**                       |                           **알림**                           |
| <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%8A%A4%ED%84%B0%EB%94%94%20%EC%8A%A4%EC%BC%80%EC%A4%84.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%95%8C%EB%A6%BC.gif?raw=true"> |
|                           **쪽지**                           |                       **쪽지 보내기**                        |
| <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%AA%BD%EC%A7%80.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EC%AA%BD%EC%A7%80-%EB%B3%B4%EB%82%B4%EA%B8%B0.gif?raw=true"> |
|                   **프로필 및 내 스터디**                    |                       **무한 스크롤**                        |
| <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EB%82%B4%EC%8A%A4%ED%84%B0%EB%94%94.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerBackEnd/blob/prod/img/%EB%AC%B4%ED%95%9C%EC%8A%A4%ED%81%AC%EB%A1%A4.gif?raw=true"> |
|                     **화상채팅 방생성**                      |                      **화상채팅 참여**                       |
| <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%ED%99%94%EC%83%81%EC%B1%84%ED%8C%85%20%EB%B0%A9%EC%83%9D%EC%84%B1.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%ED%99%94%EC%83%81%EC%B1%84%ED%8C%85%20%EC%B0%B8%EC%97%AC.gif?raw=true"> |
|                    **화상채팅 화면 전환**                    |                           **채팅**                           |
| <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%ED%99%94%EB%A9%B4%EC%A0%84%ED%99%98.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%EC%B1%84%ED%8C%85.gif?raw=true"> |
|                     **참여자 목록 확인**                     |                  **파일 전송 및 다운로드**                   |
| <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%EC%B0%B8%EC%97%AC%EC%9E%90%20%EB%AA%A9%EB%A1%9D.gif?raw=true"> | <img src="https://github.com/SangHyunGil/KonerFrontEnd/blob/main/imgFolder/%ED%8C%8C%EC%9D%BC.gif?raw=true"> |

            


> Production 환경

<p align="center">
    <img src="https://github.com/SangHyunGil/Blog/blob/master/img/dep/prod.png?raw=true" width = 70%>
</p>



> CI/CD 환경

<p align="center">
    <img src="https://github.com/SangHyunGil/Blog/blob/master/img/dep/cicd.png?raw=true" width = 70%>
</p>





> ERD는 다음과 같이 정의하였습니다.

<p align="center">
    <img src="https://github.com/SangHyunGil/Blog/blob/master/img/dep/erd.png?raw=true">
</p>




> 패키지 구조는 다음과 같습니다.

```bash 
├── chat								// 채팅
│	├── controller 
│	├── dto
│	├── entity
│	├── repository
│   └── service
├── common
│	├── advice							// Exception에 대한 처리 
│	├── dto 							// 공통적으로 사용되는 Dto
│	├── EventListener 					// EventListener
│	├── helper 							// 공통적으로사용되는 Helper 
│	├── response 						// Response를 위한 Service 
│   └── EntityDate.class 				// 시간 정보를 담는 Super 클래스
├── config
│	├── jwt 							// jwt 관련 설정
│	├── redis							// redis 관련 설정
│	├── security					    // security 관련 설정
│	├── swagger							// swagger 관련 설정
│	├── AsyncConfig.class 				// 비동기 설정 관련
│	├── JasyptConfig.class 				// yml 암호화 설정 관련
│	├── S3Config.class					// AWS S3 버켓 설정 관련
│	├── WebMvcConfig.class 				// Cors 설정 관련
│   └── .class 							// 시간 정보를 담는 Super 클래스
├── member								// 회원
│	├── controller 
│	├── dto
│	├── entity
│	├── repository
│   └── service
├── message								// 쪽지
│	├── controller 
│	├── dto
│	├── entity
│	├── repository
│   └── service
├── notification						// 알림
│	├── controller 
│	├── dto
│	├── entity
│	├── repository
│   └── service
├── study								// 스터디 관련 
├── ├── dto								// 스터디 공통 dto						
│	├── study 							// 스터디
│	|   ├── controller 							
│	|   ├── dto 					
│	|   ├── entity 					
│	|   ├── repository 					
│	|   ├── service 					
│	├── studyarticle					// 스터디 게시글
│	|   ├── controller 							
│	|   ├── dto 					
│	|   ├── entity 					
│	|   ├── repository 					
│	|   ├── service 
│	├── studyboard						// 스터디 게시판
│	|   ├── controller 							
│	|   ├── dto 					
│	|   ├── entity 					
│	|   ├── repository 					
│	|   ├── service 
│	├── studycomment					// 스터디 댓글
│	|   ├── controller 							
│	|   ├── dto 					
│	|   ├── entity 					
│	|   ├── repository 					
│	|   ├── service 
│   └── studyjoin						// 스터디 참여
│	|   ├── controller 							
│	|   ├── dto 					
│	|   ├── entity 					
│	|   ├── repository 					
│	|   ├── service 
│   └── videoroom						// 스터디 화상회의
│	|   ├── controller 							
│	|   ├── dto 					
│	|   ├── entity 					
│	|   ├── repository 					
│	|   ├── service 
└──
```

​            



   
