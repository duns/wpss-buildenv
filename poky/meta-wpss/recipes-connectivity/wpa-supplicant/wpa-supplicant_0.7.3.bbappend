FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
RDEPENDS_${PN} += " cronie"
PRINC := "${@int(PRINC) + 2}"
SRC_URI += " file://wpa_action  \
        file://ifupdown.rules \
        file://wpa_supplicant.conf"

do_install_${PN}_append() {
        install -d  ${D}/${sysconfdir}/wpa_supplicant
#       install -m 0644 ${WORKDIR}/wpa_roam.conf ${D}${sysconfdir}/wpa_supplicant/
        install -m 0644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant/
#       install -m 0644 ${WORKDIR}/functions.sh ${D}${sysconfdir}/wpa_supplicant/
        install -d  ${D}/${sbindir}
        install -m 0755 ${WORKDIR}/wpa_action ${D}${sbindir}/
        install -d  ${D}/${sysconfdir}/udev/rules.d
        install -m 0644 ${WORKDIR}/ifupdown.rules ${D}${sysconfdir}/udev/rules.d/
}

pkg_postinst_${PN}_prepend() {
if test "x$D" != "x"; then
        exit 1
else
        if ! grep -q -s wpa_cli /var/spool/cron/root; then
                echo "adding crontab"
                test -d /var/spool/cron || mkdir -p /var/spool/cron
                echo "* * * * *    /usr/sbin/wpa_cli scan" >> /var/spool/cron/root
        fi
	exit 0
fi
}