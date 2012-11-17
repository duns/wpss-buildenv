#!/bin/sh
#exit 0
REASON=$1
LOGFILE=/tmp/repair
case $REASON in
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
  201)
	date >> ${LOGFILE}
	echo wlan interface down, enabling...  >> ${LOGFILE}
	ifconfig wlan0 up
	;;
  *)
	date >> ${LOGFILE}
	echo $1 $2 $3 >> ${LOGFILE}
	;;
esac
exit 0
