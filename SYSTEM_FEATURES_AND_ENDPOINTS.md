# Multi-Sport Club Management System - Features & Endpoints Documentation

This document describes all the features and API endpoints available in the system. It is designed to help frontend developers and UI/UX designers understand the complete functionality of the application.

---

## Architecture Overview

The system is built as a **microservices architecture** with the following services:

| Service | Port | Base Path (via Gateway) | Description |
|---------|------|------------------------|-------------|
| **Gateway Service** | 8080 | `/` | API Gateway - all requests go through here |
| **Eureka Server** | 8761 | - | Service Discovery |
| **Config Server** | 8888 | - | Centralized Configuration |
| **User Management Service** | 8081 | `/api/user-management/**` | Users, Teams, Sports, Auth |
| **Player Management Service** | 8082 | `/api/player-management/**` | Players, Contracts, Transfers, Rosters |
| **Training & Match Service** | 8083 | `/api/training-match/**` | Training, Matches, Stats |
| **Medical & Fitness Service** | 8084 | `/api/medical-fitness/**` | Injuries, Treatments, Fitness |
| **Reports & Analytics Service** | 8085 | `/api/reports-analytics/**` | Reports, Analytics, Scouting |
| **Notification & Mail Service** | 8086 | `/api/notification-mail/**` | Notifications, Alerts, Messages, Email |

### Authentication & Authorization
- **Keycloak** is the identity provider (realm: `mscms`)
- All API calls require a **JWT Bearer token** in the `Authorization` header
- Users are identified across services by their **Keycloak ID** (`keycloakId`) - a UUID string from the JWT `sub` claim
- Role-based access control via `@PreAuthorize` annotations on every endpoint
- The frontend must send the JWT token with every request to the Gateway

### Important: User Identity (keycloakId)
All user references across the system use `String keycloakId` (the Keycloak UUID) instead of internal database IDs. This means:
- When creating a player contract, you send `playerKeycloakId: "abc-123-def"` (not a numeric ID)
- When reporting an injury, you send `playerKeycloakId` and `doctorKeycloakId` as strings
- The frontend gets the current user's keycloakId from the decoded JWT token's `sub` claim

### Real-Time Events (Kafka)
The system uses **Kafka event-driven architecture** for real-time notifications:
- When key actions happen (injury reported, match scheduled, transfer completed, etc.), events are published
- The **Notification & Mail Service** consumes these events and automatically creates notifications/sends emails
- The frontend should poll or use WebSocket for real-time notification updates

---

## 1. User Management Service

**Gateway Base URL:** `http://localhost:8080/api/user-management`

### 1.1 User Endpoints (`/users`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/users` | ADMIN | Create a new user |
| PUT | `/users/{id}` | ADMIN | Update user by ID |
| GET | `/users/{id}` | ADMIN | Get user by ID |
| GET | `/users` | ADMIN | Get all users |
| DELETE | `/users/{id}` | ADMIN | Delete user by ID |
| GET | `/users/search` | ADMIN | Search users with filters |
| GET | `/users/keycloak/{keycloakId}` | ALL AUTHENTICATED | Get user by Keycloak ID |

**Search Parameters** (all optional query params):
- `firstName` - partial match (case-insensitive)
- `lastName` - partial match (case-insensitive)
- `email` - partial match (case-insensitive)
- `gender` - exact match (enum: `MALE`, `FEMALE`)
- `role` - exact match (enum: see User Roles below)
- `minAge` / `maxAge` - age range filter

### 1.2 Player Profile Endpoints (`/players`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/players` | ADMIN | Create player profile |
| PUT | `/players/{id}` | ADMIN | Update player profile |
| GET | `/players/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get player by ID |
| GET | `/players` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get all players |
| DELETE | `/players/{id}` | ADMIN | Delete player profile |

**Player Fields:** dateOfBirth, nationality, preferredPosition, marketValue, kitNumber, playerStatus

### 1.3 Staff Endpoints (`/staff`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/staff` | ADMIN | Create staff member |
| PUT | `/staff/{id}` | ADMIN | Update staff member |
| GET | `/staff/{id}` | ADMIN, TEAM_MANAGER, SPORT_MANAGER | Get staff by ID |
| GET | `/staff` | ADMIN, TEAM_MANAGER, SPORT_MANAGER | Get all staff |
| DELETE | `/staff/{id}` | ADMIN | Delete staff member |

**Staff Roles:** HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH, DOCTOR, PHYSIOTHERAPIST, PERFORMANCE_ANALYST

### 1.4 Team Manager Endpoints (`/team-managers`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/team-managers` | ADMIN | Create team manager |
| PUT | `/team-managers/{id}` | ADMIN | Update team manager |
| GET | `/team-managers/{id}` | ADMIN, SPORT_MANAGER | Get team manager by ID |
| GET | `/team-managers` | ADMIN, SPORT_MANAGER | Get all team managers |
| DELETE | `/team-managers/{id}` | ADMIN | Delete team manager |

