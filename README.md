<div align="center">

# 🛍️ TrendyolAz - Enterprise Backend System

[![Java](https://img.shields.io/badge/Java-17-orange.svg?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED.svg?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Liquibase](https://img.shields.io/badge/Liquibase-Migration-red.svg?style=for-the-badge&logo=liquibase)](https://www.liquibase.org/)

**TrendyolAz**, ölçeklenebilir, modern ve güvenli e-ticaret süreçlerini yönetmek için tasarlanmış yüksek performanslı bir RESTful API arka plan (backend) sistemidir.

</div>

---

## 🏛️ Mimari Yapı ve Tasarım (Architecture)

Proje, **Clean Architecture** ve **Layered Architecture** prensiplerine uygun olarak geliştirilmiştir. Kodun bakımı kolay, test edilebilir ve modüler olması hedeflenmiştir.

## 🚀 Öne Çıkan Özellikler (Key Features)

- **Sipariş Yönetimi (Order Management):** Sepet, ürün ve sipariş süreçlerinin uçtan uca senkronizasyonu.
- **Güvenlik & Yetkilendirme:** Spring Security ve JWT (JSON Web Token) tabanlı güvenli kimlik doğrulama.
- **Database Migrations:** Liquibase ile versiyon kontrollü veritabanı şema yönetimi.
- **Docker Orchestration:** PostgreSQL ve Spring Boot uygulamasının Docker Compose ile entegre çalışması.
- **Hata Yönetimi:** Standartlaştırılmış HTTP yanıtları ile merkezi exception handling.

---

## 🛠️ Kullanılan Teknolojiler (Tech Stack)

- **Programlama Dili:** Java 17
- **Framework:** Spring Boot 3.x (Spring Web, Spring Security, Spring Data JPA)
- **Veritabanı:** PostgreSQL
- **Migration:** Liquibase
- **Containerization:** Docker & Docker Compose
- **Build Tool:** Gradle / Maven

---

## ⚙️ Kurulum ve Çalıştırma (Getting Started)

Projeyi yerel ortamınızda Docker kullanarak tek komutla çalıştırabilirsiniz:

### Pre-requisites
- [Docker Engine](https://docs.docker.com/get-docker/) & [Docker Compose](https://docs.docker.com/compose/install/)

### Adımlar

1. **Repoyu klonlayın:**
   ```bash
git clone https://github.com/agamehemmedaga/TrendyolAz.git
cd TrendyolAz

   
