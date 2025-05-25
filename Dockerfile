# Etapa de build
FROM maven:3-openjdk-17 AS builder

WORKDIR /build

# Recebe as variáveis da AWS como argumentos (opcional, mas útil em CI/CD)
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ARG AWS_SESSION_TOKEN

# Define as variáveis como ambiente para que o Maven possa usá-las
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
ENV AWS_SESSION_TOKEN=$AWS_SESSION_TOKEN

COPY . .

# Build do projeto (sem testes e sem checkstyle)
RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

# Etapa final: imagem leve apenas com a aplicação
FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /build/target/*.jar /app/app.jar

# Porta exposta (caso use algum container orchestrator ou localmente)
EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
