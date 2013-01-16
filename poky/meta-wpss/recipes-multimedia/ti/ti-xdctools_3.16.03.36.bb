require ti-xdctools.inc

PV = "3_16_03_36"
LIC_FILES_CHKSUM = "file://docs/license/xdc/shelf/package.html;md5=8d9dc7729144f037be664e9009ae6c43"

SRC_URI += "file://fix-hardcoded-paths-3.16.03.36.diff \
	file://arm-linker-hack-3.16.03.36.diff"

SRC_URI[xdcbin.md5sum] = "239da86ef7a70d920a1f9ea6ea5afc6e"
SRC_URI[xdcbin.sha256sum] = "7d18f3b02f4d49045184af0a7e093e5ef8c2c11fa21fd044d61c4f382b20336c"

S = "${WORKDIR}/ti/xdctools_${PV}"
TI_BIN_UNPK_CMDS="Y: qY:workdir:Y"
TI_BIN_UNPK_WDEXT="/ti"
