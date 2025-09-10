FROM maven:3.9.11-eclipse-temurin-8 AS build

ARG MAVEN_FLAGS=-o

WORKDIR /src

COPY pom.xml ./
COPY src ./src

RUN --mount=type=bind,from=m2,target=/root/.m2,rw \
	mvn -B $MAVEN_FLAGS -DskipTests -Droot.directory=/tmp/exploded package \
	&& cp target/boringcms-*.war /ROOT.war

FROM tomcat:9.0.121-jdk8-temurin-jammy

LABEL org.opencontainers.image.title="boringcms" \
	org.opencontainers.image.description="An XML/XSL content management system on Tomcat 9" \
	org.opencontainers.image.licenses="ISC"

COPY --from=build /ROOT.war /usr/local/tomcat/webapps/ROOT.war

RUN groupadd --system tomcat \
	&& useradd --system --gid tomcat --home-dir /usr/local/tomcat tomcat \
	&& mkdir -p /usr/local/tomcat/conf/Catalina/localhost \
	&& chown -R tomcat:tomcat \
		/usr/local/tomcat/conf/Catalina \
		/usr/local/tomcat/logs \
		/usr/local/tomcat/temp \
		/usr/local/tomcat/webapps \
		/usr/local/tomcat/work
USER tomcat

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=5s --start-period=90s --retries=12 \
	CMD curl -fsS http://localhost:8080/admin/login.xml -o /dev/null
