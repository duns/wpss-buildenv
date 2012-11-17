#!/bin/sh
ROAMNETWORKS="wpssconfig CERN linksys Oxyg"
for NET in $ROAMNETWORKS
do
	ROAMNETWORKSNL="$NET
$ROAMNETWORKSNL"
done
wpa_cli scan > /dev/null

SCANRES=`wpa_cli scan_results | grep -F "$ROAMNETWORKSNL"`
OWNBSSID=`wpa_cli status|sed -n -e '/bssid/s/bssid=//p'`
OWN=`echo -e "$SCANRES" | grep $OWNBSSID`
STRONGEST=`echo -e "$SCANRES" | sort -k 3 -r |  head -1`
STRONGESTBSSID=`echo $STRONGEST | cut -f 1`

OWNLEVEL=`echo -e "$OWN" | cut -f 3`
STRONGESTLEVEL=`echo -e "$STRONGEST" | cut -f 3`
#echo $OWNLEVEL $STRONGESTLEVEL
if [ -z "$STRONGESTLEVEL" ] ; then
echo no networks found
fi
if [ -n "$OWNLEVEL" ] ; then
if [ `expr $STRONGESTLEVEL - $OWNLEVEL` -gt 20 ] ; then
echo stronger network found, trying to roam
wpa_cli roam $STRONGESTBSSID
#wpa_cli reassociate
fi
else
echo not associated, trying to...
ifconfig wlan0 up
wpa_cli scan
wpa_cli reassociate
fi


