# Estágio 1: Build da aplicação com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código-fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# Estágio 2: Imagem final leve apenas com o JRE
FROM eclipse-temurin:21.0.9_10-jre

WORKDIR /app

COPY --from=builder /app/target/tech-challenge-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
