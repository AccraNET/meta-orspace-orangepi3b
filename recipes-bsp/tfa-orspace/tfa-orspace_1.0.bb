SUMMARY = "Trusted firmware for arm64 based platforms"
DESCRIPTION = "Trusted firmware for arm64 based platforms"

LICENSE = "CLOSED"

SRC_URI = "git://github.com/TrustedFirmware-A/trusted-firmware-a.git;branch=main;protocol=https"
SRCREV = "9b446a2d962cd439a83af79f21292c9cd7ba13d3"

S = "${WORKDIR}/git"

PACKAGES += "tfa-orspace-firmware"
FILES:tfa-orspace-firmware = "/firmware/bl31.elf"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

TFA_PLATFORM = "rk3568"
EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX} PLAT=${TFA_PLATFORM}"

do_compile(){
    oe_runmake
}

do_install(){
    install -d ${D}/sysroot-only
    install -d ${D}/firmware
    install -m 0644 ${S}/build/${TFA_PLATFORM}/release/bl31/bl31.elf ${D}/sysroot-only/
    install -m 0644 ${S}/build/${TFA_PLATFORM}/release/bl31/bl31.elf ${D}/firmware/
}