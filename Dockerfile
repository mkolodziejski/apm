FROM openjdk:8-jre-alpine


#
# UI
#
RUN apk add --no-cache py2-pip \
    && pip2 install --upgrade pip \
    && pip2 install flask

COPY apm-ui/*.py /apm/ui/
COPY apm-ui/static /apm/ui/static
COPY apm-ui/templates /apm/ui/templates

EXPOSE 5000



#
# javaagent
#
COPY javaagent/build/libs/javaagent-1.0.0.jar /apm/javaagent/



#
# webapp
#
COPY webapp/build/libs/webapp-1.0.0.jar /webapp/
EXPOSE 8080


COPY entrypoint.sh /apm/
ENTRYPOINT ["sh", "/apm/entrypoint.sh"]
