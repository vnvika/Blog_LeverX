FROM openjdk:8
ADD target/blog.jar blog.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","blog.jar"]