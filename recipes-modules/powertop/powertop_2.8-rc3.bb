SUMMARY = "Power usage tool"
DESCRIPTION = "Linux tool to diagnose issues with power consumption and power management."
HOMEPAGE = "http://01.org/powertop/"
BUGTRACKER = "http://bugzilla.lesswatts.org/"
DEPENDS = "ncurses libnl pciutils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "https://github.com/fenrus75/powertop/archive/v2.8-rc3.tar.gz"

SRC_URI[md5sum] = "c31b3b19b0a2edb84cc3a2e4bd9439b6"
SRC_URI[sha256sum] = "9c23dc59d480804a21a7a8d74e2374b86e164d247d879d013d96cac63cdb1a3f"

inherit autotools gettext pkgconfig

# we need to explicitly link with libintl in uClibc systems
EXTRA_LDFLAGS ?= ""
EXTRA_LDFLAGS_libc-uclibc = "-lintl"
LDFLAGS += "${EXTRA_LDFLAGS}"

# we do not want libncursesw if we can
do_configure_prepend() {
    # configure.ac checks for delwin() in "ncursesw ncurses" so let's drop first one
    sed -i -e "s/ncursesw//g" ${S}/configure.ac
    mkdir -p ${B}/src/tuning/
}

inherit update-alternatives
ALTERNATIVE_${PN} = "powertop"
ALTERNATIVE_TARGET[powertop] = "${sbindir}/powertop"
ALTERNATIVE_LINK_NAME[powertop] = "${sbindir}/powertop"
ALTERNATIVE_PRIORITY = "100"
