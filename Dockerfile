# Use Eclipse Temurin Java 21 image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Install Maven, unzip, curl (for Allure CLI)
RUN apt-get update && \
    apt-get install -y maven unzip curl && \
    apt-get clean

# Install Allure CLI (safe way)
RUN apt-get update && apt-get install -y curl unzip && \
    curl -fLo allure.zip https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.zip && \
    unzip -q allure.zip -d /opt && \
    ln -s /opt/allure-2.27.0/bin/allure /usr/bin/allure && \
    rm -f allure.zip

# Copy Maven project files
COPY . .

# Make sure Maven uses a proper Java version
RUN sed -i 's/<source>5<\/source>/<source>21<\/source>/g' pom.xml || true
RUN sed -i 's/<target>5<\/target>/<target>21<\/target>/g' pom.xml || true

# Build and run tests
RUN mvn clean test

# Generate Allure report
RUN allure generate target/allure-results --clean -o target/allure-report

# Set default command (optional: start shell or display report)
CMD ["ls", "-la", "target/allure-report"]
