FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
inherit	systemd
RDEPENDS_${PN} += " cronie"

PRINC = "1"

SRC_URI += "file://watchdog.service \
	file://watchdog.conf \
	file://sanity-check.sh \
	file://repair.sh "
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "watchdog.service"
FILES_${PN} += " ${sbindir}/repair.sh \
	${sbindir}/sanity-check.sh \
"

do_install_append(){
	install -d ${D}${sysconfdir}	
	install -m 644 ${WORKDIR}/watchdog.conf ${D}${sysconfdir}/	
	install -d ${D}${sbindir}	
	install -m 755 ${WORKDIR}/repair.sh ${D}${sbindir}/	
	install -m 755 ${WORKDIR}/sanity-check.sh ${D}${sbindir}/	
}


pkg_postinst_${PN}_prepend() {
        if test "x$D" != "x"; then
                exit 1
        else
                if ! grep -q -s sanity-check.sh /var/spool/cron/root; then
                       echo "adding crontab"
                       test -d /var/spool/cron || mkdir -p /var/spool/cron
                       echo "* * * * *    /usr/sbin/sanity-check.sh 1" >> /var/spool/cron/root
                fi
        fi
}
