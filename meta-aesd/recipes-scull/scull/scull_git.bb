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
           file://start_stop \
           file://access_ok_version.h \
           file://proc_ops_version.h \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "35db6b58340f4e4cc701e93bf1404ea2b81779cd"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR} -C ${STAGING_KERNEL_DIR} M=${S}/scull"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "start_stop"

FILES:${PN} += "${sysconfdir}/init.d/start_stop"
FILES:${PN} += "${bindir}/scull_load ${bindir}/scull_unload"

do_configure () {
	:
}

do_compile () {
# Ensure required compatibility headers exist in the scull source dir
    install -D -m 0644 ${WORKDIR}/access_ok_version.h ${S}/scull/access_ok_version.h
    install -D -m 0644 ${WORKDIR}/proc_ops_version.h ${S}/scull/proc_ops_version.h
	oe_runmake
}

do_install() {
      install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
      install -m 0644 ${S}/scull/scull.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/

      install -d ${D}${sysconfdir}/init.d
      install -m 0755 ${WORKDIR}/start_stop ${D}${sysconfdir}/init.d

      install -d ${D}${bindir}
      install -m 0755 ${S}/scull/scull_load ${D}${bindir}/scull_load
      install -m 0755 ${S}/scull/scull_unload ${D}${bindir}/scull_unload
}