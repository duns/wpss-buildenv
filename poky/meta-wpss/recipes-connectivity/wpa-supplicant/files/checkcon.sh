#!/bin/sh
SSIDS="CERN wpssconfig "
wpa_cli scan >/dev/null
SRESULTS=`wpa_cli scan_results`
for SSID in $SSIDS
do
	MATCH=`echo -e "$SRESULTS" | grep $SSID\$`
#	echo -e match "$MATCH"
	[ -n "$MATCH" ] && LIST="$MATCH
$LIST"
done
MAXLINE=`echo -e "$LIST"|sort -n -k 3 -r|head -1`
MAX=`echo $MAXLINE| cut -f1 -d' '`
CURRENT=`wpa_cli status|sed -n '/bssid=/s/bssid=//p'`

if [ "$MAX" == "$CURRENT" ] ;then
echo same
else
echo -e "$MAXLINE"
fi


