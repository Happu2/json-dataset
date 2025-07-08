# 📦 JSON Dataset API (Spring Boot + PostgreSQL)

A flexible Spring Boot application that provides a REST API for managing dynamic JSON datasets with PostgreSQL JSONB storage. Perfect for applications that need to store and query schema-less data efficiently.

## ✨ Features

- **Dynamic JSON Storage**: Insert JSON records with any structure into named datasets
- **Flexible Querying**: Sort and group records by any field in the JSON data
- **PostgreSQL JSONB**: Leverages PostgreSQL's native JSON support for efficient storage and querying
- **RESTful API**: Clean, intuitive API design following REST principles
- **Schema-less**: No predefined schema required - store any JSON structure

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- PostgreSQL 12+ running locally
- Maven 3.6+ (or use the included Maven wrapper)

### 1. Database Setup

```sql
-- Create the database
CREATE DATABASE dispatchdb;
```

### 2. Clone and Run

```bash
git clone <your-repo-url>
cd json-dataset-api
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## 🛠️ Tech Stack

- **Java 17+** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring Data JPA** - Data access layer
- **PostgreSQL** - Database with JSONB support
- **Jackson** - JSON processing
- **Lombok** - Boilerplate code reduction
- **Hibernate Types** - JSONB mapping support

## ⚙️ Configuration

**application.properties**

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/dispatchdb
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

# Application Configuration
server.port=8080
```

## 📬 API Documentation

### Base URL
```
http://localhost:8080/api/dataset
```

### 1. Insert Record

**Endpoint**: `POST /api/dataset/record`

**Description**: Add a new JSON record to a dataset

**Request Body**:
```json
{
  "dataset": "my-dataset",
  "data": {
    "name": "Alice",
    "age": 25,
    "city": "New York",
    "skills": ["Java", "Spring Boot", "PostgreSQL"]
  }
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/dataset/record \
  -H "Content-Type: application/json" \
  -d '{
    "dataset": "users",
    "data": {
      "name": "Alice",
      "age": 25,
      "city": "New York"
    }
  }'
```

**Response**:
```json
{
  "id": 1,
  "dataset": "users",
  "data": {
    "name": "Alice",
    "age": 25,
    "city": "New York"
  },
  "createdAt": "2024-01-15T10:30:00Z"
}
```

### 2. Query Records with Sorting

**Endpoint**: `GET /api/dataset/{datasetName}/query`

**Parameters**:
- `sortBy` (optional): Field name to sort by
- `order` (optional): Sort order (`asc` or `desc`, default: `asc`)

**Examples**:

```bash
# Sort by age in ascending order
curl -X GET "http://localhost:8080/api/dataset/users/query?sortBy=age&order=asc"

# Sort by name in descending order
curl -X GET "http://localhost:8080/api/dataset/users/query?sortBy=name&order=desc"

# Get all records without sorting
curl -X GET "http://localhost:8080/api/dataset/users/query"
```

### 3. Query Records with Grouping

**Endpoint**: `GET /api/dataset/{datasetName}/query`

**Parameters**:
- `groupBy`: Field name to group by

**Example**:
```bash
# Group users by city
curl -X GET "http://localhost:8080/api/dataset/users/query?groupBy=city"
```

**Response**:
```json
{
  "New York": [
    {
      "id": 1,
      "data": {"name": "Alice", "age": 25, "city": "New York"}
    }
  ],
  "Boston": [
    {
      "id": 2,
      "data": {"name": "Bob", "age": 30, "city": "Boston"}
    }
  ]
}
```

## 📂 Project Structure

```
src/main/java/com/yourpackage/
├── controller/
│   └── DatasetController.java
├── service/
│   └── DatasetService.java
├── dto/
│   ├── RecordRequest.java
│   └── RecordResponse.java
├── entity/
│   └── DatasetRecord.java
├── repository/
│   └── DatasetRepository.java
└── JsonDatasetApiApplication.java
```

## 🧪 Testing

### Sample Data

You can use these examples to test the API:

```bash
# Insert user records
curl -X POST http://localhost:8080/api/dataset/record \
  -H "Content-Type: application/json" \
  -d '{"dataset": "users", "data": {"name": "Alice", "age": 25, "city": "New York"}}'

curl -X POST http://localhost:8080/api/dataset/record \
  -H "Content-Type: application/json" \
  -d '{"dataset": "users", "data": {"name": "Bob", "age": 30, "city": "Boston"}}'

# Insert product records
curl -X POST http://localhost:8080/api/dataset/record \
  -H "Content-Type: application/json" \
  -d '{"dataset": "products", "data": {"name": "Laptop", "price": 999.99, "category": "Electronics"}}'
```

### Query Examples

```bash
# Sort users by age
curl -X GET "http://localhost:8080/api/dataset/users/query?sortBy=age&order=asc"

# Group users by city
curl -X GET "http://localhost:8080/api/dataset/users/query?groupBy=city"

# Sort products by price
curl -X GET "http://localhost:8080/api/dataset/products/query?sortBy=price&order=desc"
```

## 🔧 Development

### Running Tests

```bash
./mvnw test
```

### Building for Production

```bash
./mvnw clean package
java -jar target/json-dataset-api-0.0.1-SNAPSHOT.jar
```

## 📈 Performance Considerations

- **Indexing**: Consider creating GIN indexes on JSONB columns for better query performance
- **Data Size**: Monitor JSON document sizes to avoid performance issues
- **Connection Pooling**: Configure appropriate connection pool settings for production

### PostgreSQL Optimization

```sql
-- Create GIN index for better JSONB query performance
CREATE INDEX idx_dataset_record_data_gin ON dataset_record USING GIN (data);

-- Index for dataset name queries
CREATE INDEX idx_dataset_record_dataset ON dataset_record (dataset);
```

## 🚀 Deployment

### Environment Variables

For production deployments, use environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/your-db-name
export SPRING_DATASOURCE_USERNAME=your-username
export SPRING_DATASOURCE_PASSWORD=your-password
```

### Docker Support

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/json-dataset-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## ✅ TODO / Roadmap

- [ ] Add comprehensive unit and integration tests
- [ ] Implement API documentation with Swagger/OpenAPI
- [ ] Add pagination support for large datasets
- [ ] Implement data validation and schema constraints
- [ ] Add authentication and authorization
- [ ] Create Docker Compose setup
- [ ] Add monitoring and metrics
- [ ] Implement caching for frequently accessed data
- [ ] Add bulk insert/update operations
- [ ] Support for complex JSON queries (nested field filtering)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request


## 💡 Credits

Built with ❤️ using Spring Boot

---

**Need help?** Open an issue or reach out to [YourNameHere]

**Want to contribute?** Pull requests are welcome!
