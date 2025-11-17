# PlaySite Management Application

## Overview
The PlaySite Management Application is a Spring Boot-based project designed to manage play sites, attractions, and kids. It allows you to:
- Create, update, and delete play sites.
- Add or remove kids from play sites or queues.
- Calculate play site utilisation and daily visitor counts.

## Endpoints

### Play Sites
- `GET /playsites`: Get all play sites.
- `POST /playsites`: Create a play site.
- `GET /playsites/{id}`: Get a play site.
- `PUT /playsites/{id}`: Update a play site.
- `DELETE /playsites/{id}`: Delete a play site.

### Kids
- `POST /playsites/{playSiteId}/kids`: Add a kid to a play site or queue.
- `DELETE /playsites/{playSiteId}/kids/{ticketNumber}`: Remove a kid from playsite.

### Statistics
- `GET /playsites/visitors/today`: Get total visitors of all play sites.
- `GET /playsites/{playSiteId}/utilisation`: Get play site utilisation.

### To Run

1. Clone this repository
2. Build repository `mvn clean install`
3. Run repository `mvn spring-boot:run`
4. Application should be available at `http://localhost:8080`


