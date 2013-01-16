#!/bin/sh
CONFIGFILE="/etc/linphone/soundsettings.conf"
PREFERRED_DEVICE="none"
[ -e "$CONFIGFILE" ] && . "$CONFIGFILE"
INPUT=`linphonecsh generic "soundcard list" | awk -F : "/${PREFERRED_DEVICE}/{print $1}"`
[ -n "$INPUT" ] && linphonecsh generic "soundcard use $INPUT"
exit 0
