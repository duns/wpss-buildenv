#!/bin/sh
RET=0
[ -z `ifconfig wlan0|grep RUNNING` ] && RET=1
[ $RET -ne 0 ] && exit `expr $RET + 200`
exit 0
