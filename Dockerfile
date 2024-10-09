# Use the official Tomcat image from Docker Hub
FROM tomcat:9.0.62-jdk11-openjdk

# Set the working directory inside the container
WORKDIR /usr/local/tomcat/webapps/

# Copy the WAR file from the target directory to the Tomcat webapps directory
COPY target/your-app-name.war ./ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
