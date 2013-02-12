require ti-dsplink.inc

PE = "1"
PV = "1_65_02_09"
PV_dot = "1.65.02.09"
PV_major = "1_65"
S = "${WORKDIR}/dsplink_linux_${PV}"
SRC_URI[dsplinktarball.md5sum] = "898793a1d0b3e06fd4daa31826961fe8"
SRC_URI[dsplinktarball.sha256sum] = "2fdb77c071d931b1bdf54cca545f9edc1b0f1f8f280bc9cc04a01e9848b0e74c"
LIC_FILES_CHKSUM = "file://dsplink/doc/UserGuide.pdf;md5=535ee36415f03edc1b40d2beb17cffaa"

SRC_URI = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent/DSPLink/1_65_02_09/exports/dsplink_linux_1_65_02_09.tar.gz;name=dsplinktarball \
           file://ti-dsplink-examples-run.sh \
           file://ti-dsplink-examples-loadmodules.sh \
           file://ti-dsplink-examples-unloadmodules.sh "

#SRC_URI += " file://0001-remove-check-for-make-version-3.85-works-fine.patch \
#           "

#SRC_URI[dsplinktarball.md5sum] = "1bda596b631bd2f517edc70f6be4f2ca"
#SRC_URI[dsplinktarball.sha256sum] = "4b1bda89bd8465b887f5bcdf7b95018bc1d1f8968c0c44f8cbad2a9e1c52bcb7"
