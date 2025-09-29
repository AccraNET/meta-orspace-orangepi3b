require recipes-bsp/u-boot/u-boot.inc
require recipes-bsp/u-boot/u-boot-common.inc

inherit python3native

SRC_URI += " file://rk3566_ddr_1056MHz_v1.23.bin"
SRC_URI += "  file://0001-Add-Orspace-product-number.patch"

DEPENDS += "tfa-orspace"
DEPENDS += "python3-pyelftools-native"

EXTRA_OEMAKE += "ROCKCHIP_TPL=${UNPACKDIR}/rk3566_ddr_1056MHz_v1.23.bin BL31=${WORKDIR}/recipe-sysroot/sysroot-only/bl31.elf"

do_deploy:append() {
    install -m 0644 ${B}/u-boot-rockchip.bin ${DEPLOYDIR}/u-boot-rockchip.bin
}