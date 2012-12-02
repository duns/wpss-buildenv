DESCRIPTION = "TI Codecs and Server Combo for OMAP3730"
SECTION = "multimedia"
LICENSE = "TI"
PN = "ti-codecs-omap3530"
FILESEXTRAPATHS_prepend := "${THISDIR}/ti-codecs-omap3730:"

require recipes-ti/includes/ti-paths.inc
require recipes-ti/includes/ti-staging.inc

PR="${MACHINE_KERNEL_PR}"
PR_append = "a"

PV="4_02_00_00"

LIC_FILES_CHKSUM = "file://packages/ti/sdo/server/cs/docs/codecs-omap3530_software_manifest.pdf;md5=92eb335aae23228b9726bba5ceaf1063"

CODEC_SUITE_NAME="${WORKDIR}/${PN}_${PV}"

SRCREV = "8393c892b09e0ac42b19ff1531e232478c3b1a6c"


SRC_URI="file://codecs-omap3530_4_02_00_00.tgz \
"
#    file://disable-c6accel.diff \


TI_BIN_UNPK_CMDS = "Y:workdir"

S = "${CODEC_SUITE_NAME}"

DEPENDS = "ti-cgt6x ti-xdctools ti-dspbios ti-codec-engine ti-linuxutils"

#generic codec
DSPSUFFIX_omap3530 = "x64P"

#do_unpack_append () {
#	mv codecs-omap3530_4_02_00_00 ${CODEC_SUITE_NAME}
#}

addtask prepsources after do_unpack before do_patch

do_prepsources () {
#
    mkdir -p ${CODEC_SUITE_NAME}
	cp -R ${WORKDIR}/codecs-omap3530_4_02_00_00/* ${CODEC_SUITE_NAME}/
#    tar xvzf codecs-omap3530_4_02_00_00.tgz -C ${CODEC_SUITE_NAME}
#    chmod 755 -R ${CODEC_SUITE_NAME}
}

do_compile() {

    cd "${S}"

    make \
             CE_INSTALL_DIR=${CE_INSTALL_DIR} \
             FC_INSTALL_DIR=${FC_INSTALL_DIR} \
             LINK_INSTALL_DIR=${LINK_INSTALL_DIR} \
             CMEM_INSTALL_DIR=${CMEM_INSTALL_DIR} \
             LPM_INSTALL_DIR=${LPM_INSTALL_DIR} \
             BIOS_INSTALL_DIR=${BIOS_INSTALL_DIR} \
             CODEGEN_INSTALL_DIR=${CODEGEN_INSTALL_DIR} \
             XDC_INSTALL_DIR=${XDC_INSTALL_DIR} \
             CODEC_INSTALL_DIR="${S}" \
             XDCARGS="prod" \
             C6ACCEL_INSTALL_DIR=${C6ACCEL_INSTALL_DIR} \
             clean

    make \
             CE_INSTALL_DIR=${CE_INSTALL_DIR} \
             FC_INSTALL_DIR=${FC_INSTALL_DIR} \
             LINK_INSTALL_DIR=${LINK_INSTALL_DIR} \
             CMEM_INSTALL_DIR=${CMEM_INSTALL_DIR} \
             LPM_INSTALL_DIR=${LPM_INSTALL_DIR} \
             BIOS_INSTALL_DIR=${BIOS_INSTALL_DIR} \
             CODEGEN_INSTALL_DIR=${CODEGEN_INSTALL_DIR} \
             XDC_INSTALL_DIR=${XDC_INSTALL_DIR} \
             CODEC_INSTALL_DIR="${S}" \
             XDCARGS="prod" \
             C6ACCEL_INSTALL_DIR=${C6ACCEL_INSTALL_DIR} \
             all
}

do_install() {

    install -d ${D}/${installdir}/ti-codecs-server
    cd ${S}

    # Install the DSP Server Binary 
    for file in `find . -name *.${DSPSUFFIX}`; do
        cp ${file} ${D}/${installdir}/ti-codecs-server
    done

    # Install docs (codec qualiTI test reports, server config datasheet, etc)
    for file in `find . -name *.html`; do
        cp ${file} ${D}/${installdir}/ti-codecs-server
    done

    install -d ${D}${CODEC_INSTALL_DIR_RECIPE}
    cp -pPrf ${CODEC_SUITE_NAME}/* ${D}${CODEC_INSTALL_DIR_RECIPE}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES += "ti-codecs-omap3530-server"
FILES_ti-codecs-omap3530-server = "${installdir}/ti-codecs-server/*"


