# ROCKCHIP BOOT
Most rockchip application processors follow a generic boot process that can be summarised below  
1. BootRom code first runs. This is etched into the SOC and does not change. It scans for any enabled boot media and searches for a proprietry rockchip id block header at a specific offset (0x40) into the boot media. This header contains information about the size fo the firmware black that is appended to it. The bootrom code then loads the binary blob firmware that is attached to the block header into SRAM and jumps into it.

2. Second boot firmware loaded by bootrom code above runs. The main task of this firmware image is to intialise the DRAM controlers and get DRAM up and running. At this stage of the boot process, DRAM is available and a bigger program can then be loaded into memory. The loader component of this second boot firmware is responsible for loading uboot proper into the initialised DRAM memory and jumps into it. 

3. Uboot proper runs in DRAM memory. 


## Pre-bootloader flavours
There are 2 official methods for creating the boot firmware images for an application processor. One consists of using prebuilt proprietry binaries from rcokchip, and the second involves using build artifacts from the uboot upstream sources.

### Prebuilt Rockchip Supplied binaries

### Uboot open source build artifacts
Support for DDR controller initialization has been upstreamed into uboot for rockchip application processors. 



## Rockchip ID Block
The rockchip idblock is a data structure meant to be parsed by the bootrom code to load the secondary ( optionally, tertiary) boot stage code into memory to insitialize the system.

The size of the IDblock header is 512 bytes.  
The bootrom expexts to find this block at a predetermined location, based on the boot medium (offset 0x40 for sdcards and emmc storage).

### Information stored in IDBlock
1. Location of the first boot loader stage on the boot medium.  
This is encoded using a 16 bit field representing the number of 512 size bytes into the boot medium  
2. Size of the first boot loader stage.  
This is also encoded using a 16 bit field represent the number of 2kb blocks of offset.  
3. The size of the second boot loader stage.  It is assumed that the location fo the second bootloader stage imeeditaley follows the end of the first bootloader stage.


#### Note on second image encoded by idblock
It is possible for the first bootloader stage to never return. It will by itself load the second stage into DRAm and jump straight into it.  
However, if the first stage boot loader rteturns back to the bootrom, the bootrom will use the previous information read from the idblock to load the second boot stage and jump into it.  
This is a design decision to make the first stage loader specific in its functions of bringing up the system DRAM. In other words, it does not need to contain code to read a storage device to load up its code.


## Packaging idbloader.img
TO-DO