### 1.5 Sport Manager Endpoints (`/sport-managers`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/sport-managers` | ADMIN | Create sport manager |
| PUT | `/sport-managers/{id}` | ADMIN | Update sport manager |
| GET | `/sport-managers/{id}` | ADMIN | Get sport manager by ID |
| GET | `/sport-managers` | ADMIN | Get all sport managers |
| DELETE | `/sport-managers/{id}` | ADMIN | Delete sport manager |

### 1.6 Scout Endpoints (`/scouts`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/scouts` | ADMIN | Create scout |
| PUT | `/scouts/{id}` | ADMIN | Update scout |
| GET | `/scouts/{id}` | ADMIN, SPORT_MANAGER | Get scout by ID |
| GET | `/scouts` | ADMIN, SPORT_MANAGER | Get all scouts |
| DELETE | `/scouts/{id}` | ADMIN | Delete scout |

**Scout Fields:** region, organizationName

### 1.7 Fan Endpoints (`/fans`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/fans` | PUBLIC | Create fan (default role on registration) |
| PUT | `/fans/{id}` | ADMIN | Update fan |
| GET | `/fans/{id}` | ADMIN | Get fan by ID |
| GET | `/fans` | ADMIN | Get all fans |
| DELETE | `/fans/{id}` | ADMIN | Delete fan |

**Fan Fields:** displayName, favoriteTeam

### 1.8 Sponsor Endpoints (`/sponsors`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/sponsors` | ADMIN | Create sponsor |
| PUT | `/sponsors/{id}` | ADMIN | Update sponsor |
| GET | `/sponsors/{id}` | ADMIN, SPORT_MANAGER | Get sponsor by ID |
| GET | `/sponsors` | ADMIN, SPORT_MANAGER | Get all sponsors |
| DELETE | `/sponsors/{id}` | ADMIN | Delete sponsor |

### 1.9 Team Endpoints (`/teams`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/teams` | ADMIN, SPORT_MANAGER | Create team |
| PUT | `/teams/{id}` | ADMIN, SPORT_MANAGER | Update team |
| GET | `/teams/{id}` | ALL AUTHENTICATED | Get team by ID |
| GET | `/teams` | ALL AUTHENTICATED | Get all teams |
| DELETE | `/teams/{id}` | ADMIN | Delete team |

**Team Fields:** name, country, sportId

### 1.10 Sport Endpoints (`/sports`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/sports` | ADMIN | Create sport |
| PUT | `/sports/{id}` | ADMIN | Update sport |
| GET | `/sports/{id}` | ALL AUTHENTICATED | Get sport by ID |
| GET | `/sports` | ALL AUTHENTICATED | Get all sports |
| DELETE | `/sports/{id}` | ADMIN | Delete sport |

### 1.11 National Team Endpoints (`/national-teams`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/national-teams` | ADMIN, SPORT_MANAGER | Create national team |
| PUT | `/national-teams/{id}` | ADMIN, SPORT_MANAGER | Update national team |
| GET | `/national-teams/{id}` | ALL AUTHENTICATED | Get national team by ID |
| GET | `/national-teams` | ALL AUTHENTICATED | Get all national teams |
| DELETE | `/national-teams/{id}` | ADMIN | Delete national team |

**User Roles (enum):**
`ADMIN`, `PLAYER`, `HEAD_COACH`, `ASSISTANT_COACH`, `FITNESS_COACH`, `SPECIFIC_COACH`, `DOCTOR`, `PHYSIOTHERAPIST`, `PERFORMANCE_ANALYST`, `TEAM_MANAGER`, `SPORT_MANAGER`, `SCOUT`, `FAN`, `SPONSOR`

**Kafka Events Published:**
- `user.created` - when a new user is registered
- `user.updated` - when user profile is updated
- `user.deleted` - when a user is deleted
- `player.status.changed` - when a player's status changes (e.g., Available -> Injured)

---

## 2. Player Management Service

**Gateway Base URL:** `http://localhost:8080/api/player-management`

### 2.1 Player Contract Endpoints (`/player-contracts`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-contracts` | ADMIN, SPORT_MANAGER | Create contract |
| PUT | `/player-contracts/{id}` | ADMIN, SPORT_MANAGER | Update contract |
| GET | `/player-contracts/{id}` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get contract by ID |
| GET | `/player-contracts` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get all contracts |
| DELETE | `/player-contracts/{id}` | ADMIN | Delete contract |

**Request Fields:** `playerKeycloakId` (String), startDate, endDate, salary, releaseClause

### 2.2 Player Transfer - Incoming (`/player-transfers-incoming`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-transfers-incoming` | ADMIN, SPORT_MANAGER | Create incoming transfer |
| PUT | `/player-transfers-incoming/{id}` | ADMIN, SPORT_MANAGER | Update transfer |
| GET | `/player-transfers-incoming/{id}` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get transfer by ID |
| GET | `/player-transfers-incoming` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get all transfers |
| DELETE | `/player-transfers-incoming/{id}` | ADMIN | Delete transfer |

