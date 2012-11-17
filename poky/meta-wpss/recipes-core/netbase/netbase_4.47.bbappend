#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
#PRINC = "1"
PRINC := "${@int(PRINC) + 3}"
#SRC_URI += " file://wpa_action  \
#	file://ifupdown.rules \ 
#	file://wpa_supplicant.conf"



