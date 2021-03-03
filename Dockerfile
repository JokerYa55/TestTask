FROM maven:3.3.9-jdk-8
RUN mkdir -p /home/app
RUN groupadd -r app &&\
    useradd -r -g app -d /home/app -s /sbin/nologin -c "Docker image user" app
ENV APP_HOME=/home/app 
WORKDIR $APP_HOME
ENV MAVEN_CONFIG .m2
ADD target/*.jar $APP_HOME
EXPOSE 8080                                 
WORKDIR $APP_HOME
RUN mkdir .m2 &&\
    chown -R app:app $APP_HOME &&\
    chmod -R 777 $APP_HOME
USER app
CMD java -jar -Dmaven.repo.local=.m2 $JAVA_OPT  $(find *.jar)