### 2.3 Player Transfer - Outgoing (`/player-transfers-outgoing`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-transfers-outgoing` | ADMIN, SPORT_MANAGER | Create outgoing transfer |
| PUT | `/player-transfers-outgoing/{id}` | ADMIN, SPORT_MANAGER | Update transfer |
| GET | `/player-transfers-outgoing/{id}` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get transfer by ID |
| GET | `/player-transfers-outgoing` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get all transfers |
| DELETE | `/player-transfers-outgoing/{id}` | ADMIN | Delete transfer |

**Transfer Fields:** `playerKeycloakId` (String), fromTeam/toTeam, transferFee, transferDate, status, contractDetails

### 2.4 Roster Endpoints (`/rosters`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/rosters` | ADMIN, TEAM_MANAGER, HEAD_COACH | Create roster |
| PUT | `/rosters/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH | Update roster |
| GET | `/rosters/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get roster by ID |
| GET | `/rosters` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get all rosters |
| DELETE | `/rosters/{id}` | ADMIN | Delete roster |

### 2.5 National Team Call-Up Endpoints (`/player-call-ups`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-call-ups` | ADMIN, SPORT_MANAGER | Create call-up request |
| PUT | `/player-call-ups/{id}` | ADMIN, SPORT_MANAGER | Update call-up |
| GET | `/player-call-ups/{id}` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get call-up by ID |
| GET | `/player-call-ups` | ADMIN, SPORT_MANAGER, TEAM_MANAGER | Get all call-ups |
| DELETE | `/player-call-ups/{id}` | ADMIN | Delete call-up |

**Call-Up Fields:** `playerKeycloakId` (String), `nationalTeamKeycloakId` (String), callUpDate, returnDate, status

### 2.6 Outer Player Endpoints (`/outer-players`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/outer-players` | ADMIN, SCOUT, SPORT_MANAGER | Create outer player |
| PUT | `/outer-players/{id}` | ADMIN, SCOUT, SPORT_MANAGER | Update outer player |
| GET | `/outer-players/{id}` | ADMIN, SCOUT, SPORT_MANAGER, TEAM_MANAGER | Get outer player by ID |
| GET | `/outer-players` | ADMIN, SCOUT, SPORT_MANAGER, TEAM_MANAGER | Get all outer players |
| DELETE | `/outer-players/{id}` | ADMIN | Delete outer player |

### 2.7 Outer Team Endpoints (`/outer-teams`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/outer-teams` | ADMIN, SCOUT, SPORT_MANAGER | Create outer team |
| PUT | `/outer-teams/{id}` | ADMIN, SCOUT, SPORT_MANAGER | Update outer team |
| GET | `/outer-teams/{id}` | ADMIN, SCOUT, SPORT_MANAGER, TEAM_MANAGER | Get outer team by ID |
| GET | `/outer-teams` | ADMIN, SCOUT, SPORT_MANAGER, TEAM_MANAGER | Get all outer teams |
| DELETE | `/outer-teams/{id}` | ADMIN | Delete outer team |

**Kafka Events Published:**
- `transfer.incoming.completed` - when an incoming transfer is completed
- `transfer.outgoing.completed` - when an outgoing transfer is completed
- `contract.created` - when a new player contract is created
- `roster.updated` - when a roster is modified
- `callup.approved` - when a national team call-up is approved

---

## 3. Training & Match Service

**Gateway Base URL:** `http://localhost:8080/api/training-match`

### 3.1 Training Session Endpoints (`/training-sessions`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/training-sessions` | ADMIN, HEAD_COACH | Create training session |
| PUT | `/training-sessions/{id}` | ADMIN, HEAD_COACH | Update session |
| GET | `/training-sessions/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH | Get session by ID |
| GET | `/training-sessions` | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH | Get all sessions |
| DELETE | `/training-sessions/{id}` | ADMIN, HEAD_COACH | Delete session |

**Session Fields:** teamId, headCoachId, specificCoachId, type, status, location, date, duration, objectives

### 3.2 Training Plan Endpoints (`/training-plans`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/training-plans` | ADMIN, HEAD_COACH | Create training plan |
| PUT | `/training-plans/{id}` | ADMIN, HEAD_COACH | Update plan |
| GET | `/training-plans/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Get plan by ID |
| GET | `/training-plans` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Get all plans |
| DELETE | `/training-plans/{id}` | ADMIN, HEAD_COACH | Delete plan |

### 3.3 Training Attendance Endpoints (`/training-attendance`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/training-attendance` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Record attendance |
| PUT | `/training-attendance/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Update attendance |
| GET | `/training-attendance/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH | Get by ID |
| GET | `/training-attendance` | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH | Get all |
| DELETE | `/training-attendance/{id}` | ADMIN | Delete attendance |

### 3.4 Training Drill Endpoints (`/training-drills`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/training-drills` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Add drill |
| PUT | `/training-drills/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Update drill |
| GET | `/training-drills/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH | Get drill by ID |
| GET | `/training-drills` | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH | Get all drills |
| DELETE | `/training-drills/{id}` | ADMIN | Delete drill |

