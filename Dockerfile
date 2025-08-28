#!/usr/bin/env docker
# Dockerfile para primeira-aventura backend - Spring Boot com Java 23

# Multi-stage build para otimizar a imagem final
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copiar arquivos de configuração do Maven primeiro (para cache)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download das dependências (layer cacheable)
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve

# Copiar código fonte
COPY src ./src
COPY uploads ./uploads

# Build da aplicação com encoding UTF-8
RUN ./mvnw clean package -DskipTests -Dfile.encoding=UTF-8 -Dproject.build.sourceEncoding=UTF-8

# Imagem final otimizada
FROM eclipse-temurin:23-jre-alpine

WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001

# Copiar JAR da imagem de build
COPY --from=build /app/target/*.jar app.jar

# Alterar proprietário do arquivo
RUN chown spring:spring app.jar

# Usar usuário não-root
USER root

# Expor porta da aplicação Spring Boot
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Executar aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
