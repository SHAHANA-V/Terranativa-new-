FROM tomcat:9-jdk17-temurin

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your app (WAR-style folder or WAR file)
COPY . /usr/local/tomcat/webapps/ROOT/

# Expose default port
EXPOSE 8080
