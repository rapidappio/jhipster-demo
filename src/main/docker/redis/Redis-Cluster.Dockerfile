FROM redis:7.2.5
RUN apt update && \
    apt install dnsutils -y
ADD redis/connectRedisCluster.sh /usr/local/bin/connectRedisCluster
RUN chmod 755 /usr/local/bin/connectRedisCluster
ENTRYPOINT ["connectRedisCluster"]
