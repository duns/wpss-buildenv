FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
inherit	systemd

PRINC = "1"

SRC_URI += "file://watchdog.service \
	file://watchdog.conf"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "watchdog.service"

do_install_append(){
	install -d ${D}${sysconfdir}	
	install -m 644 ${WORKDIR}/watchdog.conf ${D}${sysconfdir}/	
}
