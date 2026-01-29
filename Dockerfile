# Use the latest official Maven image with the latest Java version
FROM maven:latest

# Set environment variables
ENV MAVEN_CONFIG=/root/.m2

# Install any additional dependencies if needed
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy entire root directory from source into container /app
COPY . .

# Declare the volume mount point inside the container
VOLUME /app/target

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Build the project
RUN mvn clean install -DskipTests

# Set the default command to run tests
CMD ["mvn", "clean", "test", "-DsuiteXmlFile=testng.xml"]