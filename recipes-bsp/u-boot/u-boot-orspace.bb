require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-common.inc

inherit python3native

DEPENDS += "tfa-orspace"
DEPENDS += "python3-pyelftools-native"

EXTRA_OEMAKE += "BL31=${WORKDIR}/recipe-sysroot/sysroot-only/bl31.elf"

