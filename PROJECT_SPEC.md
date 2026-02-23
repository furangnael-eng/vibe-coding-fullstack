# 프로젝트 명세서 (PROJECT_SPEC.md)

## 1. 프로젝트 개요
본 프로젝트는 최소한의 기능을 갖춘 스프링부트(Spring Boot) 웹 애플리케이션을 생성하기 위한 기본 설정을 정의한다.

## 2. 기술 스택 (Technical Stack)
- **JDK**: JDK 25 이상
- **Language**: Java
- **Spring Boot**: 4.0.1 이상
- **Build Tool**: Gradle 9.3.0 이상 (Groovy DSL)
- **Configuration**: YAML (.yml) 형식 사용

## 3. 프로젝트 메타데이터 (Project Metadata)
- **Group**: `com.example`
- **Artifact**: `vibeapp`
- **Name**: `vibeapp`
- **Description**: 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트다.
- **Package Name**: `com.example.vibeapp`
- **Main Class Name**: `VibeApp`

## 4. 의존성 및 플러그인 (Dependencies & Plugins)
- **플러그인**:
    - `org.springframework.boot` (버전: 4.0.1+)
    - `io.spring.dependency-management` (Spring Boot 버전에 맞춤)
    - `java`
- **의존성**:
    - `spring-boot-starter-web`: 웹 애플리케이션 기능을 위한 핵심 의존성
    - `spring-boot-starter-thymeleaf`: Thymeleaf 뷰 템플릿 엔진 추가
    - `spring-boot-starter`: 기본 스프링부트 스타터

## 5. 설정 파일 구성 (Configuration)
- `src/main/resources/application.yml` 파일을 사용하여 설정을 관리한다.
