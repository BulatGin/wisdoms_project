FROM tomcat:9.0.0.M26-jre8-alpine

ADD ROOT.war $CATALINA_HOME/webapps/

EXPOSE 8080