### 3.5 Player Training Assessment Endpoints (`/player-training-assessments`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-training-assessments` | ADMIN, HEAD_COACH | Create assessment |
| PUT | `/player-training-assessments/{id}` | ADMIN, HEAD_COACH | Update assessment |
| GET | `/player-training-assessments/{id}` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Get by ID |
| GET | `/player-training-assessments` | ADMIN, HEAD_COACH, ASSISTANT_COACH | Get all |
| DELETE | `/player-training-assessments/{id}` | ADMIN | Delete assessment |

### 3.6 Match Endpoints (`/matches`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/matches` | ADMIN, TEAM_MANAGER | Schedule match |
| PUT | `/matches/{id}` | ADMIN, TEAM_MANAGER | Update match |
| GET | `/matches/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get match by ID |
| GET | `/matches` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get all matches |
| DELETE | `/matches/{id}` | ADMIN | Delete match |

**Match Types:** `FRIENDLY`, `LEAGUE`, `CUP`, `TOURNAMENT`, `PRE_SEASON`
**Match Status:** `SCHEDULED`, `LIVE`, `FINISHED`, `CANCELLED`, `POSTPONED`

### 3.7 Match Event Endpoints (`/match-events`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/match-events` | ADMIN, HEAD_COACH | Record event |
| PUT | `/match-events/{id}` | ADMIN, HEAD_COACH | Update event |
| GET | `/match-events/{id}` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get event by ID |
| GET | `/match-events` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get all events |
| DELETE | `/match-events/{id}` | ADMIN | Delete event |

**Event Fields:** matchId, `playerKeycloakId` (String), teamId, eventType, minute, extraTime, description
**Event Types:** `GOAL`, `ASSIST`, `YELLOW_CARD`, `RED_CARD`, `SUBSTITUTION`, `INJURY`

### 3.8 Match Lineup Endpoints (`/match-lineups`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/match-lineups` | ADMIN, HEAD_COACH | Create lineup |
| PUT | `/match-lineups/{id}` | ADMIN, HEAD_COACH | Update lineup |
| GET | `/match-lineups/{id}` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get lineup by ID |
| GET | `/match-lineups` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get all lineups |
| DELETE | `/match-lineups/{id}` | ADMIN | Delete lineup |

### 3.9 Match Formation Endpoints (`/match-formations`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/match-formations` | ADMIN, HEAD_COACH | Create formation |
| PUT | `/match-formations/{id}` | ADMIN, HEAD_COACH | Update formation |
| GET | `/match-formations/{id}` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get formation by ID |
| GET | `/match-formations` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get all formations |
| DELETE | `/match-formations/{id}` | ADMIN | Delete formation |

**Formation Fields:** teamId, `setByCoachKeycloakId` (String), formation (e.g. "4-4-2"), tacticalApproach, formationDetails

### 3.10 Player Match Statistics Endpoints (`/player-match-statistics`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-match-statistics` | ADMIN, HEAD_COACH, PERFORMANCE_ANALYST | Record stats |
| PUT | `/player-match-statistics/{id}` | ADMIN, HEAD_COACH, PERFORMANCE_ANALYST | Update stats |
| GET | `/player-match-statistics/{id}` | ADMIN, HEAD_COACH, TEAM_MANAGER, PERFORMANCE_ANALYST | Get by ID |
| GET | `/player-match-statistics` | ADMIN, HEAD_COACH, TEAM_MANAGER, PERFORMANCE_ANALYST | Get all |
| DELETE | `/player-match-statistics/{id}` | ADMIN | Delete stats |

### 3.11 Match Performance Review Endpoints (`/match-performance-reviews`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/match-performance-reviews` | ADMIN, HEAD_COACH, PERFORMANCE_ANALYST | Create review |
| PUT | `/match-performance-reviews/{id}` | ADMIN, HEAD_COACH, PERFORMANCE_ANALYST | Update review |
| GET | `/match-performance-reviews/{id}` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get by ID |
| GET | `/match-performance-reviews` | ADMIN, HEAD_COACH, TEAM_MANAGER | Get all |
| DELETE | `/match-performance-reviews/{id}` | ADMIN | Delete review |

**Kafka Events Published:**
- `match.scheduled` - when a new match is scheduled
- `match.completed` - when a match finishes
- `match.cancelled` - when a match is cancelled
- `training.session.completed` - when a training session completes
- `training.session.cancelled` - when a training session is cancelled

---

## 4. Medical & Fitness Service

**Gateway Base URL:** `http://localhost:8080/api/medical-fitness`

### 4.1 Injury Endpoints (`/injuries`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/injuries` | ADMIN, DOCTOR | Report injury |
| PUT | `/injuries/{id}` | ADMIN, DOCTOR | Update injury |
| GET | `/injuries/{id}` | ADMIN, DOCTOR, HEAD_COACH, TEAM_MANAGER | Get injury by ID |
| GET | `/injuries` | ADMIN, DOCTOR, HEAD_COACH, TEAM_MANAGER | Get all injuries |
| DELETE | `/injuries/{id}` | ADMIN | Delete injury |

