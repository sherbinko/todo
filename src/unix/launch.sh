#!/bin/sh

java -jar `which "$0"` "$@"
RES=$?
exit $RES
