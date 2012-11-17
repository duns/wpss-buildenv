INITSCRIPT_NAME = "openvpn"
INITSCRIPT_PARAMS = "defaults 10"
inherit update-rc.d systemd
PRINC = "3"
SRC_URI += "file://openvpn@.service"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SYSTEMD_PACKAGES="${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd="openvpn@.service"
SYSTEMD_AUTO_ENABLE_${PN}-systemd = "disable"

do_install_append () {
        ln -sf /dev/null ${D}${systemd_unitdir}/system/openvpn.service
}

FILES_${PN}-systemd += "${systemd_unitdir}/system/openvpn.service"
