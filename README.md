# RK3566
SOC manufactured by Rockchip  
CPU => Quad-Core 64 bit Cortex-A55  
GPU => ARM Mali G52 2EE graphics processor  
NPU => Integrated RKNN NPU AI accelerator, 0.8Tops@INT8 performance  
VPU  



# Boot Sequence of RK3566
BootROM is the first boot stage.  
The bootROM will attempt to load from sector 64 on a detected sd card.  
Sector 64 -> LBA 64 -> 32KB offset  
Therefore any bootloader code (TPL) should be placed at this sector of the sd card image.  
  
  NB: LBA = Logical block address.  
  Starts from 0 .... n  
    
Default block size is 512 bytes.  



# UBOOT Configuration
## TPL


# rkdeveloptool
This is a tool that is used to communicate to rockchip SOC's whne they are in the moskrom mode  
## miniloader.bin
This binary is needed at maskrom mode to initialize the device to be able to comminucate with the Host pc  
This can be obtained from the link https://drive.google.com/drive/folders/1_dkTZ6LD8JnoQ2PHpuud_FapzYD1Imtp  

The file name is `MiniLoaderAll.bin`

A copy of this binary is included in this repo under the "binaries" folder.

NB: The commands need to be run using sudo.



# DOCUMENTATION
Vist the docs folder for info on foused learning points