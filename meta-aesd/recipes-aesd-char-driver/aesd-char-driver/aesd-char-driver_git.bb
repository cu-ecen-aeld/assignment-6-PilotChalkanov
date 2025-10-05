# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com:cu-ecen-aeld/assignments-3-and-later-PilotChalkanov.git;protocol=ssh;branch=feat/assignment8-chalkanov-partial-write \
           file://aesdchar_start_stop.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "b432ce3df0d66f736393fc1f0b1c6b42525a5b74"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR} -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar_start_stop"

FILES:${PN} += "${sysconfdir}/init.d/aesdchar_start_stop"
FILES:${PN} += "${bindir}/aesdchar_load ${bindir}/aesdchar_unload"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install() {
      install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
      install -m 0644 ${S}/aesd-char-driver/*.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/

      install -d ${D}${sysconfdir}/init.d
      install -m 0755 ${WORKDIR}/aesdchar_start_stop.sh ${D}${sysconfdir}/init.d

      install -d ${D}${bindir}
      install -m 0755 ${S}/aesd-char-driver/aesdchar_load ${D}${bindir}/aesdchar_load
      install -m 0755 ${S}/aesd-char-driver/aesdchar_unload ${D}${bindir}/aesdchar_unload
}