SUMMARY = "Trusted firmware for arm64 based platforms"
DESCRIPTION = "Trusted firmware for arm64 based platforms"

LICENSE = "CLOSED"

SRC_URI = "git://github.com/TrustedFirmware-A/trusted-firmware-a.git;branch=main;protocol=https"
# SRCREV = "c17351450c8a513ca3f30f936e26a71db693a145"
SRCREV = "9b446a2d962cd439a83af79f21292c9cd7ba13d3"

S = "${WORKDIR}/git"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"


do_compile(){
    oe_runmake PLAT=rk3568 CROSS_COMPILE=${TARGET_PREFIX}
}

do_install(){
    install -d ${D}/sysroot-only
    install -m 0644 ${S}/build/rk3568/release/bl31/bl31.elf ${D}/sysroot-only/
}