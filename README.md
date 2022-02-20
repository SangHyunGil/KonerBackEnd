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



   