**Injury Types:** `MUSCLE_STRAIN`, `LIGAMENT_SPRAIN`, `FRACTURE`, `CONTUSION`, `TENDONITIS`, `DISLOCATION`, `CONCUSSION`
**Severity Levels:** `MINOR`, `MODERATE`, `SEVERE`, `CRITICAL`
**Injury Status:** `REPORTED`, `DIAGNOSED`, `TREATING`, `RECOVERING`, `RECOVERED`, `CHRONIC`

### 4.2 Diagnosis Endpoints (`/diagnoses`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/diagnoses` | ADMIN, DOCTOR | Create diagnosis |
| PUT | `/diagnoses/{id}` | ADMIN, DOCTOR | Update diagnosis |
| GET | `/diagnoses/{id}` | ADMIN, DOCTOR, HEAD_COACH | Get diagnosis by ID |
| GET | `/diagnoses` | ADMIN, DOCTOR, HEAD_COACH | Get all diagnoses |
| DELETE | `/diagnoses/{id}` | ADMIN | Delete diagnosis |

**Diagnosis Fields:** `playerKeycloakId` (String), `doctorKeycloakId` (String), injuryId, diagnosis, medicalNotes, testResults, recommendations

### 4.3 Treatment Endpoints (`/treatments`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/treatments` | ADMIN, DOCTOR | Create treatment |
| PUT | `/treatments/{id}` | ADMIN, DOCTOR | Update treatment |
| GET | `/treatments/{id}` | ADMIN, DOCTOR, HEAD_COACH, PHYSIOTHERAPIST | Get by ID |
| GET | `/treatments` | ADMIN, DOCTOR, HEAD_COACH, PHYSIOTHERAPIST | Get all |
| DELETE | `/treatments/{id}` | ADMIN | Delete treatment |

**Treatment Status:** `PLANNED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

### 4.4 Rehabilitation Endpoints (`/rehabilitations`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/rehabilitations` | ADMIN, DOCTOR, PHYSIOTHERAPIST | Create rehab |
| PUT | `/rehabilitations/{id}` | ADMIN, DOCTOR, PHYSIOTHERAPIST | Update rehab |
| GET | `/rehabilitations/{id}` | ADMIN, DOCTOR, PHYSIOTHERAPIST, HEAD_COACH | Get by ID |
| GET | `/rehabilitations` | ADMIN, DOCTOR, PHYSIOTHERAPIST, HEAD_COACH | Get all |
| DELETE | `/rehabilitations/{id}` | ADMIN | Delete rehab |

**Rehab Status:** `NOT_STARTED`, `IN_PROGRESS`, `COMPLETED`, `PAUSED`

### 4.5 Recovery Program Endpoints (`/recovery-programs`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/recovery-programs` | ADMIN, DOCTOR, PHYSIOTHERAPIST | Create program |
| PUT | `/recovery-programs/{id}` | ADMIN, DOCTOR, PHYSIOTHERAPIST | Update program |
| GET | `/recovery-programs/{id}` | ADMIN, DOCTOR, PHYSIOTHERAPIST, HEAD_COACH, FITNESS_COACH | Get by ID |
| GET | `/recovery-programs` | ADMIN, DOCTOR, PHYSIOTHERAPIST, HEAD_COACH, FITNESS_COACH | Get all |
| DELETE | `/recovery-programs/{id}` | ADMIN | Delete program |

### 4.6 Fitness Test Endpoints (`/fitness-tests`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/fitness-tests` | ADMIN, DOCTOR, FITNESS_COACH | Create test |
| PUT | `/fitness-tests/{id}` | ADMIN, DOCTOR, FITNESS_COACH | Update test |
| GET | `/fitness-tests/{id}` | ADMIN, DOCTOR, FITNESS_COACH, HEAD_COACH | Get by ID |
| GET | `/fitness-tests` | ADMIN, DOCTOR, FITNESS_COACH, HEAD_COACH | Get all |
| DELETE | `/fitness-tests/{id}` | ADMIN | Delete test |

**Fitness Test Fields:** `playerKeycloakId` (String), `conductedByDoctorKeycloakId` (String), testType, testDate, result, unit, category
**Test Types:** `VO2_MAX`, `SPEED_TEST`, `AGILITY_TEST`, `STRENGTH_TEST`, `FLEXIBILITY_TEST`, `ENDURANCE_TEST`, `BODY_COMPOSITION`

### 4.7 Training Load Endpoints (`/training-loads`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/training-loads` | ADMIN, FITNESS_COACH | Record training load |
| PUT | `/training-loads/{id}` | ADMIN, FITNESS_COACH | Update training load |
| GET | `/training-loads/{id}` | ADMIN, FITNESS_COACH, HEAD_COACH, DOCTOR | Get by ID |
| GET | `/training-loads` | ADMIN, FITNESS_COACH, HEAD_COACH, DOCTOR | Get all |
| DELETE | `/training-loads/{id}` | ADMIN | Delete training load |

