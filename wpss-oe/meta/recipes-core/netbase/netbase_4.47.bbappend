#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#PRINC = "1"
PRINC := "${@int(PRINC) + 2}"
SRC_URI += " file://wpa_action  \
	file://ifupdown.rules \ 
	file://wpa_supplicant.conf"

do_install_append() {
        install -d  ${D}/${sysconfdir}/wpa_supplicant
#  	install -m 0644 ${WORKDIR}/interfaces ${D}${sysconfdir}/wpa_supplicant/
#  	install -m 0644 ${WORKDIR}/wpa_roam.conf ${D}${sysconfdir}/wpa_supplicant/
  	install -m 0644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant/
#  	install -m 0644 ${WORKDIR}/functions.sh ${D}${sysconfdir}/wpa_supplicant/
        install -d  ${D}/${sbindir}
  	install -m 0755 ${WORKDIR}/wpa_action ${D}${sbindir}/
        install -d  ${D}/${sysconfdir}/udev/rules.d
  	install -m 0644 ${WORKDIR}/ifupdown.rules ${D}${sysconfdir}/udev/rules.d/
}

