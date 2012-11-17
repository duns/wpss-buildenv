DESCRIPTION = "WPSS VPN keys"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

#PV = "${DISTRO_VERSION}"
PR = "r2"
inherit  systemd
SYSTEMD_PACKAGES="${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd="openvpn-tap.service"



RRECOMMENDS_${PN} += "openvpn openvpn-systemd"

SRC_URI += "file://pcatlaswpss02.crt \
           file://pcatlaswpss02.conf \
	file://wpss-ovpn-client.crt \
	file://wpss-ovpn-client.csr \
	file://wpss-ovpn-client.key \
	file://openvpn-tap.service"

CONFFILES_${PN} +=  "${sysconfdir}/openvpn/pcatlaswpss02.conf"

do_install_append () {
        install -d ${D}${sysconfdir}/openvpn
        install -m 0644 ${WORKDIR}/pcatlaswpss02.conf ${D}${sysconfdir}/openvpn/
        install -m 0644 ${WORKDIR}/pcatlaswpss02.crt ${D}${sysconfdir}/openvpn/
        install -m 0644 ${WORKDIR}/wpss-ovpn-client.crt ${D}${sysconfdir}/openvpn/
        install -m 0644 ${WORKDIR}/wpss-ovpn-client.csr ${D}${sysconfdir}/openvpn/
        install -m 0600 ${WORKDIR}/wpss-ovpn-client.key ${D}${sysconfdir}/openvpn/
}

#pkg_postinst_append() {
#        update-rc.d openvpn defaults
#}
#pkg_postinst_${PN}-systemd_append() {
#        mkdir -p ${D}/etc/systemd/system
#        ln -s ${D}/lib/systemd/system/openvpn@.service ${D}/etc/systemd/system/multi-user.target.wants/openvpn@pcatlaswpss02.service
#}


FILES_${PN} += "${sysconfdir}/openvpn/pcatlaswpss02.conf \
	${sysconfdir}/openvpn/pcatlaswpss02.crt \
	${sysconfdir}/openvpn/wpss-ovpn-client.crt \
	${sysconfdir}/openvpn/wpss-ovpn-client.csr \
	${sysconfdir}/openvpn/wpss-ovpn-client.key"
