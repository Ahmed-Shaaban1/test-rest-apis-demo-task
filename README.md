### README.md
```markdown
# 📚 Bookstore API Automation Framework

This is an automated API testing framework built for the [FakeRestAPI](https://fakerestapi.azurewebsites.net/) Bookstore endpoints. It covers CRUD operations for both Books and Authors with full integration into Docker and CI/CD pipelines.

---

## ✅ Features
- Java + TestNG + RestAssured
- Allure reporting support
- Reusable ApiClient and POJO model classes
- Edge case handling (404, invalid payload, etc.)
- Runs via Docker
- CI-ready with GitHub Actions

---

## 🛠️ Tech Stack
- Java 21
- Maven
- RestAssured
- TestNG
- Allure Report
- Docker
- GitHub Actions

---

## 🚀 How to Run Locally

### 1. Clone the project
```bash
git clone https://github.com/your-username/bookstore-api-tests.git
cd bookstore-api-tests
```

### 2. Run tests using Maven
```bash
mvn clean test -DbaseUrl=https://fakerestapi.azurewebsites.net
```

### 3. View Allure Report
```bash
allure serve target/allure-results
```

> 📌 Make sure [Allure CLI](https://docs.qameta.io/allure/#_installing_a_commandline) is installed

---

## 🐳 Run with Docker
```bash
# Build the Docker image
docker build -t api-tests .

# Run the test suite
docker run --rm api-tests
```

---

## 🔁 Folder Structure
```
project-root/
├── src/test/java
│   ├── config          # ConfigReader for dynamic URLs
│   ├── models          # Book and Author POJOs
│   ├── tests           # TestNG test classes
│   └── utilities       # Reusable API client
├── testng.xml          # TestNG suite config
├── Dockerfile          # Docker runner
├── pom.xml             # Maven config
├── .github/workflows   # GitHub Actions pipeline
└── README.md           # You are here :)
```

---

## 🧪 Test Coverage
- `GET`, `POST`, `PUT`, `DELETE` for Books and Authors
- Edge cases: Not Found, Invalid Payloads

---

## 📸 Sample Report
Allure HTML report will be auto-generated under `target/allure-results`.

---

## 👤 Author
**Ahmed Sherif**

---

## 📄 License
This is a demo project. Use freely for educational purposes.
```