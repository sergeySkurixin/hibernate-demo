
ENV APP_HOME /u01/app
RUN mkdir -p "$APP_HOME"

copy build/libs/*.jar .