**Kafka Events Published:**
- `injury.reported` - when a new injury is reported
- `injury.status.changed` - when an injury status changes
- `fitness.test.completed` - when a fitness test is completed
- `treatment.completed` - when a treatment is marked complete
- `rehabilitation.completed` - when a rehabilitation program completes

---

## 5. Reports & Analytics Service

**Gateway Base URL:** `http://localhost:8080/api/reports-analytics`

### 5.1 Match Analysis Endpoints (`/match-analyses`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/match-analyses` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH | Create analysis |
| PUT | `/match-analyses/{id}` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH | Update analysis |
| GET | `/match-analyses/{id}` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER, SPORT_MANAGER | Get by ID |
| GET | `/match-analyses` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER, SPORT_MANAGER | Get all |
| DELETE | `/match-analyses/{id}` | ADMIN | Delete analysis |

**Match Analysis Fields:** matchId, `analyzedByUserKeycloakId` (String), possession, shots, shotsOnTarget, passes, passAccuracy, tackles, fouls, corners, offsides, yellowCards, redCards, keyMoments, tacticalAnalysis, playerRatings, heatmapData, heatmapType

### 5.2 Player Analytics Endpoints (`/player-analytics`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/player-analytics` | ADMIN, PERFORMANCE_ANALYST | Create analytics |
| PUT | `/player-analytics/{id}` | ADMIN, PERFORMANCE_ANALYST | Update analytics |
| GET | `/player-analytics/{id}` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER | Get by ID |
| GET | `/player-analytics` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER | Get all |
| DELETE | `/player-analytics/{id}` | ADMIN | Delete analytics |

**Player Analytics Fields:** `playerKeycloakId` (String), `analyzedByUserKeycloakId` (String), season, matchesPlayed, goals, assists, averageRating, trainingSessions, trainingAttendanceRate, injuries, fitnessScore, performanceTrend, kpiData

### 5.3 Scout Report Endpoints (`/scout-reports`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/scout-reports` | ADMIN, SCOUT | Create scout report |
| PUT | `/scout-reports/{id}` | ADMIN, SCOUT | Update report |
| GET | `/scout-reports/{id}` | ADMIN, SCOUT, SPORT_MANAGER | Get by ID |
| GET | `/scout-reports` | ADMIN, SCOUT, SPORT_MANAGER | Get all |
| DELETE | `/scout-reports/{id}` | ADMIN | Delete report |

**Scout Report Fields:** `scoutKeycloakId` (String), outerPlayerId, outerTeamId, playerName, playerAge, playerPosition, playerNationality, scoutDate, technicalRating, tacticalRating, physicalRating, mentalRating, overallRating, strengths, weaknesses, recommendation, notes, marketValueEstimate

### 5.4 Sponsor Contract Offer Endpoints (`/sponsor-contract-offers`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/sponsor-contract-offers` | ADMIN, SPONSOR | Create offer |
| PUT | `/sponsor-contract-offers/{id}` | ADMIN, SPONSOR | Update offer |
| GET | `/sponsor-contract-offers/{id}` | ADMIN, SPONSOR, SPORT_MANAGER, TEAM_MANAGER | Get by ID |
| GET | `/sponsor-contract-offers` | ADMIN, SPONSOR, SPORT_MANAGER, TEAM_MANAGER | Get all |
| DELETE | `/sponsor-contract-offers/{id}` | ADMIN | Delete offer |

**Offer Fields:** `sponsorKeycloakId` (String), sponsorName, offerTitle, description, contractValue, startDate, endDate, sponsorshipType, status, terms, benefits, targetTeamId

### 5.5 Team Analytics Endpoints (`/team-analytics`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/team-analytics` | ADMIN, PERFORMANCE_ANALYST | Create team analytics |
| PUT | `/team-analytics/{id}` | ADMIN, PERFORMANCE_ANALYST | Update analytics |
| GET | `/team-analytics/{id}` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER, SPORT_MANAGER | Get by ID |
| GET | `/team-analytics` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER, SPORT_MANAGER | Get all |
| DELETE | `/team-analytics/{id}` | ADMIN | Delete analytics |

**Team Analytics Fields:** teamId, `analyzedByUserKeycloakId` (String), season, matchesPlayed, wins, draws, losses, goalsFor, goalsAgainst, goalDifference, cleanSheets, averagePossession, averagePassAccuracy, strengthAnalysis, weaknessAnalysis, performanceTrend

### 5.6 Training Analytics Endpoints (`/training-analytics`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/training-analytics` | ADMIN, PERFORMANCE_ANALYST | Create training analytics |
| PUT | `/training-analytics/{id}` | ADMIN, PERFORMANCE_ANALYST | Update analytics |
| GET | `/training-analytics/{id}` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, FITNESS_COACH | Get by ID |
| GET | `/training-analytics` | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, FITNESS_COACH | Get all |
| DELETE | `/training-analytics/{id}` | ADMIN | Delete analytics |

**Training Analytics Fields:** teamId, `analyzedByUserKeycloakId` (String), season, totalSessions, averageAttendanceRate, averageSessionDuration, sessionTypes, intensityDistribution, playerProgressData, injuryCorrelation, recommendations

