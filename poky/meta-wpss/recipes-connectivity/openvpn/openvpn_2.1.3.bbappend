INITSCRIPT_NAME = "openvpn"
INITSCRIPT_PARAMS = "defaults 10"
inherit update-rc.d systemd
PRINC = "3"
SRC_URI += "file://openvpn@.service"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SYSTEMD_PACKAGES="${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd="openvpn@.service"

