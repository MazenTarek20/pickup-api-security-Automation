# 🔐 Pickup API Security Automation

An automated **API security testing suite** built in Java for the Pickup platform, using **REST Assured** and **TestNG**. 
The suite validates authentication, authorization, injection resistance, input validation, and rate limiting across three core API services.

---

## 📁 Project Structure

```
pickup-api-security-Automation/
│
├── API 1/                        # Security test cases for API service 1
├── API 2/                        # Security test cases for API service 2
├── API 3/                        # Security test cases for API service 3
│
└── CODE/
    └── security-test/            # Core test utilities, base classes & helpers
        ├── src/
        │   ├── main/java/        # Utilities, constants, config loaders
        │   └── test/java/        # Test classes organized by security category
        ├── pom.xml               # Maven dependencies & build config
        └── testng.xml            # TestNG suite configuration
```

---

## 🎯 Security Coverage

| Category | Description |
|---|---|
| **Authentication & Authorization** | Token validation, expired tokens, privilege escalation, broken access control |
| **Injection Attacks** | SQL injection, NoSQL injection, XSS payloads in request bodies/params |
| **Input Validation** | Malformed JSON, boundary values, missing required fields, type mismatches |
| **Rate Limiting & DoS** | Flood requests, endpoint throttling verification, abuse detection |
| **Sensitive Data Exposure** | Response header inspection, verbose error message leakage |
| **Business Logic Flaws** | IDOR, parameter tampering, unauthorized resource access |

---

## 🛠️ Tech Stack

| Tool | Purpose |
|---|---|
| **Java 17+** | Core language |
| **REST Assured** | HTTP request execution and response assertions |
| **TestNG** | Test execution, grouping, and parallel runs |
| **Maven** | Dependency management and build lifecycle |
| **Allure Reports** | Rich test reporting |
| **dotenv / config.properties** | Environment-based configuration management |

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Apache Maven 3.8+
- IDE: IntelliJ IDEA or Eclipse (recommended)

### Clone the Repository

```bash
git clone https://github.com/MazenTarek20/pickup-api-security-Automation.git
cd pickup-api-security-Automation
```

### Configure Environment

Create a `config.properties` file inside `CODE/security-test/src/main/resources/` (never commit this file):

```properties
base.url=https://your-api-staging-url.com
auth.token=your_auth_token_here
api.key=your_api_key_here
env=staging
```

> ⚠️ **Important:** Add `config.properties` to `.gitignore`. Never commit credentials to version control.

### Install Dependencies

```bash
cd CODE/security-test
mvn clean install -DskipTests
```

---

## 🧪 Running the Tests

### Run the full security suite

```bash
mvn test
```

### Run tests for a specific API

```bash
# API 1 test group
mvn test -Dgroups="api1"

# API 2 test group
mvn test -Dgroups="api2"

# API 3 test group
mvn test -Dgroups="api3"
```

### Run a specific test category

```bash
# Authentication tests only
mvn test -Dgroups="authentication"

# Injection tests only
mvn test -Dgroups="injection"
```

### Run a specific test class

```bash
mvn test -Dtest=AuthorizationSecurityTest
```

### Generate Allure Report

```bash
mvn allure:serve
```

---

## 📊 Test Organization by API

### API 1 — Authentication & User Endpoints
- Token expiry and invalidation checks
- Brute-force login protection
- Unauthorized role access attempts
- JWT tampering and algorithm confusion

### API 2 — Order / Pickup Operations
- IDOR on order and pickup IDs
- Parameter tampering (e.g., price, status fields)
- SQL/NoSQL injection in filter/search parameters
- Access control between user roles (driver vs. customer)

### API 3 — Notifications / Webhooks
- Payload injection via webhook body
- Replay attack simulation
- Missing/invalid signature verification
- Mass assignment vulnerabilities

---

## 🧩 Example Test Structure

```java
@Test(groups = {"authentication", "api1"})
public void shouldReturn401_WhenTokenIsExpired() {
    given()
        .header("Authorization", "Bearer " + EXPIRED_TOKEN)
    .when()
        .get(Endpoints.USER_PROFILE)
    .then()
        .statusCode(401)
        .body("error", equalTo("Token has expired"));
}
```

---

## 📂 Key Files

| File | Purpose |
|---|---|
| `CODE/security-test/pom.xml` | Maven project config and dependencies |
| `CODE/security-test/testng.xml` | TestNG suite definition and group config |
| `src/main/java/.../BaseTest.java` | Shared REST Assured setup and teardown |
| `src/main/java/.../ConfigReader.java` | Loads environment config from properties file |
| `src/main/java/.../Endpoints.java` | Central endpoint constants |
| `src/test/java/...` | All security test classes |

---

## 🛡️ Security Testing Principles

1. **Staging only** — never run these tests against a production environment.
2. **Least privilege test accounts** — use dedicated accounts with minimum permissions.
3. **No side effects** — tests clean up any data they create.
4. **Responsible disclosure** — any discovered vulnerabilities must be reported internally before being committed or published.

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/add-idor-tests`
3. Write tests following the existing class and method naming conventions
4. Ensure all tests pass: `mvn test`
5. Open a pull request with a clear description of the security scenario covered

---

## ⚠️ Disclaimer

This project is intended **solely for authorized security testing** of the Pickup platform in controlled, non-production environments.
Unauthorized use of these scripts against systems you do not own or have explicit permission to test is illegal and unethical.

---

## 👤 Author

**Mazen Tarek**
- GitHub: [@MazenTarek20](https://github.com/MazenTarek20)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
