#!/bin/sh
#exec > /dev/null 2>&1
#exec >> /tmp/test 2>&1
. /etc/profile
INTERVAL=180
NUMPINGS=1
PANICFILE="/tmp/dopanic"
TIMEFILE="/tmp/sanity-last-check"
FAILURE_FILE="/tmp/sanity-failures"
MAX_FAILURES=3
INITAL_GRACE_TIME=60
SANITY=0
EPOCH=`date +%s`
PINGVPN="10.10.1.1"
PINGHOST="pcatlaswpss02"
WAITPING=3
EXIT=exit
REASONFILE="/tmp/sanity-state"
[ -n "$1" ]  && INTERVAL=0 

read_failure_file()
{
	NUMFAILURES=0
	[ -e "$FAILURE_FILE" ] && NUMFAILURES=`cat "$FAILURE_FILE"`
	[ $NUMFAILURES -ge $MAX_FAILURES ] && return 1
	return 0

}
refresh_failure_file()
{
	NUMFAILURES=0
	[ -e "$FAILURE_FILE" ] && NUMFAILURES=`cat "$FAILURE_FILE"`
	expr "$NUMFAILURES" + 1 > $FAILURE_FILE
	return 0

}

[ -z "$1" ] && [ -e "$REASONFILE" ] && SANITY=`cat "$REASONFILE"` && rm -f "$REASONFILE"  && ${EXIT} $SANITY
if [ -z "$1" ]; then
#NTPSYNCED=`systemctl status ntpd.service| grep '(running)'`
#NTPSYNCED=`date +%Y | grep -e  "201."`
	NTPSYNCED=`systemctl status ntpd.service| grep '(running)'`
	[ -z "$NTPSYNCED" ] && ${EXIT} 0
	if [ ! -e "$TIMEFILE" ] ; then
		expr "$EPOCH" + "$INITAL_GRACE_TIME" > "$TIMEFILE"
		rm -f "$FAILURE_FILE"
	fi
	PREVEPOCH=`cat $TIMEFILE`
	expr "$EPOCH" - "$PREVEPOCH" \> "$INTERVAL" 
	[ $? -eq 1 ] && echo deferring execution && ${EXIT} 0
	echo sanity check
	echo "$EPOCH" > "$TIMEFILE"
fi
#if called without arguments, just check for panicfile
[ -z "$1" ] && [ -e "$PANICFILE" ] && ${EXIT} 200
[ -z "$1" ] && [ ! -e "$PANICFILE" ] && touch "$PANICFILE"  && ${EXIT} 0

#exec > /tmp/test 2>&1

#test if wireless is on
WIRELESS_OFF=1
TRIES=5
while [ $TRIES -gt 0 ]
do
[ -n "`ifconfig wlan0|grep RUNNING`" ] && WIRELESS_OFF=0
[ $WIRELESS_OFF -eq 0 ] && break
TRIES=`expr $TRIES - 1`
sleep 1
done


	echo none > ${WPSS_LED_CON}/trigger
if [ "$WIRELESS_OFF"x == "1x" ] ; then
	#action if wireless is off
	echo WiFi seems to be off
	echo none > ${WPSS_LED_NET}/trigger
	SANITY=201
else
	echo Wifi is on
	echo default-on > ${WPSS_LED_NET}/trigger
#test if server is alive
	/bin/ping -q -w $WAITPING -c $NUMPINGS "$PINGHOST" 
#sleep 1
#sleep $WAITPING &
#wait $!
	if [ "$?" -ne 0 ]; then
		SERVER_UNREACHABLE=1
		echo server is unreachable
		SANITY=202
	else
		echo timer > ${WPSS_LED_NET}/trigger

#test if VPN is alive
		/bin/ping -q -w $WAITPING -c $NUMPINGS "$PINGVPN" 
		if [ "$?" -ne 0 ]; then
			VPN_UNREACHABLE=1
			echo VPN is unreachable
			SANITY=203
		else
			echo none > ${WPSS_LED_NET}/trigger
			echo default-on > ${WPSS_LED_CON}/trigger

		fi
	fi
fi



if [ "$SANITY" -eq 0 ]; then
echo sanity check succeeded
rm -f "$FAILURE_FILE"
else
echo sanity check failed, updating number of failed checks
NUMFAILURES=0
[ -e "$FAILURE_FILE" ] && NUMFAILURES=`cat "$FAILURE_FILE"`
expr "$NUMFAILURES" + 1 > $FAILURE_FILE
[ $NUMFAILURES -ge $MAX_FAILURES ] && SANITY=200
fi
echo $SANITY > "$REASONFILE"
[ "$SANITY" -ne 200 ] && rm -f "$PANICFILE"
${EXIT} $SANITY
