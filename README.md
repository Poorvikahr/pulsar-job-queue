# Pulsar Job Queue

A distributed background job processing system built with Java and Spring Boot — inspired by tools like Celery and Sidekiq. It lets an application hand off time-consuming tasks (image resizing, email sending, report generation, etc.) to be processed asynchronously by background workers, instead of blocking API requests.

## Problem

Applications often need to perform tasks that shouldn't block the main request — sending emails, processing images, generating reports. Doing this work synchronously inside the request makes users wait longer and risks losing work if the server crashes mid-task.

**Pulsar** solves this by accepting jobs instantly, queuing them, and letting independent worker processes pick them up and execute them reliably — with status tracking throughout.

## How It Works

1. A client submits a job via `POST /jobs` with a job type and payload
2. The job is saved to the database with status `QUEUED`, and the API responds immediately with a job ID
3. A background worker polls the database every few seconds for queued jobs
4. The worker picks up the oldest job, marks it `IN_PROGRESS`, executes it, then marks it `COMPLETED` (or `FAILED` if an error occurs)
5. The client can check job status anytime via `GET /jobs/{id}`

## Tech Stack

- **Java 17** + **Spring Boot**
- **Spring Data JPA** for persistence
- **H2** (in-memory database, for development)
- **Lombok** for boilerplate reduction
- Scheduled polling via Spring's `@Scheduled`

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/jobs` | Submit a new job |
| `GET` | `/jobs/{id}` | Check the status of a job |

**Example request:**
```json
POST /jobs
{
  "type": "resize_image",
  "payload": "some-image-url"
}
```

**Example response:**
```json
{
  "id": "4176ee57-0436-4ef7-b82a-5930b0d17e5d",
  "type": "resize_image",
  "payload": "some-image-url",
  "status": "QUEUED",
  "createdAt": "2026-07-29T11:39:02.28",
  "updatedAt": "2026-07-29T11:39:02.28"
}
```

## Running Locally

```bash
git clone https://github.com/Poorvikahr/pulsar-job-queue.git
cd pulsar-job-queue/distqueue
./mvnw spring-boot:run
```

The app runs on `http://localhost:8081` by default (configurable in `application.properties`).

## Roadmap

- [x] Job submission and status tracking API
- [x] Background worker with polling-based job execution
- [ ] Multiple concurrent workers with safe job locking (no double-processing)
- [ ] Automatic retries with exponential backoff and dead-letter queue for failed jobs
- [ ] Redis-backed queue for faster, production-style job dispatch
- [ ] Delayed/scheduled job support
- [ ] Live dashboard for job monitoring
- [ ] Dockerized deployment

## Why This Project

Most background job systems (Celery, Sidekiq, SQS) are used as black boxes. This project builds the core mechanics from scratch — job queuing, worker polling, concurrency-safe processing, and failure handling — to demonstrate a solid understanding of distributed systems fundamentals.
