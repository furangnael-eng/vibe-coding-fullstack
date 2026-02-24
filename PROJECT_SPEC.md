# 프로젝트 명세서 (PROJECT_SPEC.md)

## 1. 프로젝트 개요
본 프로젝트는 현대적이고 미니멀한 디자인을 지향하는 "Vibe Coding" 기반의 블로그/게시글 관리 시스템이다. 깨끗한 UI와 사용자 경험을 최우선으로 하며, 기능형(Feature-based) 아키텍처를 채택하여 유지보수성을 극대화한다.

## 2. 기술 스택 (Technical Stack)
- **Backend**: 
    - Java 25 (JDK 25)
    - Spring Boot 4.0.1
    - Gradle 9.3.1
- **Database / ORM**:
    - H2 Database (In-memory / Persistence)
    - MyBatis (SQL Mapper)
- **Frontend**:
    - Thymeleaf (View Engine)
    - Tailwind CSS (Styling)
    - Vanilla JavaScript
- **Configuration**: YAML (.yml)

## 3. 프로젝트 메타데이터 (Project Metadata)
- **Group**: `com.example`
- **Artifact**: `vibeapp`
- **Package Name**: `com.example.vibeapp`
- **Main Class Name**: `VibeApp`

## 4. 프로젝트 구조 (Project Structure - Feature-based)
관심사 분리에 따른 계층형 구조가 아닌, 도메인/기능별로 패키지를 구성한다.
- `com.example.vibeapp.home`: 홈 페이지 및 공통 기능
- `com.example.vibeapp.post`: 게시글 등록, 상세, 목록(페이징), 수정, 삭제 기능 일체 및 태그 관리
- `resources/templates/[feature]`: 뷰 템플릿 또한 기능별 폴더로 분류 관리
- `resources/mapper/[feature]`: MyBatis SQL 매퍼 파일 기능별 폴더 관리

## 5. 주요 기능 (Key Features)
- **Clean Light UI**: Slate-50 배경과 Slate-900 텍스트를 활용한 고대비 라이트 모드 테마 적용.
- **Post Management**: 
    - 게시글 등록 및 상세 보기
    - 게시글 목록 페이징 처리 (페이지당 5개)
    - 게시글 수정 및 실제 데이터 반영
    - 게시물 삭제 기능 (확인 컨펌 포함)
- **Interactive Tags**: 
    - 쉼표(,)를 통해 복수 태그를 한 번에 입력하는 기능
    - 게시글 상세 및 수정 페이지에서의 태그 시각화 및 관리
- **Post Insights**: 
    - 게시글 조회수(Views) 증가 및 표시 기능
- **UX/UI Detail**: 
    - Glassmorphism 기반의 마이크로 애니메이션 및 스타일링
    - 반응형 디자인 적용

## 6. 의존성 및 플러그인 (Dependencies & Plugins)
- **Plugins**: Spring Boot, Dependency Management, Java
- **Dependencies**: 
    - `spring-boot-starter-web`
    - `spring-boot-starter-thymeleaf`
    - `spring-boot-starter-validation`
    - `mybatis-spring-boot-starter`
    - `com.h2database:h2`

## 7. 설정 파일 구성 (Configuration)
- `src/main/resources/application.yml` 파일을 사용하여 모든 설정을 중앙 관리한다.
- `schema.sql`, `data.sql`을 통한 데이터베이스 초기화 및 더미 데이터 제공.
