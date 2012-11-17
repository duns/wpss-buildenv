FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
inherit	systemd

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
	install -m 644 ${WORKDIR}/repair.sh ${D}${sbindir}/	
	install -m 644 ${WORKDIR}/sanity-check.sh ${D}${sbindir}/	
}