---

## 6. Notification & Mail Service

**Gateway Base URL:** `http://localhost:8080/api/notification-mail`

### 6.1 Notification Endpoints (`/notifications`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/notifications` | ADMIN | Create notification |
| PUT | `/notifications/{id}` | ADMIN | Update notification |
| GET | `/notifications/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER | Get by ID |
| GET | `/notifications` | ADMIN | Get all notifications |
| DELETE | `/notifications/{id}` | ADMIN | Delete notification |
| GET | `/notifications/recipient/{keycloakId}` | ALL AUTHENTICATED | Get notifications for user |
| GET | `/notifications/recipient/{keycloakId}/unread` | ALL AUTHENTICATED | Get unread notifications |
| PATCH | `/notifications/{id}/read` | ALL AUTHENTICATED | Mark notification as read |

**Notification Fields:** `recipientUserKeycloakId` (String), notificationType, category, status, title, message, emailSubject, emailBody, relatedEntityId, relatedEntityType, actionUrl

**Notification Types:** `IN_APP`, `EMAIL`, `BOTH`
**Notification Categories:** `INJURY`, `KPI`, `TRANSFER`, `REPORT`, `MATCH_REMINDER`, `TRAINING_REMINDER`, `SYSTEM`
**Notification Status:** `PENDING`, `SENT`, `DELIVERED`, `READ`, `FAILED`

**Auto-Generated Notifications (via Kafka events):**
- Injury reported -> notification to coaches and medical staff
- Match scheduled -> notification to players and coaches
- Match completed -> notification to analysts
- Training cancelled -> notification to players
- Player status changed -> notification to coaches
- Transfer completed -> notification to managers

### 6.2 Alert Endpoints (`/alerts`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/alerts` | ADMIN | Create alert |
| PUT | `/alerts/{id}` | ADMIN | Update alert |
| GET | `/alerts/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH, DOCTOR | Get by ID |
| GET | `/alerts` | ADMIN, TEAM_MANAGER, HEAD_COACH, DOCTOR | Get all alerts |
| DELETE | `/alerts/{id}` | ADMIN | Delete alert |
| GET | `/alerts/target/{keycloakId}` | ADMIN, TEAM_MANAGER, HEAD_COACH, DOCTOR | Get alerts for user |
| GET | `/alerts/unacknowledged` | ADMIN, TEAM_MANAGER, HEAD_COACH, DOCTOR | Get unacknowledged alerts |
| PATCH | `/alerts/{id}/acknowledge?acknowledgedByKeycloakId=xxx` | ADMIN, TEAM_MANAGER, HEAD_COACH, DOCTOR | Acknowledge alert |
| PATCH | `/alerts/{id}/resolve` | ADMIN, TEAM_MANAGER, HEAD_COACH | Resolve alert |

**Alert Fields:** alertType, priority, title, message, description, `targetUserKeycloakId` (String), targetRole, relatedEntityId, relatedEntityType, actionRequired, metadata (JSON string)

**Alert Types:** `INJURY_REPORTED`, `KPI_DROP`, `TRANSFER_COMPLETED`, `REPORT_GENERATED`, `MATCH_UPCOMING`, `TRAINING_CANCELLED`, `MEDICAL_CHECKUP_DUE`, `CONTRACT_EXPIRING`
**Alert Priority:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

### 6.3 Message Endpoints (`/messages`)

| Method | Endpoint | Roles | Description |
|--------|----------|-------|-------------|
| POST | `/messages` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER, DOCTOR | Send message |
| PUT | `/messages/{id}` | ADMIN | Update message |
| GET | `/messages/{id}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER, DOCTOR | Get by ID |
| GET | `/messages` | ADMIN | Get all messages |
| DELETE | `/messages/{id}` | ADMIN | Delete message |
| GET | `/messages/recipient/{keycloakId}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER, PLAYER, DOCTOR, FITNESS_COACH | Get received messages |
| GET | `/messages/sender/{keycloakId}` | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER, DOCTOR | Get sent messages |

**Message Fields:** `senderUserKeycloakId` (String), `recipientUserKeycloakId` (String), subject, content, status, relatedEntityId, relatedEntityType, attachments, parentMessageId (for replies/threads)

**Message Status:** `SENT`, `DELIVERED`, `READ`

---

## 7. Key User Flows (Updated)

### 7.1 User Registration Flow
1. User registers via Keycloak (gets `keycloakId` UUID)
2. Frontend calls `POST /users` with user details and keycloakId
3. User is assigned default `FAN` role
4. Admin changes role if needed (e.g., to `PLAYER`, `DOCTOR`)
5. Kafka event `user.created` is published
6. Notification service creates welcome notification

### 7.2 Training Session Flow
1. Coach creates training session (`POST /training-sessions`)
2. Kafka event published
3. Notification service auto-creates notifications for assigned players
4. Training session occurs
5. Coach records attendance (`POST /training-attendance`)
6. Coach adds drills and assessments
7. When completed, Kafka event `training.session.completed` is published

### 7.3 Match Management Flow
1. Manager schedules match (`POST /matches`)
2. Kafka event `match.scheduled` is published -> notifications sent to players/coaches
3. Coach creates lineup and formation
4. Match occurs - events recorded live (`POST /match-events`)
5. Statistics recorded (`POST /player-match-statistics`)
6. Match completed -> Kafka event `match.completed` published
7. Analyst creates match analysis (`POST /match-analyses`)
8. Coach creates performance review (`POST /match-performance-reviews`)

### 7.4 Injury Management Flow
1. Doctor reports injury (`POST /injuries`)
2. Kafka event `injury.reported` published -> alert sent to coaches and medical staff
3. Doctor creates diagnosis (`POST /diagnoses`)
4. Treatment plan created (`POST /treatments`)
5. Rehabilitation program started (`POST /rehabilitations`)
6. Recovery program assigned (`POST /recovery-programs`)
7. Status updated -> Kafka event `injury.status.changed` published
8. When treatment/rehab completed -> events published -> notifications sent

### 7.5 Transfer Flow
1. Sport Manager creates transfer request (`POST /player-transfers-outgoing` or `/player-transfers-incoming`)
2. Transfer approved and processed
3. Kafka event `transfer.completed` published
4. Notification service auto-notifies managers and relevant staff
5. New contract created (`POST /player-contracts`)
6. Roster updated (`PUT /rosters/{id}`)

### 7.6 Internal Messaging Flow
1. Coach sends message to Doctor about player injury (`POST /messages`)
2. Doctor receives notification about new message
3. Doctor views messages (`GET /messages/recipient/{keycloakId}`)
4. Doctor replies (`POST /messages` with `parentMessageId`)
5. Thread continues as needed

---

## 8. Frontend Integration Notes

### 8.1 Authentication
- Use Keycloak JS adapter for login/logout
- Store JWT token and include as `Authorization: Bearer <token>` header
- Decode JWT to get `sub` claim = user's `keycloakId`
- Decode JWT to get `realm_access.roles` = user's roles for UI visibility

### 8.2 API Gateway
- **All API calls go through the Gateway** at `http://localhost:8080`
- Routes are prefixed:
  - `/api/user-management/**` -> User Management Service
  - `/api/player-management/**` -> Player Management Service
  - `/api/training-match/**` -> Training & Match Service
  - `/api/medical-fitness/**` -> Medical & Fitness Service
  - `/api/reports-analytics/**` -> Reports & Analytics Service
  - `/api/notification-mail/**` -> Notification & Mail Service

