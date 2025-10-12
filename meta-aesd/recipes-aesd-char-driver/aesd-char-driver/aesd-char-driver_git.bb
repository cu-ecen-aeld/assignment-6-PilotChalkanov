# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ed1a118f474eea5e159b560c339329b"
INSANE_SKIP_${PN} += "license license-checksum"
INSANE_SKIP_${PN} += "license"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-PilotChalkanov.git;protocol=ssh;branch=feature/assignment-9-chalkanov \
           file://aesdchar_start_stop.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "1e4c276df851359af40ed862cc2a7768b8db493f"

S = "${WORKDIR}/git"

inherit module

RPROVIDES:${PN} += "kernel-module-aesdchar-${KERNEL_VERSION}"
EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR} -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar_start_stop"

FILES:${PN} += "${sysconfdir}/init.d/aesdchar_start_stop"
FILES:${PN} += "${bindir}/aesdchar_load ${bindir}/aesdchar_unload"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/*"

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
      install -m 0755 ${WORKDIR}/aesdchar_start_stop.sh ${D}${sysconfdir}/init.d/aesdchar_start_stop

      install -d ${D}${bindir}
      install -m 0755 ${S}/aesd-char-driver/aesdchar_load ${D}${bindir}/aesdchar_load
      install -m 0755 ${S}/aesd-char-driver/aesdchar_unload ${D}${bindir}/aesdchar_unload
}
