<p align="center">
<picture>
<source media="(prefers-color-scheme: dark)" srcset="https://capsule-render.vercel.app/api?type=waving&height=250&color=1D4E89&fontColor=FFFFFF&text=🏥MediQ&fontSize=70&fontAlignY=30&animation=fadeIn&rotate=0&desc=위치%20기반%20병원·약국%20조회%20및%20실시간%20대기%20예약%20플랫폼&descSize=25&reversal=false">
<source media="(prefers-color-scheme: light)" srcset="https://capsule-render.vercel.app/api?type=waving&height=250&color=1D4E89&fontColor=FFFFFF&text=🏥MediQ&fontSize=70&fontAlignY=30&animation=fadeIn&rotate=0&desc=위치%20기반%20병원·약국%20조회%20및%20실시간%20대기%20예약%20플랫폼&descSize=25&reversal=false">
<img alt="MediQ Banner" src="https://capsule-render.vercel.app/api?type=waving&height=250&color=1D4E89&fontColor=FFFFFF&text=🏥MediQ&fontSize=70&fontAlignY=30&animation=fadeIn&rotate=0&desc=위치%20기반%20병원·약국%20조회%20및%20실시간%20대기%20예약%20플랫폼&descSize=25&reversal=false" width="100%">
</picture>
</p>

<br>

## ☁️ 목차
- [🐣 팀원 소개](#-팀원-소개)
- [🧸 기술 스택](#-기술-스택)
- [🔗 서비스 및 API 문서](#-서비스-및-api-문서)
- [💊 MediQ 서비스 소개](#-mediq-서비스-소개)
- [🎯 백엔드 프로젝트 목표](#-백엔드-프로젝트-목표)
- [🌿 프로젝트 기획 및 설계](#-프로젝트-기획-및-설계)


<br>

---

## 🐣 팀원 소개

<div align="center">

| **권민석** | **김미정** | **노승찬** | **이한별** |
| :---: | :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/19d001b7-cc93-48ff-96e3-5d0d3743ef2e" width="150" height="150" /> | <img src="https://github.com/user-attachments/assets/0e6d2bbe-e8f6-4fd9-9200-74ff652d4d53" width="150" height="150" /> | <img src="https://github.com/user-attachments/assets/0e7f0f41-3c3d-4f51-b4fd-2b0d7dee85b6" width="150" height="150" /> | <img src="https://github.com/user-attachments/assets/b4a0439f-ec7c-4ee7-ae61-6954b12d4693" width="150" height="150" /> |
| [![RIMIN0650](https://img.shields.io/badge/RIMIN0650-181717?logo=github&logoColor=white)](https://github.com/RIMIN0650) | [![mihub02](https://img.shields.io/badge/mihub02-181717?logo=github&logoColor=white)](https://github.com/mihub02) | [![seungchan-0629](https://img.shields.io/badge/seungchan--0629-181717?logo=github&logoColor=white)](https://github.com/seungchan-0629) | [![sole0714](https://img.shields.io/badge/sole0714-181717?logo=github&logoColor=white)](https://github.com/sole0714) |

</div>

<br>

---


<br>

---

## 🛠 기술 스택 (Backend)

### ⚙️ Core & Framework
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

### 🗄 Database & Cache
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

### ☁️ Infrastructure & CI/CD
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

<br>

---

## 🔗 서비스 및 API 문서

| 분류 | 링크 |
| :---: | :--- |
| **🌍 서비스 도메인** | [https://www.mediq.kro.kr](https://www.mediq.kro.kr) |
| **📜 API 명세서 (Swagger)** | [Swagger 주소 입력](#) |

<br>

---

## 💊 MediQ 서비스 소개

### 💡 프로젝트 배경
환자가 병원에 방문하기 전, 주변 병원의 대기 인원을 미리 조회하고 원격으로 대기 등록을 할 수 있다면 어떨까요? **MediQ**는 병원 방문 시 발생하는 고질적인 '무한 대기' 현상을 줄이고, 의료 서비스 이용의 효율성을 극대화하기 위해 탄생한 위치 기반 의료 편의 플랫폼입니다.

### 🩺 주요 기능
- **위치 기반 탐색**: 내 주변 가까운 병원 및 야간/휴일 영업 약국 정보 제공
- **스마트 예약 시스템**: 진료과목별 실시간 대기 인원 비교 및 원격 대기 등록
- **의료 기록 관리**: 개인별 진료 기록, 처방전, 관련 문서 안전 저장 및 열람
- **진료 대기 안내**: 원격 대기 등록 후 내 차례가 다가오면 실시간 푸시 알림 송신


## 🎯 백엔드 프로젝트 목표

단순한 기능 구현을 넘어, **실제 서비스 운영 환경**을 고려한 튼튼한 백엔드 시스템을 구축하는 것을 목표로 하였습니다.

- **LBS 기반 실시간 데이터 처리**: 프론트엔드와 지연 없는 통신을 통해 정확한 병원 대기 정보 제공
- **안전한 의료 정보 관리**: 사용자별 병원 방문 이력 및 민감한 데이터의 안전한 저장과 접근 제어
- **확장성 고려**: 추후 병원 연동 및 트래픽 증가에 대비하여 유연하게 대처할 수 있는 RESTful API 설계

<br>

---

## 🌿 프로젝트 기획 및 설계

### 🗺 시스템 아키텍처 (Target Architecture)
> 대규모 트래픽 발생 및 병원 실시간 예약 부하를 분산하기 위해 설계한 이상적인 타겟 아키텍처입니다.

<div align="center">
  <img src="https://github.com/user-attachments/assets/dc8455f0-ab4f-4bf4-b01b-5b487fd784ff" width="558" alt="Image" />
</div>

- **L4/L7 Load Balancer**: 예약 요청이 몰릴 때 다수의 서버 인스턴스로 트래픽 분산
- **Scale-out 구조**: 유연한 서버 확장을 위한 Stateless 애플리케이션 서버 구성
- **Database Replication**: 조회(Read)와 예약(Write) 부하 분산을 위한 Primary-Replica 구조
- **Redis Cache**: 잦은 조회가 발생하는 실시간 대기열 데이터를 캐싱하여 DB 병목 현상 방지


