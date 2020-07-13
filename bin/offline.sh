#!/bin/bash
# Startup script for spring boot project
# include yaml_parse function
. "$(dirname "$0")"/yaml.sh

# the name of the project
cd $(dirname $0)
BIN_DIR=$(pwd)
chmod 0755 *.sh
cd ..
DEPLOY_DIR=$(pwd)

CONF_DIR=$DEPLOY_DIR/config

EUREKA_URL=""
SERVER_PORT=""
APP_NAME=""
LOCAL_IP=""
if [ -f "$CONF_DIR/application.yml" ]
then
    # remove ^M in file
    sed -i "s/$(echo -e '\015')/\n/g" $CONF_DIR/application.yml
    # read yml file
    eval $(YamlParse__parse "$CONF_DIR/application.yml" "config_")
    SERVER_PORT=$config_server_port
    APP_NAME=$config_spring_application_name
    EUREKA_URL=$config_eureka_client_serviceUrl_defaultZone
fi

if [ -f "$CONF_DIR/bootstrap.yml" ]
then
    # remove ^M in file
    sed -i "s/$(echo -e '\015')/\n/g" $CONF_DIR/bootstrap.yml
    # read yml file
    eval $(YamlParse__parse "$CONF_DIR/bootstrap.yml" "config_")
    EUREKA_URL=${config_eureka_client_serviceUrl_defaultZone}
fi


LOCAL_IPS="$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}')"

# =============取出第一个IP=============
i=0
for IP in $LOCAL_IPS ; do
    LOCAL_IP=$IP
    let i=$i+1
    if [ $i == 1 ] 
    then
      break;
    fi  
done


# ====================================DEAL WITH PARAMS===================================================
EUREKA_URL=${EUREKA_URL%% *}
SERVER_PORT=${SERVER_PORT%% *}
APP_NAME=${APP_NAME%% *}


# ====================================START===================================================
echo -e "Start offline the $APP_NAME $LOCAL_IP:$SERVER_PORT ..."

# ====================================SEND REQUEST===================================================
URL="$EUREKA_URL/apps/$APP_NAME/$LOCAL_IP:$SERVER_PORT/status?value=OUT_OF_SERVICE"
echo "SERVER URL: $URL"

#curl -I -m 10 -o /dev/null -s -w %{http_code} -X PUT $URL  > /dev/null 2>&1
#只返回Http的响应头
RESULT_CODE=`curl -I -m 10 -o /dev/null -s -w %{http_code} -X PUT $URL`

echo "RESULT_CODE: $RESULT_CODE"

if [ $RESULT_CODE == 200 ]
then  
   echo "$APP_NAME $LOCAL_IP:$SERVER_PORT has already out from $EUREKA_URL"
else
   echo "$APP_NAME $LOCAL_IP:$SERVER_PORT out from $EUREKA_URL failed,Please check out!"
   echo "EUREKA_URL: $EUREKA_URL"
   echo "PORT: $SERVER_PORT"
   echo "APP_NAME: $APP_NAME"
   echo "LOCAL_IP: $LOCAL_IP"
   exit 1
fi

echo -e "Offline the $APP_NAME $LOCAL_IP:$SERVER_PORT ...\c"
echo -e ".\c"

# 需要sleep,等待Eruka Instance client默认30S,刷新缓存
sleep 30s

# ====================================PRINT LOG===================================================
echo "EUREKA_URL: $EUREKA_URL"
echo "PORT: $SERVER_PORT"
echo "APP_NAME: $APP_NAME"
echo "LOCAL_IP: $LOCAL_IP"


echo "OK!"

