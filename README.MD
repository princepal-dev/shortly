# Shortify

Production-grade URL shortener built with Java and Spring Boot.

Shortify lets users create short URLs, manage links, and track click analytics, with authentication via JWT, Redis-backed caching, PostgreSQL persistence, and Docker-based local infrastructure.

## Project Status

This repository is currently in the **bootstrap stage**:
- Spring Boot application scaffold is in place.
- Docker Compose services for PostgreSQL and Redis are defined.
- Core modules (auth, URL APIs, analytics, admin) are planned and documented below.

## Core Features (Target)

- URL shortening with customizable alias support
- JWT-based authentication and authorization
- Link management (create, update, deactivate, delete)
- Click analytics (total clicks, unique visitors, referrers, user agents, timestamps)
- Redis caching for hot link resolution and reduced DB reads
- PostgreSQL as primary persistent store
- Dockerized local development stack

## Tech Stack

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data Redis
- PostgreSQL
- Docker / Docker Compose
- Maven

## High-Level Architecture

- **API Layer**: REST endpoints for auth, URL operations, and analytics
- **Security Layer**: JWT filter chain and role-based access control
- **Service Layer**: business logic for shortening, resolving, tracking, and management
- **Cache Layer (Redis)**: fast alias lookup and frequently accessed metadata
- **Database Layer (PostgreSQL)**: durable storage for users, URLs, and analytics records

## Prerequisites

- JDK 17+
- Maven 3.9+
- Docker + Docker Compose

## Quick Start (Docker-backed services)

From project root:

```bash
docker compose up -d
./mvnw spring-boot:run
```

## Local Development

1. Start infrastructure services:

```bash
docker compose up -d postgres redis
```

2. Run the app:

```bash
./mvnw spring-boot:run
```

## API Surface (Planned)

### Authentication
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`

### URL Management
- `POST /api/urls` - create short URL
- `GET /api/urls/{code}` - get URL details
- `PUT /api/urls/{code}` - update URL metadata
- `DELETE /api/urls/{code}` - deactivate/delete URL
- `GET /r/{code}` - redirect to original URL

### Analytics
- `GET /api/urls/{code}/analytics` - per-link analytics
- `GET /api/analytics/dashboard` - account-level aggregates

## Security Notes

- Use strong JWT secrets and rotate regularly
- Keep access and refresh token lifetimes short and explicit
- Store secrets in environment variables or a secret manager
- Enforce HTTPS in production
- Add rate limiting for auth and redirect endpoints

## Caching Strategy (Planned)

- Cache key: short code -> destination URL + metadata
- Cache hit path: serve redirect without database lookup
- Cache invalidation on URL update/deletion
- Optional TTL for stale data protection

## Analytics Strategy (Planned)

- Track redirect events asynchronously where possible
- Capture timestamp, IP hash, user agent, referrer, and geo metadata
- Store raw events for audit + aggregate counters for fast dashboards

## Roadmap

1. Implement domain model and persistence layer
2. Implement JWT auth and user management
3. Build URL create/resolve/manage endpoints
4. Add click tracking and analytics aggregation
5. Add Redis caching and invalidation logic
6. Add API documentation and production hardening
7. Add CI/CD and deployment manifests

## Repository Structure

- `src/main/java/com/princeworks/shortify` - application source
- `compose.yaml` - PostgreSQL and Redis services
- `pom.xml` - Maven dependencies and build config

## License

Add your preferred license (MIT/Apache-2.0/etc.) in this repository.

