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

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-PilotChalkanov.git;protocol=ssh;branch=main \
           file://misc-start-stop \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "5c3cae6ddc96b8645dfa6f6bc4ddbba08aae8789"

S = "${WORKDIR}/git"

inherit module

MODULE_SUBDIR = "misc-modules"
EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR} -C ${STAGING_KERNEL_DIR} M=${S}/${MODULE_SUBDIR}"


inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-start-stop"

FILES:${PN} += "${sysconfdir}/init.d/misc-start-stop"
FILES:${PN} += "${bindir}/module_load ${bindir}/module_unload"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install() {
      install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
      install -m 0755 ${S}/misc-modules/hello.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
      install -m 0755 ${S}/misc-modules/faulty.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/

      install -d ${D}${sysconfdir}/init.d
      install -m 0755 ${WORKDIR}/misc-modules-start-stop ${D}${sysconfdir}/init.d

      install -d ${D}${bindir}
      install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}
      install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}
}