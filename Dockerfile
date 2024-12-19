FROM tomcat:10-jdk17

COPY . /usr/local/work/FournisseurIdentite/

COPY lib/* /usr/local/tomcat/lib/

RUN chown -R root:root /usr/local/work/FournisseurIdentite
RUN chmod -R 755 /usr/local/work/FournisseurIdentite

WORKDIR /usr/local/work/FournisseurIdentite/

RUN chmod +x /usr/local/work/FournisseurIdentite/deploy.sh

EXPOSE 8080

CMD ["sh", "-c", "/usr/local/work/FournisseurIdentite/deploy.sh && /usr/local/tomcat/bin/catalina.sh run"]
