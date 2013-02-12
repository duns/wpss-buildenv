#!/bin/sh
#exit 0
REASON=$1
LOGFILE=/home/root/repair
EXIT_CRITICAL="exit 0"
case $REASON in
  -9)
#HACK to avoid wrong date
        NTPSYNCED=`date +%Y | grep -e "201." `
	[ -z "$NTPSYNCED" ] && echo HACK: skiping reboot due to incorrect time >> ${LOGFILE} &&  exit 0
        #child process did not return in time
		date >> ${LOGFILE}
		echo child process did not return in time $REASON $2 $3 , rebooting  >> ${LOGFILE}
		sync
	        ${EXIT_CRITICAL}
        ;;
  12)
        #out of memory
		date >> ${LOGFILE}
		echo out of memory, rebooting  >> ${LOGFILE}
		sync
        	exit 1
        ;;
  101)
	#ping timeout
	IP=$2
	case $IP in 
		wlan0)
#		date >> ${LOGFILE}
#		echo wlan0 inactive, restarting... $1 $2 $3 >> ${LOGFILE}
#		ifdown wlan0
#		ifup wlan0
#		sleep 5
		;;
		*)
		date >> ${LOGFILE}
		echo $1 $2 $3 >> ${LOGFILE}
		;;
	esac
	;;
  200)
	date >> ${LOGFILE}
	echo sanity check failed, rebooting...  >> ${LOGFILE}
	${EXIT_CRITICAL}
	;;
  201)
	date >> ${LOGFILE}
	echo wlan interface down, enabling...  >> ${LOGFILE}
	ifdown wlan0
#	sleep 2

	nohup ifup wlan0 &
#	ifconfig wlan0 up
	;;
  202)
	date >> ${LOGFILE}
	echo  server inacessible , reenabling...  >> ${LOGFILE}
	ifdown wlan0
	nohup ifup wlan0 &
#	ifconfig wlan0 down
#	ifconfig wlan0 up
	;;
  203)
	date >> ${LOGFILE}
	echo  VPN down, reenabling...  >> ${LOGFILE}
	systemctl restart openvpn-tap.service
	;;
  *)
	date >> ${LOGFILE}
	echo $1 $2 $3 >> ${LOGFILE}
	sync
	;;
esac
exit 0
