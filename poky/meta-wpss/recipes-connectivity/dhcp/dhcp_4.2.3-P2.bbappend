FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PRINC := "${@int(PRINC) + 1}"
SRC_URI += "file://dhclient.conf.master"
FILES_${PN} += "${sysconfdir}/dhcp/dhclient.conf.master"
RDEPENDS_${PN} += " gawk sed "

do_install_append () {
        install -d ${D}${sysconfdir}/dhcp
        install -m 0644 ${WORKDIR}/dhclient.conf.master ${D}${sysconfdir}/dhcp
}

pkg_postinst_${PN}() {
#!/bin/sh -e
if [ x"$D" = "x" ]; then
	# Actions to carry out on the device go here
	DOQUIT=0
        RETRIES=10
        while [ ${DOQUIT} -eq 0 ];
        do
        WLANMAC=`ifconfig wlan0|awk '/HWaddr/{print $5}'`
        if [ x"${WLANMAC}" = "x" ]; then
                echo wlan not yet up, retrying in 1 sec
                sleep 1
                RETRIES=`expr $RETRIES - 1`
                [ ${RETRIES} -eq 0 ] && exit 1

        else
                echo mac address ${WLANMAC}
		sed /etc/dhcp/dhclient.conf.master -e "s/WLANMACADDRESS/${WLANMAC}/" > /etc/dhcp/dhclient.conf
                DOQUIT=1
        fi
        done
else
	exit 1
fi
}