### 8.3 Request/Response Format
- All requests/responses use **JSON**
- Dates use ISO format: `"2025-03-15"` (LocalDate) or `"2025-03-15T10:30:00"` (LocalDateTime)
- Enums are sent as **strings**: `"GOAL"`, `"MINOR"`, `"SCHEDULED"`
- User references are always **String keycloakId** (UUID format)

### 8.4 Error Handling
All services return consistent error responses:
```json
{
  "status": 404,
  "message": "Resource not found with id: 123",
  "timestamp": "2025-03-15T10:30:00"
}
```

HTTP Status Codes:
- `201 Created` - resource created successfully
- `200 OK` - read/update successful
- `204 No Content` - delete successful
- `400 Bad Request` - validation error
- `401 Unauthorized` - missing or invalid JWT token
- `403 Forbidden` - user doesn't have required role
- `404 Not Found` - resource not found
- `500 Internal Server Error` - unexpected server error

### 8.5 Role-Based UI Visibility
Show/hide menu items and buttons based on the user's role from the JWT token:

| Feature Area | Roles That Can Access |
|-------------|----------------------|
| User Management | ADMIN |
| Player Profiles | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER |
| Contracts & Transfers | ADMIN, SPORT_MANAGER, TEAM_MANAGER |
| Training Management | ADMIN, HEAD_COACH, ASSISTANT_COACH, FITNESS_COACH |
| Match Management | ADMIN, TEAM_MANAGER, HEAD_COACH |
| Medical & Fitness | ADMIN, DOCTOR, PHYSIOTHERAPIST, FITNESS_COACH |
| Reports & Analytics | ADMIN, PERFORMANCE_ANALYST, HEAD_COACH, TEAM_MANAGER |
| Scouting | ADMIN, SCOUT, SPORT_MANAGER |
| Sponsorship | ADMIN, SPONSOR, SPORT_MANAGER |
| Notifications | ALL AUTHENTICATED (own notifications) |
| Messages | ADMIN, TEAM_MANAGER, HEAD_COACH, SPORT_MANAGER, DOCTOR |
| Alerts | ADMIN, TEAM_MANAGER, HEAD_COACH, DOCTOR |

### 8.6 Notification Polling
- Poll `GET /notifications/recipient/{keycloakId}/unread` periodically for notification badge count
- Poll `GET /alerts/unacknowledged` for alert indicators
- Consider WebSocket implementation for real-time updates (future enhancement)

---

**Document Version:** 2.0
**Last Updated:** 2025
**Prepared for:** Frontend Development Team & UI/UX Design Team
